package eu.openminted.workflows.uima.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import eu.openminted.workflows.componentargs.ComponentArgs;
import eu.openminted.workflows.componentargs.ComponentExecutionCmdArgsParser;
/**
 * @author galanisd
 *
 */
@SpringBootApplication
public class PipelineCommandLineRunner implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(PipelineCommandLineRunner.class);

	//private final static String READER = "READER";
	//private final static String ENGINE = "ENGINE";
	
	// == === ==	
	public static void main(String[] args) throws Exception {

		log.info("...");
		SpringApplication app = new SpringApplication(PipelineCommandLineRunner.class);
		// app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
		log.info("DONE!");
	}

	@Override
	public void run(String... args) throws Exception {
		
		ComponentExecutionCmdArgsParser argsParser = new ComponentExecutionCmdArgsParser();
		ComponentArgs componentArgs = argsParser.parse(args);
		
		log.info(componentArgs.dump());
		
		UIMAFitRunner runner = new UIMAFitRunner();
		runner.uimaFitRun(componentArgs);
	}	
}


