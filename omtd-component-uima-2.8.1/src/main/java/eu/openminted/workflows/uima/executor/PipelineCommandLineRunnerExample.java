package eu.openminted.workflows.uima.executor;

import org.springframework.boot.SpringApplication;

public class PipelineCommandLineRunnerExample {

	// == === ==	
	public static void main(String[] args) throws Exception {

		String className = "de.tudarmstadt.ukp.dkpro.core.io.pdf.PdfReader";
		String inputDir = "C:/Users/galanisd/Desktop/Dimitris/EclipseWorkspaces/ILSPMars/omtd-component-executor/testInput/";
		String outputDir = "C:/Users/galanisd/Desktop/Dimitris/EclipseWorkspaces/ILSPMars/omtd-component-executor/testOutput/";
		
		SpringApplication app = new SpringApplication(PipelineCommandLineRunner.class);
		String testArgs [] = {"-className" , className, "-input", inputDir, "-output", outputDir, "-Plang=en", "-Pmodel=stanford"};
		app.run(testArgs);
	}
}
