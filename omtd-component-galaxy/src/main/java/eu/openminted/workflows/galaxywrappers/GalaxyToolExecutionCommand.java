package eu.openminted.workflows.galaxywrappers;

import java.util.ArrayList;

public class GalaxyToolExecutionCommand {

	public final static String UIMA = "UIMA";
	public final static String GATE = "GATE";
	public final static String ALVIS = "Alvis";
	
	public static String buildExecutionCommand(String framework, String inputDirVar, String coordinates, String componentID, ArrayList<String> parameters){
		
		System.out.println("Framework:" + framework);
		
		if(framework.equals(UIMA)){
			//System.out.println(UIMA);
			return buildExecutionCommandUIMA(inputDirVar, coordinates, componentID, parameters);
		}else if(framework.equals(GATE)){
			return "TO BE COMPLETED";
		}else if(framework.equals(ALVIS)){
			return "TO BE COMPLETED";
		}else{
			return "NO COMMAND AVAILABLE";
		}
	}
	
	public static String buildExecutionCommandUIMA(String inputDirVar, String coordinates, String componentID, ArrayList<String> parameters) {
		
		StringBuilder command = new StringBuilder();
		
		// 
		command.append("\n");
		command.append("mkdir tmp;\n");
		command.append("#for $file in $" + inputDirVar + "\n");
		command.append("\t");
		command.append("cp $file tmp/$file.element_identifier;\n");
		command.append("#end for\n");
		
		// UIMA executor
		command.append("Linux_runUIMA.sh " + coordinates + " " + componentID + " tmp $output.job_working_directory/working/out/");

		// Add parameters.
		command.append(galaxyParemeters(parameters));
		return command.toString();
	}
	
	public static String galaxyParemeters(ArrayList<String> parameters){
		StringBuilder parametersStr = new StringBuilder();
	
		for(int i = 0; i < parameters.size(); i++){
			
			String parameterName = parameters.get(i);
			
			parametersStr.append(" \n" );
			parametersStr.append("#if $"  + parameterName + "\n");
			parametersStr.append("-" + parameterName + " $" + parameterName + "\n");
			parametersStr.append("#end if");
		}

		return parametersStr.toString();
	
	}
}
