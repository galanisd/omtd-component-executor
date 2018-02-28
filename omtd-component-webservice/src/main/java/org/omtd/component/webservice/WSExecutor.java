package org.omtd.component.webservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import eu.openminted.workflows.componentargs.ComponentArgs;
import eu.openminted.workflows.componentargs.ComponentExecutionCmdArgsParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class WSExecutor implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(WSExecutor.class);
	private static final String URL = "wsurl";
	
	// == === ==	
	public static void main(String[] args) throws Exception {

		log.info("...");
		SpringApplication app = new SpringApplication(WebServiceClient.class);
		// app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
		log.info("DONE!");
	}

	@Override
	public void run(String... args) throws Exception {

		ComponentExecutionCmdArgsParser argsParser = new ComponentExecutionCmdArgsParser();
		ComponentArgs componentArgs = argsParser.parse(args);
		log.info(componentArgs.dump());
		
		// Get web service url.
		String url = componentArgs.getParameters().get(URL);
		WebServiceClient ws = new WebServiceClient(componentArgs.getInput(), componentArgs.getOutput(), url);
		ws.run();
	}
}
