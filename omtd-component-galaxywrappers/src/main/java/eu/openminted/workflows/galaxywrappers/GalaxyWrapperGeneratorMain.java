package eu.openminted.workflows.galaxywrappers;

import java.io.File;

import eu.openminted.workflows.galaxytool.Tool;

public class GalaxyWrapperGeneratorMain {

	public static void main(String args[]){		
		//String path = "/home/ilsp/Desktop/omtds-dkpro-core-1.9.0-SNAPSHOT";		
		
		String root = "C:/Users/galanisd/Desktop/OMTDSHARE_GalaxyWrappers/";
		String galaxyWrappersFolder = "omtdDKPro";
		String path = root + "omtds-dkpro-core-1.9.0-SNAPSHOT";				
		String outPath = path + "_" + "wrappers/"; 
		
		GalaxyWrapperGenerator galaxyWrapperGenerator = new GalaxyWrapperGenerator(outPath);
		GalaxySectionGenerator galaxySectionGenerator = new GalaxySectionGenerator(galaxyWrappersFolder, galaxyWrappersFolder);
		
		File omtdsFilesDir = new File(path + "/");
		File [] componentFiles = omtdsFilesDir.listFiles();
		
		for(int i = 0; i < componentFiles.length; i++){
			File componentFile = componentFiles[i];
			
			System.out.println("\n" + componentFiles[i].getAbsolutePath() + " ...start processing..." );
			Tool tool = galaxyWrapperGenerator.generate(componentFile);			
			System.out.println(componentFiles[i].getAbsolutePath() + " ... was processed");
			
			galaxySectionGenerator.addTool(galaxyWrappersFolder + "/" + componentFile.getName() + ".xml");
		}
		
		galaxySectionGenerator.write(root + "section.xml");
	}
}
