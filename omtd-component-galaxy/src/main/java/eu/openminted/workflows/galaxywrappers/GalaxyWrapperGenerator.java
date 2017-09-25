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
import eu.openminted.workflows.galaxytool.Collection;
import eu.openminted.workflows.galaxytool.Container;
import eu.openminted.workflows.galaxytool.DiscoverDatasets;
import eu.openminted.workflows.galaxytool.GalaxyCons;
import eu.openminted.workflows.galaxytool.Inputs;
import eu.openminted.workflows.galaxytool.Outputs;
import eu.openminted.workflows.galaxytool.Param;
import eu.openminted.workflows.galaxytool.Requirements;
import eu.openminted.workflows.galaxytool.Tool;

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

			// ** Create the tool.
			Tool tool = new Tool();

			// Get tool desc.
			String desc = "";
			if (descriptions != null && descriptions.size() > 0) {
				desc = descriptions.get(0).getValue();
			} else {
				desc = componentInfo.getIdentificationInfo().getResourceNames().get(0).getValue();
			}

			// Get id.
			componentID = componentInfo.getIdentificationInfo().getResourceIdentifiers().get(0).getValue();

			String fullName = componentInfo.getIdentificationInfo().getResourceNames().get(0).getValue();

			String name = fullName.substring(fullName.lastIndexOf(".") + 1);
			// Get tool version.
			String version = componentInfo.getVersionInfo().getVersion();

			// Configure wrapper description.
			tool.setDescription(desc);
			tool.setId(componentID);
			tool.setName(name);
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

			ProcessingResourceInfo info = componentInfo.getInputContentResourceInfo();

			// Create input params
			ArrayList<Param> inputParams = createInputParams(info);
			// Create&add the data input param
			Param dataGalaxyParam = createDataInputParam(name);
			inputParams.add(dataGalaxyParam);

			inputs.setParams(inputParams);
			tool.setInputs(inputs);

			// tool.setCommand("to be completed");
			String coord = normalizeCoordinates(getCoordinatesFromResourceIdentifier(componentID));
			
			String framework  = componentInfo.getComponentCreationInfo().getFramework().value();
			tool.setCommand(GalaxyToolExecutionCommand.buildExecutionCommand(framework, dataGalaxyParam.getName(), coord, fullName));

			DiscoverDatasets dd = new DiscoverDatasets();
			dd.setDirectory("out");
			dd.setPattern("__designation__");
			dd.setFormat("pdf");
			dd.setVisible("false");
			Collection collection = new Collection();
			collection.setDiscoverDatasets(dd);
			collection.setName("output");
			collection.setType("list");
			collection.setLabel(name + "_output");
			outputs.setCollection(collection);
			// info = componentInfo.getOutputResourceInfo();
			// outputs.setParams(extractInputParams(info));

			tool.setOutputs(outputs);

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

	public static String getCoordinatesFromResourceIdentifier(String resourceID){
		String coordinates = "ERROR";
		if(resourceID.startsWith("mvn:")){
			
			int end = resourceID.indexOf("#");
			coordinates = resourceID.substring("mvn:".length(), end);
		}
		
		return coordinates;
	}
	
	public static String normalizeCoordinates(String coordinates){
		return coordinates.replaceAll(":", "_");
	}
	
	public void setDefaultValue(ParameterInfo paramInfo, Param galaxyParam){
		// Set default value.
		
		if (paramInfo.getDefaultValue() != null && paramInfo.getDefaultValue().size() > 0) {
			String parameterType = paramInfo.getParameterType().value();
			String defaultValue = paramInfo.getDefaultValue().get(0);
			
			if(parameterType.equalsIgnoreCase(ParameterTypeEnum.STRING.value())){
				galaxyParam.setValue(defaultValue);
			}else if(parameterType.equalsIgnoreCase(ParameterTypeEnum.BOOLEAN.value())){
				galaxyParam.setChecked(defaultValue);
			}else if(parameterType.equalsIgnoreCase(ParameterTypeEnum.INTEGER.value())){
				galaxyParam.setValue(defaultValue);
			}else if(parameterType.equalsIgnoreCase(ParameterTypeEnum.FLOAT.value())){
				galaxyParam.setValue(defaultValue);
			}else{
				System.out.println("UKNOWN PARAMETER TYPE:" + parameterType + "use default: " + GalaxyCons.text);
			}		
			
		}
	}

	// Map OMTD-SHARE
	// https://docs.galaxyproject.org/en/master/dev/schema.html#id29
	public void setParameterType(ParameterInfo paramInfo, Param galaxyParam){
		String parameterType = paramInfo.getParameterType().value();
		
		if(parameterType.equalsIgnoreCase(ParameterTypeEnum.STRING.value())){
			galaxyParam.setType(GalaxyCons.text);
		}else if(parameterType.equalsIgnoreCase(ParameterTypeEnum.BOOLEAN.value())){
			galaxyParam.setType(GalaxyCons.BooleanT);
			System.out.println("boolean:" + paramInfo.getDefaultValue().get(0) + " -- " + paramInfo.getParameterName() );
		}else if(parameterType.equalsIgnoreCase(ParameterTypeEnum.INTEGER.value())){
			galaxyParam.setType(GalaxyCons.integerT);
			System.out.println("integer:" + paramInfo.getParameterName());
		}else if(parameterType.equalsIgnoreCase(ParameterTypeEnum.FLOAT.value())){
			galaxyParam.setType(GalaxyCons.FloatT);
			System.out.println("float:" + paramInfo.getParameterName());
		}else{
			System.out.println("UKNOWN PARAMETER TYPE:" + parameterType + "use default: " + GalaxyCons.text);
			galaxyParam.setType(GalaxyCons.text);
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

		for (ParameterInfo paramInfo : parametersInfos) {
			Param galaxyParam = new Param();

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

}
