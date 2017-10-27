package eu.openminted.workflows.galaxywrappers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import eu.openminted.registry.domain.Component;
import eu.openminted.registry.domain.ComponentInfo;
import eu.openminted.registry.domain.Description;
import eu.openminted.registry.domain.ParameterInfo;
import eu.openminted.registry.domain.ParameterTypeEnum;
import eu.openminted.registry.domain.ProcessingResourceInfo;
import eu.openminted.workflows.galaxytool.Add;
import eu.openminted.workflows.galaxytool.Collection;
import eu.openminted.workflows.galaxytool.Container;
import eu.openminted.workflows.galaxytool.DiscoverDatasets;
import eu.openminted.workflows.galaxytool.GalaxyCons;
import eu.openminted.workflows.galaxytool.Inputs;
import eu.openminted.workflows.galaxytool.Outputs;
import eu.openminted.workflows.galaxytool.Param;
import eu.openminted.workflows.galaxytool.Requirements;
import eu.openminted.workflows.galaxytool.Sanitizer;
import eu.openminted.workflows.galaxytool.Tool;
import eu.openminted.workflows.galaxytool.Valid;

/**
 * @author ilsp
 *
 */
public class GalaxyWrapperGenerator {

	private static final Logger log = Logger.getLogger(GalaxyWrapperGenerator.class.getName());

	private OMTDSHAREParser omtdshareParser;
	private GalaxyToolWrapperWriter galaxyToolWrapperWriter;

	private File outDirHandler;

	private Component componentMeta;
	private String componentID;
	private String dockerImage;
	private String framework;
	
	public GalaxyWrapperGenerator(String outDir) {
		omtdshareParser = new OMTDSHAREParser();
		galaxyToolWrapperWriter = new GalaxyToolWrapperWriter();

		// Create wrappers output dir if not exists.
		outDirHandler = new File(outDir);
		if (!outDirHandler.exists()) {
			outDirHandler.mkdirs();
		}
	}
	
	public String getDockerImage() {
		return dockerImage;
	}


	public void setDockerImage(String dockerImage) {
		this.dockerImage = dockerImage;
	}


	public Component getComponentMeta() {
		return componentMeta;
	}

	
	public String getComponentID() {
		return componentID;
	}


	public void setComponentID(String componentID) {
		this.componentID = componentID;
	}


	public Tool generate(File omtdShareFile) {
		try {
			// Parse omtd-share file.
			componentMeta = omtdshareParser.parse(omtdShareFile);

			// Get info
			ComponentInfo componentInfo = componentMeta.getComponentInfo();
			List<Description> descriptions = componentInfo.getIdentificationInfo().getDescriptions();
			ProcessingResourceInfo processingResourceInfo = componentInfo.getInputContentResourceInfo();
			
			// ** Create the tool.
			Tool tool = new Tool();

			// Get some required info from OMTD component meta
			// * Tool desc.
			String desc = "";
			if (descriptions != null && descriptions.size() > 0) {
				desc = descriptions.get(0).getValue();
			} else {
				desc = componentInfo.getIdentificationInfo().getResourceNames().get(0).getValue();
			}
			// * Component ID.
			componentID = componentInfo.getIdentificationInfo().getResourceIdentifiers().get(0).getValue();
			// * Component full name.
			String componentFullName = componentInfo.getIdentificationInfo().getResourceNames().get(0).getValue();
			// * Framework used.
			framework  = componentInfo.getComponentCreationInfo().getFramework().value();
			// * Short name.
			String componentShortName = Utils.getShortNameFromComponentID(componentID);
			// * Component version.
			String version = componentInfo.getVersionInfo().getVersion();

			// Configure wrapper description.
			tool.setDescription(desc);
			tool.setId(componentID);
			tool.setName(componentShortName);
			tool.setVersion(version);

			// Configure wrapper requirements.
			Requirements requirements = new Requirements();
			Container container = new Container();
			container.setType("docker");
			System.out.println("Assigned docker image:" + dockerImage);
			container.setValue(dockerImage);
			requirements.setContainer(container);
			tool.setRequirements(requirements);

			// Configure wrapper inputs/outputs.
			Inputs inputs = new Inputs();
			Outputs outputs = new Outputs();

			// * Create input params
			ArrayList<Param> inputParams = createInputParams(processingResourceInfo);
			// * Create&add the data input param
			Param dataGalaxyParam = createDataInputParam(componentShortName);
			inputParams.add(dataGalaxyParam);
			
			inputs.setParams(inputParams);
			tool.setInputs(inputs);
			
			// * Outputs
			DiscoverDatasets dd = new DiscoverDatasets();
			dd.setDirectory("out");
			dd.setPattern("__designation__");
			dd.setFormat("pdf");
			dd.setVisible("false");
			Collection collection = new Collection();
			collection.setDiscoverDatasets(dd);
			collection.setName("output");
			collection.setType("list");
			collection.setLabel(componentShortName + "_output");
			outputs.setCollection(collection);
			// info = componentInfo.getOutputResourceInfo();
			// outputs.setParams(extractInputParams(info));
			tool.setOutputs(outputs);

			// Set command
			setToolCommand(tool, framework, componentID, dataGalaxyParam.getName(), componentID, processingResourceInfo.getParameterInfos());

			// Serialize wrapper object to a file.
			String galaxyWrapperPath = outDirHandler.getAbsolutePath() + "/" + omtdShareFile.getName() + ".xml";
			galaxyToolWrapperWriter.write(tool, galaxyWrapperPath);

			return tool;
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			return null;
		}

	}

	private void setToolCommand(Tool tool, String framework, String componentID, String galaxyParamName, String componentFullName, List<ParameterInfo> parametersInfos){
		String execCMD = "";
		
		// Works for java
		String coord = getCoordinatesFromResourceIdentifier(componentID);
		
		//
		GalaxyToolExecutionCommand gtec = new GalaxyToolExecutionCommand(framework);
		execCMD = gtec.buildCheetahCode(galaxyParamName, coord, componentID, listParameters(parametersInfos)); 
		tool.setCommand(execCMD);	
	}
	
	
	private Param createDataInputParam(String name) {
		Param dataGalaxyParam = new Param();

		// dataGalaxyParam.setType("data");
		dataGalaxyParam.setType("data_collection");
		dataGalaxyParam.setCollection_type("list");
		dataGalaxyParam.setFormat("pdf");
		
		dataGalaxyParam.setName(name + "_InputFiles");
		dataGalaxyParam.setLabel(name + "_InputFiles");

		// dataGalaxyParam.setMultiple("true");

		return dataGalaxyParam;
	}

	public static String getCoordinatesFromResourceIdentifier(String compID){
		String coordinates = "ERROR";
		if(compID.startsWith("mvn:")){
			
			int end = compID.indexOf("#");
			coordinates = compID.substring("mvn:".length(), end);
		}
		
		return coordinates;
	}
	
	
	public void setDefaultValue(ParameterInfo paramInfo, Param galaxyParam){
		// Set default value.
		
		if (paramInfo.getDefaultValue() != null && paramInfo.getDefaultValue().size() > 0) {
			
			String defaultValue = paramInfo.getDefaultValue().get(0);
			
			if(paramInfo.getParameterType() != null){
				String parameterType = paramInfo.getParameterType().value();	
				
				if(parameterType.equalsIgnoreCase(ParameterTypeEnum.STRING.value())){
					galaxyParam.setValue(defaultValue);
				}else if(parameterType.equalsIgnoreCase(ParameterTypeEnum.BOOLEAN.value())){
					galaxyParam.setChecked(defaultValue);
				}else if(parameterType.equalsIgnoreCase(ParameterTypeEnum.INTEGER.value())){
					galaxyParam.setValue(defaultValue);
				}else if(parameterType.equalsIgnoreCase(ParameterTypeEnum.FLOAT.value())){
					galaxyParam.setValue(defaultValue);
				}else{
					System.out.println("UKNOWN PARAMETER TYPE:" + parameterType + "use default: " + GalaxyCons.textT);
					galaxyParam.setValue(defaultValue);
				}				
			}else{
				galaxyParam.setValue(defaultValue);
			}
			
		}
	}

	// Map OMTD-SHARE
	// https://docs.galaxyproject.org/en/master/dev/schema.html#id29
	public void setParameterType(ParameterInfo paramInfo, Param galaxyParam){
		
		if(paramInfo.getParameterType() != null){
			
			String parameterType = paramInfo.getParameterType().value();
			
			if(parameterType.equalsIgnoreCase(ParameterTypeEnum.STRING.value())){
				galaxyParam.setType(GalaxyCons.textT);
				galaxyParam.setSanitizer(createSanitizerWithAllAllowed());
			}else if(parameterType.equalsIgnoreCase(ParameterTypeEnum.BOOLEAN.value())){
				galaxyParam.setType(GalaxyCons.booleanT);
				System.out.println("boolean:" + paramInfo.getDefaultValue().get(0) + " -- " + paramInfo.getParameterName() );
			}else if(parameterType.equalsIgnoreCase(ParameterTypeEnum.INTEGER.value())){
				galaxyParam.setType(GalaxyCons.integerT);
				System.out.println("integer:" + paramInfo.getParameterName());
			}else if(parameterType.equalsIgnoreCase(ParameterTypeEnum.FLOAT.value())){
				galaxyParam.setType(GalaxyCons.floatT);
				System.out.println("float:" + paramInfo.getParameterName());
			}else{
				System.out.println("UKNOWN PARAMETER TYPE:" + parameterType + "use default: " + GalaxyCons.textT);
				galaxyParam.setType(GalaxyCons.textT);
			}	
		}else{
			galaxyParam.setType(GalaxyCons.textT);
		}
	}
	

	
	/**
	 * Creates the input parameter for a Galaxy tool.
	 * @param info
	 * @return the list of parameters.
	 */
	private ArrayList<Param> createInputParams(ProcessingResourceInfo info) {

		ArrayList<Param> params = new ArrayList<Param>();
		List<ParameterInfo> parametersInfos = info.getParameterInfos();

		//For each OMTD-SHARE parameter
		for (ParameterInfo paramInfo : parametersInfos) {
			// Create Galaxy param.
			Param galaxyParam = new Param();

			// Add some info
			galaxyParam.setName(paramInfo.getParameterName());
			galaxyParam.setLabel(paramInfo.getParameterLabel());
			galaxyParam.setHelp(paramInfo.getParameterDescription());
			galaxyParam.setOptional(String.valueOf(paramInfo.isOptional()));

			// Set default value.
			setDefaultValue(paramInfo, galaxyParam);

			// Set type 
			setParameterType(paramInfo, galaxyParam);

			// Add it to the list.
			params.add(galaxyParam);
		}

		return params;
	}

	private ArrayList<String> listParameters(List<ParameterInfo> parametersInfos) {

		ArrayList<String> params = new ArrayList<String>();

		for (ParameterInfo paramInfo : parametersInfos) {
			// Add it to the list.
			params.add(paramInfo.getParameterName());
		}

		return params;
	}
	
	private Sanitizer createSanitizerWithAllowedValues(){
		Sanitizer sanitizer = new Sanitizer();
		
		Valid valid = new Valid();
				
		String[] values = {"<" , ">", "[", "]"};		
		
		ArrayList<Add> list = new ArrayList<Add>();
		for(int i = 0; i < values.length; i++){
			Add add = new Add();
			add.setValue(values[i]);
			list.add(add);
		}
		valid.setAddList(list);
		sanitizer.setValid(valid);
		
		return sanitizer;

	}
	
	private Sanitizer createSanitizerWithAllAllowed(){
		Sanitizer sanitizer = new Sanitizer();
		
		sanitizer.setSanitize("False");
		
		return sanitizer;

	}
	
}
