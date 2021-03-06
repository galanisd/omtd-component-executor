package eu.openminted.workflows.galaxy;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;

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
import eu.openminted.workflows.galaxywrappers.GalaxyWrapperGenerator;
import eu.openminted.workflows.galaxywrappers.Utils;


public class GalaxyWrapperGeneratorMain implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(GalaxyWrapperGeneratorMain.class);
	
	private static String wrappersFolderSuffix = "_" + "wrappers/";
	private static String mavenCoordinatesList = "coordinates.list";
	private static String jobConfFile = "jobConf";
	private static String sectionFile = "section.xml";
	
	@Override
	public void run(String... args) throws Exception {		
		
		System.out.println("len:" + args.length + "->" + Arrays.toString(args));
		
		// --- 
		String root = args[0];
		String omtdShareDescFolder = args[1];
		String galaxyWrappersFolderInGalaxy = args[2];
		String dockerImage=args[3];
		String suffix=args[4];
		// --- 
		String omtdShareDescFolderAbsolute = root + omtdShareDescFolder;
		String outPath = omtdShareDescFolderAbsolute + wrappersFolderSuffix; 	
		String coordinatesPath = root + galaxyWrappersFolderInGalaxy + mavenCoordinatesList;
		
		// --- 
		GalaxyWrapperGenerator galaxyWrapperGenerator = new GalaxyWrapperGenerator(outPath);
		galaxyWrapperGenerator.setDockerImage(dockerImage);
		GalaxySectionGenerator galaxySectionGenerator = new GalaxySectionGenerator();
		galaxySectionGenerator.addSection(galaxyWrappersFolderInGalaxy, galaxyWrappersFolderInGalaxy, GalaxySectionGenerator.ToolList_Type);
		
		
		FileOutputStream coordinatesFOS = new FileOutputStream(coordinatesPath); 
		FileOutputStream jobConf = new FileOutputStream(root + jobConfFile); 
		File omtdsFilesDir = new File(omtdShareDescFolderAbsolute + "/");
		
		// --- 
		System.out.println("descriptor:" + omtdsFilesDir.getAbsolutePath() );
		File [] componentFiles = omtdsFilesDir.listFiles();
		System.out.println("|component files| = " + componentFiles.length);
		
		// for each file
		for(int i = 0; i < componentFiles.length; i++){
			File componentFile = componentFiles[i];
			
			System.out.println("\n" + componentFiles[i].getAbsolutePath() + " ...start processing..." );
			Tool tool = galaxyWrapperGenerator.generate(componentFile, suffix);			
			String componentID = galaxyWrapperGenerator.getComponentID();
			String coordinates = Utils.getMVNCoordinatesFromResourceIdentifier(componentID);
			
			coordinatesFOS.write((coordinates + "\n").getBytes());
			coordinatesFOS.flush();
			
			String jobConfEntry = "<tool id=\"" + componentID + "\" destination=\"chronos_dest\"/>" + "\n";
			jobConf.write(jobConfEntry.getBytes());
			jobConf.flush();
			System.out.println(componentFiles[i].getAbsolutePath() + " ... was processed");
			
			galaxySectionGenerator.addTool(galaxyWrappersFolderInGalaxy + "/" + componentFile.getName() + ".xml");
		}
		
		galaxySectionGenerator.write(root + sectionFile);		
		coordinatesFOS.close();
		jobConf.close();
	}
	
	// ---
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