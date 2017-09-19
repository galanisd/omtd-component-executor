package eu.openminted.workflows.galaxywrappers;

public class GalaxyToolExecutionCommand {

	public final static String UIMA = "UIMA";
	public final static String GATE = "GATE";
	public final static String ALVIS = "Alvis";
	
	public static String buildExecutionCommand(String framework, String inputDirVar, String coordinates, String componentID){
		
		System.out.println("Framework:" + UIMA);
		
		if(framework.equals(UIMA)){
			//System.out.println(UIMA);
			return buildExecutionCommandUIMA(inputDirVar, coordinates, componentID);
		}else if(framework.equals(GATE)){
			return "TO BE COMPLETED";
		}else if(framework.equals(ALVIS)){
			return "TO BE COMPLETED";
		}else{
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
		command.append("Linux_runUIMA.sh " + coordinates + " " + componentID + " tmp $output.job_working_directory/working/out/");

		return command.toString();
	}
}
