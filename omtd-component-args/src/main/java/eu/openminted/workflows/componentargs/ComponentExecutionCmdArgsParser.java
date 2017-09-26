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
	
	public ComponentExecutionCmdArgsParser(){
		parser = new DefaultParser();
		buildOptions();
	}
	
	private void buildOptions(){
		
		options = new Options();
		
		Option input = Option.builder("input")
				.hasArg(true)
				//.argName("input")
                .required(true)
                .longOpt("inputFolder")
                .desc("input folder")
                .build();						

		Option output = Option.builder("output")
				.hasArg(true)
				//.argName("output")
                .required(true)
                .longOpt("output")
                .desc("output folder")
                .build();

		Option className = Option.builder("className")
				.hasArg(true)
				//.argName("className")
                .required(true)
                .longOpt("className")
                .desc(" the class name")
                .build();
		
		Option param = Option.builder("P")
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
	
	public CommandLine parse(String args[]){
		CommandLine givenCMD;
	    
		try {
	        givenCMD = parser.parse(options, args, false);
	        
	        System.err.println("input" + " " + givenCMD.getOptionValue("input"));
	        System.err.println("output" + " " + givenCMD.getOptionValue("output"));
	        System.err.println("className" + " " + givenCMD.getOptionValue("className"));
	        
	        Set<String> set = givenCMD.getOptionProperties("P").stringPropertyNames();
	        
	        Iterator<String> it = set.iterator();
	        while(it.hasNext()){
	        	String propertyName = it.next();
	        	String propertyValue = givenCMD.getOptionProperties("P").getProperty(propertyName);
	        
	        	System.err.println(propertyName + " " + propertyValue );
	        }
	        
	        return givenCMD;
	    } catch (ParseException e) {
	        System.err.println("Error parsing command line options");
	        System.err.println(e.getMessage());
	        return null;
	    }
	}
}
