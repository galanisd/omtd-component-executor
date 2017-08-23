package eu.openminted.workflows.galaxywrappers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import eu.openminted.registry.domain.Component;
import eu.openminted.registry.domain.ComponentInfo;
import eu.openminted.registry.domain.Description;
import eu.openminted.registry.domain.ParameterInfo;
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
	
	public GalaxyWrapperGenerator(String outDir){			
		omtdshareParser = new OMTDSHAREParser();
		galaxyToolWrapperWriter = new GalaxyToolWrapperWriter();
		
		// Create wrappers output dir if not exists. 
		outDirHandler = new File(outDir);
		if(!outDirHandler.exists()){
			outDirHandler.mkdirs();
		}
	}
	
	public void generate(File omtdShareFile){
		try{
			// Parse omtd-share file.			
			Component componentMeta = omtdshareParser.parse(omtdShareFile);

			// Get info
			ComponentInfo componentInfo = componentMeta.getComponentInfo();			
			List<Description> descriptions = componentInfo.getIdentificationInfo().getDescriptions();
			
			// ** Create the tool.
			Tool tool = new Tool();
			
			// Get tool desc.
			String desc  = "";
			if(descriptions != null && descriptions.size() > 0){				
				desc = descriptions.get(0).getValue();
			}else{
				desc = componentInfo.getIdentificationInfo().getResourceNames().get(0).getValue();
			}

			// Get id.
			String id = componentInfo.getIdentificationInfo().getResourceIdentifiers().get(0).getValue();
			
			String name = componentInfo.getIdentificationInfo().getResourceNames().get(0).getValue();
					
			// Get tool version.
			String version = componentInfo.getVersionInfo().getVersion();
			
			// Configure wrapper description.
			tool.setDescription(desc);			
			tool.setId(id);
			tool.setName(name);
			tool.setVersion(version);

			// Configure wrapper requirements.
			Requirements requirements = new Requirements();
			Container container = new Container();
			container.setType("docker");
			container.setValue("omtd-workflows-executor");
			requirements.setContainer(container);
			tool.setRequirements(requirements);
			
			tool.setCommand("to be completed");
			
			// Configure wrapper inputs/outputs.
			Inputs inputs = new Inputs();
			Outputs outputs = new Outputs();
			
			ProcessingResourceInfo  info = componentInfo.getInputContentResourceInfo();
			
			ArrayList<Param> inputParams = extractInputParams(info);
			Param dataGalaxyParam = new Param();
			dataGalaxyParam.setType("data");
			dataGalaxyParam.setName(name + "_InputFiles");
			dataGalaxyParam.setLabel(name + "_InputFiles");
			dataGalaxyParam.setFormat("uknown");
			dataGalaxyParam.setMultiple("true");
			inputParams.add(dataGalaxyParam);
			
			inputs.setParams(inputParams);
			tool.setInputs(inputs);
			
			DiscoverDatasets dd = new DiscoverDatasets();
			dd.setDirectory("out");
			dd.setPattern("__designation__");
			dd.setFormat("uknown");
			dd.setVisible("false");
			Collection collection = new Collection();
			collection.setDiscoverDatasets(dd);
			collection.setName("output");
			collection.setType("list");
			collection.setLabel(name + "_output");
			outputs.setCollection(collection);
			//info = componentInfo.getOutputResourceInfo();
			//outputs.setParams(extractInputParams(info));
			 
			
			tool.setOutputs(outputs);
			
			// Serialize wrapper object to a file.
			String galaxyWrapperPath = outDirHandler.getAbsolutePath() + "/" + omtdShareFile.getName() + ".xml";
			galaxyToolWrapperWriter.write(tool, galaxyWrapperPath);
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.info(e.getMessage());
		}
	
	}
	
	private ArrayList<Param> extractInputParams(ProcessingResourceInfo info){
		
		ArrayList<Param> params = new ArrayList<Param>();		
		List<ParameterInfo> parametersInfos = info.getParameterInfos();
		
		for(ParameterInfo paramInfo : parametersInfos){
			Param galaxyParam = new Param();
			
			galaxyParam.setName(paramInfo.getParameterName());
			galaxyParam.setLabel(paramInfo.getParameterLabel());
			galaxyParam.setDescription(paramInfo.getParameterDescription());
			galaxyParam.setOptional(String.valueOf(paramInfo.isOptional()));
			
			// Set default value.
			if(paramInfo.getDefaultValue() != null && paramInfo.getDefaultValue().size() > 0){
				galaxyParam.setValue(paramInfo.getDefaultValue().get(0));
			}

			// https://docs.galaxyproject.org/en/master/dev/schema.html
			// Use only "text" for now.
			galaxyParam.setType(GalaxyCons.text);
			
			// Add it to the list.
			params.add(galaxyParam);
		}		
		
		return params;
	}
	
	

}
