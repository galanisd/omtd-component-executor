package eu.openminted.workflows.galaxywrappers;

import java.util.ArrayList;
import java.util.List;

import eu.openminted.registry.domain.Component;
import eu.openminted.registry.domain.ComponentDistributionInfo;

/**
 * @author ilsp
 *
 */
public class GalaxyToolExecutionCommand {
	
	private String framework;
	private String inDir;
	private String otDir;
	private List<ComponentDistributionInfo> componentDistributionInfos;
	private Component componentMeta;
	
	public GalaxyToolExecutionCommand(){
	}
	
	/**
	 * Builds component's cheetah code for Galaxy wrapper.
	 * @param inputDirVar
	 * @param coordinates
	 * @param componentID
	 * @param parameters
	 * @param componentMeta
	 * @return
	 */
	public String buildCheetahCode(String inputDirVar, String coordinates, String componentID, ArrayList<String> parameters, Component componentMeta){
		String cheetahCode = "";
		
		this.componentMeta = componentMeta;
		this.componentDistributionInfos = componentMeta.getComponentInfo().getDistributionInfos();
		
		if(Utils.isDocker(componentDistributionInfos)){
			cheetahCode = buildCheetahCodeDocker(inputDirVar, componentID, parameters);
		}else if(Utils.isWebService(componentDistributionInfos)){
			cheetahCode = buildCheetahCodeWS(inputDirVar, componentID, parameters);
		}else{
        	framework = componentMeta.getComponentInfo().getComponentCreationInfo().getFramework().value();
			System.out.println("Framework:" + framework);
			
			if(framework.equals(Framework.UIMA)){
				cheetahCode = buildCheetahCodeUIMA(inputDirVar, Utils.normalizeCoordinates(coordinates), componentID, parameters);
			}else if(framework.equals(Framework.GATE)){
				cheetahCode = buildCheetahCodeGATE(inputDirVar, coordinates, componentID, parameters);
			}else { 
				cheetahCode = "NOT FOUND";
			}			
		}
		
		return cheetahCode;
	}
	
	private String buildCheetahCodeUIMA(String inputDirVar, String coordinates, String componentID, ArrayList<String> parameters) {
		StringBuilder command = new StringBuilder();
		//command.append("<![CDATA[");
		// Copy input to a tmp dir. 
		// TO-DO: check if this is required.
		prepareTMP(inputDirVar, command);
		otDir = "$output.job_working_directory/working/out/";
		
		// Build UIMA executor command
		// * First: command -input -output
		command.append("Linux_runUIMA.sh " + coordinates + " " + Utils.getClassNameFromComponentID(componentID) + " -input tmp " + "-output " + otDir);
		// * Then: add parameters.
		command.append(galaxyParemeters(parameters));
		// * Change line.
		command.append("\n");
		//command.append("]]>");
		return command.toString();
	}
	
	private String buildCheetahCodeGATE(String inputDirVar, String coordinates, String componentID, ArrayList<String> parameters) {
		StringBuilder command = new StringBuilder();
		//command.append("<![CDATA[");
		// Copy input to a tmp dir. 
		// TO-DO: check if this is required.
		prepareTMP(inputDirVar, command);
		otDir = "$output.job_working_directory/working/out/";
		
		// Build GATE executor command
		// * First: command -input -output
		command.append("Linux_runGATE.sh " + coordinates + " " + Utils.getClassNameFromComponentID(componentID) + " tmp" + " " + otDir);
		// * Then: add parameters.
		command.append(galaxyParemeters(parameters));
		// * Change line.
		command.append("\n");
		//command.append("]]>");
		return command.toString();
	}

	private String buildCheetahCodeDocker(String inputDirVar, String componentID, ArrayList<String> parameters) {
		StringBuilder command = new StringBuilder();
		//command.append("<![CDATA[");
		// Copy input to a tmp dir. 
		// TO-DO: check if this is required.
		prepareTMP(inputDirVar, command);
		otDir = "$output.job_working_directory/working/out/";
		
		// Build Docker executor command
		// * First: command -input -output
		command.append(Utils.getCommand(componentDistributionInfos) + " --input tmp " + "--output " + otDir);
		// * Then: add parameters.
		command.append(galaxyParemetersDock(parameters));
		// * Change line.
		command.append("\n");
		//command.append("]]>");
		return command.toString();
	}

	private String buildCheetahCodeWS(String inputDirVar, String componentID, ArrayList<String> parameters) {
		StringBuilder command = new StringBuilder();
		//command.append("<![CDATA[");
		// Copy input to a tmp dir. 
		// TO-DO: check if this is required.
		prepareTMP(inputDirVar, command);
		otDir = "$output.job_working_directory/working/out/";
		
		// Build WS executor command
		// * First: command -input -output
		command.append("Linux_runWS.sh" + " -input tmp " + "-output " + otDir + " -Pwsurl=" + Utils.getURL(componentDistributionInfos) + " -Ptypesystems=" + Utils.getTypesystemsStr(componentMeta));
		// * Then: add parameters.
		// NO PARAMETERS in Web Services.
		//command.append(galaxyParemeters(parameters));
		// * Change line.
		command.append("\n");
		//command.append("]]>");
		return command.toString();
	}
	
	private void prepareTMP(String inputDirVar, StringBuilder command){
		command.append("\n");
		command.append("mkdir tmp;\n");
		command.append("#for $file in $" + inputDirVar + "\n");
		command.append("\t");
		command.append("cp $file tmp/$file.element_identifier;\n");
		command.append("#end for\n");
	}
	
	
	private String galaxyParemeters(ArrayList<String> parameters){
		StringBuilder parametersStr = new StringBuilder();
	
		for(int i = 0; i < parameters.size(); i++){
			
			String parameterName = parameters.get(i);
			
			parametersStr.append(" \n" );
			parametersStr.append("#if $"  + parameterName + "\n");
			
			String paramValue = getParamValue(parameterName);
			parametersStr.append("-P" + parameterName + "='" +  paramValue + "'\n");
			parametersStr.append("#end if");
		}

		return parametersStr.toString();
	}

	private String galaxyParemetersDock(ArrayList<String> parameters){
		StringBuilder parametersStr = new StringBuilder();
	
		for(int i = 0; i < parameters.size(); i++){
			
			String parameterName = parameters.get(i);
			
			parametersStr.append(" \n" );
			parametersStr.append("#if $"  + parameterName + "\n");
			
			String paramValue = getParamValue(parameterName);
			parametersStr.append("--param:" + parameterName + "='" +  paramValue + "'\n");
			parametersStr.append("#end if");
		}

		return parametersStr.toString();
	}
	
	private String getParamValue(String parameterName){
		String value = "$" + parameterName;
		if(framework.equals(Framework.UIMA)){
			if(parameterName.equals("targetLocation")){
				value = this.otDir + value;
			}
		}else{
			
		}
		
		return value;
	}
}
