package eu.openminted.workflows.componentargs;

import java.util.HashMap;


public class ComponentArgs {
	
	private String input;
	private String output;
	private String className;
	
	private HashMap<String, String> parameters;

	public ComponentArgs(){
		parameters = new HashMap<String, String>();
	}
	
	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public HashMap<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}
	
	public String print(){					
		StringBuilder sb = new StringBuilder();
		sb.append("\n\n\n\n\nExecuting.." + "\n");
		sb.append("========================" + "\n");
		sb.append("className:" + className + "\n");
		sb.append("inputDir:" + input + "\n");
		sb.append("outputDir:" + output + "\n");
		sb.append("========================" + "\n");
		sb.append("========================" + "\n");
		sb.append("\n\n\n\n\n");
		
		return sb.toString();
	}
}
