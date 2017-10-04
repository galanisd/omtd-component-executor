package eu.openminted.workflows.galaxywrappers;

import java.util.ArrayList;

/**
 * @author ilsp
 *
 */
public class GalaxyToolExecutionCommand {
	
	public static String buildCheetahCode(String framework, String inputDirVar, String coordinates, String componentID, ArrayList<String> parameters){
		
		System.out.println("Framework:" + framework);
		
		if(framework.equals(Framework.UIMA)){
			//System.out.println(UIMA);
			return buildCheetahCode(inputDirVar, coordinates, componentID, parameters);
		}else if(framework.equals(Framework.GATE)){
			return "TO BE COMPLETED";
		}else if(framework.equals(Framework.ALVIS)){
			return "TO BE COMPLETED";
		}else{
			return "NO COMMAND AVAILABLE";
		}
	}
	
	private static String buildCheetahCode(String inputDirVar, String coordinates, String componentID, ArrayList<String> parameters) {
		
		StringBuilder command = new StringBuilder();
		
		//command.append("<![CDATA[");
		// Copy input to a tmp dir. 
		// TO-DO: check if this is required.
		command.append("\n");
		command.append("mkdir tmp;\n");
		command.append("#for $file in $" + inputDirVar + "\n");
		command.append("\t");
		command.append("cp $file tmp/$file.element_identifier;\n");
		command.append("#end for\n");
		
		// Build UIMA executor command
		// * First command -input -output
		command.append("Linux_runUIMA.sh " + coordinates + " " + componentID + " -input tmp " + "-output $output.job_working_directory/working/out/");
		// * Then add parameters.
		command.append(galaxyParemeters(parameters));
		// 
		command.append("\n");
		//command.append("]]>");
		return command.toString();
	}
	
	private static String galaxyParemeters(ArrayList<String> parameters){
		StringBuilder parametersStr = new StringBuilder();
	
		for(int i = 0; i < parameters.size(); i++){
			
			String parameterName = parameters.get(i);
			
			parametersStr.append(" \n" );
			parametersStr.append("#if $"  + parameterName + "\n");
			parametersStr.append("-P" + parameterName + "='" + "$" + parameterName + "'\n");
			parametersStr.append("#end if");
		}

		return parametersStr.toString();
	
	}
}
