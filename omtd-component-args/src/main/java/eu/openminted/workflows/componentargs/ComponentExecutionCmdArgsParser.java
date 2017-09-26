package eu.openminted.workflows.componentargs;

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
		
		options.addOption(input);
		options.addOption(output);
		options.addOption(className);
				
	}
	
	public CommandLine parse(String args[]){
		CommandLine givenCMD;
	    
		try {
	        givenCMD = parser.parse(options, args, false);
	        System.err.println(givenCMD.getArgList().size());
	        
	        System.err.println(givenCMD.hasOption("input") + " " + givenCMD.getOptionValue("input"));
	        System.err.println(givenCMD.hasOption("output") + " " + givenCMD.getOptionValue("output"));
	        System.err.println(givenCMD.hasOption("className") + " " + givenCMD.getOptionValue("className"));
	        
	        return givenCMD;
	    } catch (ParseException e) {
	        System.err.println("Error parsing command line options");
	        System.err.println(e.getMessage());
	        return null;
	    }
	}
}
