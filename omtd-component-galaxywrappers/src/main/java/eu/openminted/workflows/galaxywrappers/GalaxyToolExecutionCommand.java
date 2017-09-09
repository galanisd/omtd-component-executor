package eu.openminted.workflows.galaxywrappers;

public class GalaxyToolExecutionCommand {

	public static String buildExecutionCommand(String framework, String inputDirVar, String coordinates, String componentID){
		
		if(framework.equals("UIMA")){
			System.out.println("UIMA");
			return buildExecutionCommandUIMA(inputDirVar, coordinates, componentID);
		}else if(framework.equals("GATE")){
			return "TO BE COMPLETED";
		}
		else{
			return "NO COMMAND AVAILABLE";
		}
	}
	
	public static String buildExecutionCommandUIMA(String inputDirVar, String coordinates, String componentID) {
		StringBuilder command = new StringBuilder();
		command.append("\n");
		command.append("mkdir tmp;\n");
		command.append("#for $file in $" + inputDirVar + "\n");
		command.append("\t");
		command.append("cp $file tmp/$file.element_identifier;\n");
		command.append("#end for\n");
		command.append("bash ./Linux_runDKPro.sh " + coordinates + " " + componentID + " tmp $output.job_working_directory/working/out/");

		return command.toString();
	}
}
