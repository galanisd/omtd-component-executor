package eu.openminted.workflows.dkpro.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
		String className = args[0]; 
		String inputDir = args[1];
		String outputDir = args[2];				
			
		//String className = "de.tudarmstadt.ukp.dkpro.core.io.pdf.PdfReader";
		//String inputDir = "C:/Users/galanisd/Desktop/Dimitris/EclipseWorkspaces/ILSPMars/omtd-workflows-executor/testInput/";
		//String outputDir = "C:/Users/galanisd/Desktop/Dimitris/EclipseWorkspaces/ILSPMars/omtd-workflows-executor/testOutput/";
		
		log.info("\n\n\n\n\nExecuting..");
		log.info("========================");
		log.info("className:" + className);
		log.info("inputDir:" + inputDir);
		log.info("outputDir:" + outputDir);
		log.info("========================");
		log.info("========================");
		log.info("\n\n\n\n\n");
		
		UIMAFitRunner runner = new UIMAFitRunner();
		runner.uimaFitRun(className, inputDir, outputDir);
	}	
}


