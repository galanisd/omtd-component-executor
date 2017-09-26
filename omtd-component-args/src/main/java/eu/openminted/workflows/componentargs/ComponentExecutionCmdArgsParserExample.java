package eu.openminted.workflows.componentargs;

public class ComponentExecutionCmdArgsParserExample {
	
	public static void main(String args[]){
		String testArgs [] = {"-input", "inF", "-output", "outF", "-className" , "eu.openminted.MyLemmatizer"};		
		//String testArgs [] = {"-input", "inF", "-output", "outF", "-clssName" , "eu.openminted.MyLemmatizer"};
		
		ComponentExecutionCmdArgsParser parser = new ComponentExecutionCmdArgsParser();
		parser.parse(testArgs);
		
	}
}
