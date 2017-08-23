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
import eu.openminted.workflows.galaxytool.Container;
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
	
	public String generate(File omtdShareFile){
		String xmlFile = "";
		try{
			// Parse omtd-share file.			
			Component componentMeta = omtdshareParser.parse(omtdShareFile);

			// Get info
			ComponentInfo componentInfo = componentMeta.getComponentInfo();			
			List<Description> descriptions = componentInfo.getIdentificationInfo().getDescriptions();
			
			// ** Create the tool
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
			
			// Configure wrapper description
			tool.setDescription(desc);			
			tool.setId(id);
			tool.setName(name);
			tool.setVersion(version);

			// Configure wrapper requirements
			Requirements requirements = new Requirements();
			Container container = new Container();
			container.setType("docker");
			container.setValue("omtd-simple-workflows-docker");
			requirements.setContainer(container);
			tool.setRequirements(requirements);
			
			tool.setCommand("to be completed");
			
			Inputs inputs = new Inputs();
			Outputs outputs = new Outputs();
			
			ProcessingResourceInfo  info = componentInfo.getInputContentResourceInfo();
			inputs.setParams(extractParams(info));
			
			info = componentInfo.getOutputResourceInfo();
			outputs.setParams(extractParams(info));
			 
			tool.setInputs(inputs);
			tool.setOutputs(outputs);
			
			String galaxyWrapperPath = outDirHandler.getAbsolutePath() + "/" + omtdShareFile.getName() + ".xml";
			galaxyToolWrapperWriter.write(tool, galaxyWrapperPath);
			
			
		}catch(Exception e){
			e.printStackTrace();
			log.info(e.getMessage());
			return "ERROR";
		}
		
		return xmlFile;
	}
	
	private ArrayList<Param> extractParams(ProcessingResourceInfo info){
		ArrayList<Param> params = new ArrayList<Param>();
		
		List<ParameterInfo> parametersInfos = info.getParameterInfos();
		
		for(ParameterInfo paramInfo : parametersInfos){
			Param param = new Param();
			
			param.setName(paramInfo.getParameterName());
			params.add(param);
		}
		
		
		return params;
	}
}
