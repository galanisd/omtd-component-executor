package eu.openminted.workflows.galaxywrappers;

import java.io.File;
import java.io.FileOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

import eu.openminted.workflows.galaxytool.Tool;


public class GalaxyWrapperGeneratorMain implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(GalaxyWrapperGeneratorMain.class);
	
	@Override
	public void run(String... args) throws Exception {		

		String root = "/home/ilsp/Desktop/OMTDTemp/";		
		String omtdShareDescFolder = "omtds-dkpro-core-1.9.0-SNAPSHOT";			
		String galaxyWrappersFolderInGalaxy = "omtdDKPro";
		 
		//String root = "/home/ilsp/Desktop/OMTDTemp/";		
		//String omtdShareDescFolder = root + "annie-descriptors";			
		//String galaxyWrappersFolderInGalaxy = "omtdGATE"
		
		//String root = args[0];
		//String galaxyWrappersFolderInGalaxy = args[1];
		//String omtdShareDescFolder = args[2];
			
		// --- 
		String omtdShareDescFolderAbsolute = root + omtdShareDescFolder;
		String outPath = omtdShareDescFolderAbsolute + "_" + "wrappers/"; 	
		String coordinatesPath = root + galaxyWrappersFolderInGalaxy + "coordinates.list";
		
		GalaxyWrapperGenerator galaxyWrapperGenerator = new GalaxyWrapperGenerator(outPath);
		GalaxySectionGenerator galaxySectionGenerator = new GalaxySectionGenerator(galaxyWrappersFolderInGalaxy, galaxyWrappersFolderInGalaxy);
		FileOutputStream coordinatesFOS = new FileOutputStream(coordinatesPath); 
		
		File omtdsFilesDir = new File(omtdShareDescFolderAbsolute + "/");
		File [] componentFiles = omtdsFilesDir.listFiles();
		
		for(int i = 0; i < componentFiles.length; i++){
			File componentFile = componentFiles[i];
			
			System.out.println("\n" + componentFiles[i].getAbsolutePath() + " ...start processing..." );
			Tool tool = galaxyWrapperGenerator.generate(componentFile);			
			String componentID = galaxyWrapperGenerator.getComponentID();
			String coordinates = GalaxyWrapperGenerator.getCoordinatesFromResourceIdentifier(componentID);
			
			coordinatesFOS.write((coordinates + "\n").getBytes());
			coordinatesFOS.flush();
			System.out.println(componentFiles[i].getAbsolutePath() + " ... was processed");
			
			
			galaxySectionGenerator.addTool(galaxyWrappersFolderInGalaxy + "/" + componentFile.getName() + ".xml");
		}
		
		galaxySectionGenerator.write(root + "section.xml");		
		coordinatesFOS.close();
	}
	
	// ---
	public static void main(String args[]){
		
		log.info("...");
		SpringApplication app = new SpringApplication(GalaxyWrapperGeneratorMain.class);
		app.setWebEnvironment(false);
		// app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
		log.info("DONE!");
	}
}