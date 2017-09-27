package eu.openminted.workflows.componentargs;

import java.util.Iterator;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author galanisd
 *
 */
public class ComponentExecutionCmdArgsParser {

	private CommandLineParser parser;
	private Options options;
	
	public final static String INPUT = "input";
	public final static String OUTPUT = "output";
	public final static String CLASSNAME = "className";
	public final static String PARAMETER = "P";
	
	public ComponentExecutionCmdArgsParser(){
		parser = new DefaultParser();
		buildOptions();
	}
	
	private void buildOptions(){
		
		options = new Options();
		
		Option input = Option.builder(INPUT)
				.hasArg(true)
				//.argName("input")
                .required(true)
                .longOpt("inputFolder")
                .desc("input folder")
                .build();						

		Option output = Option.builder(OUTPUT)
				.hasArg(true)
				//.argName("output")
                .required(true)
                .longOpt("output")
                .desc("output folder")
                .build();

		Option className = Option.builder(CLASSNAME)
				.hasArg(true)
				//.argName("className")
                .required(true)
                .longOpt("className")
                .desc(" the class name")
                .build();
		
		Option param = Option.builder(PARAMETER)
				.argName("key=value")
				.required(false)
				.hasArgs()
				.numberOfArgs(2)
				.valueSeparator('=')
				.desc("sample")
				.build();

						
		options.addOption(input);
		options.addOption(output);
		options.addOption(className);
		options.addOption(param);
				
	}
	
	public ComponentArgs parse(String args[]){
		
		ComponentArgs cmdIN = new ComponentArgs();
		
		CommandLine givenCMD;
	    
		try {
	        givenCMD = parser.parse(options, args, false);
	        
	        cmdIN.setInput(givenCMD.getOptionValue(INPUT));
	        System.err.println("input" + " " + cmdIN.getInput());
	        
	        cmdIN.setOutput(givenCMD.getOptionValue(OUTPUT));
	        System.err.println("output" + " " + cmdIN.getOutput());
	        
	        cmdIN.setClassName(givenCMD.getOptionValue(CLASSNAME));
	        System.err.println("className" + " " + cmdIN.getClassName());
	        
	        Set<String> set = givenCMD.getOptionProperties(PARAMETER).stringPropertyNames();
	        
	        Iterator<String> it = set.iterator();
	        while(it.hasNext()){
	        	String propertyName = it.next();
	        	String propertyValue = givenCMD.getOptionProperties(PARAMETER).getProperty(propertyName);	        
	        	System.err.println(propertyName + " " + propertyValue );
	        	cmdIN.getParameters().put(propertyName, propertyValue);
	        }
	        
	        return cmdIN;
	    } catch (ParseException e) {
	        System.err.println("Error parsing command line options");
	        System.err.println(e.getMessage());
	        return null;
	    }
	}
}
