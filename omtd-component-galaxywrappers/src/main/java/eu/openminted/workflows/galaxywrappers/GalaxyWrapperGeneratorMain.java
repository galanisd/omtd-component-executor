package eu.openminted.workflows.galaxywrappers;

import java.io.File;

public class GalaxyWrapperGeneratorMain {

	public static void main(String args[]){		
		//String path = "/home/ilsp/Desktop/omtds-dkpro-core-1.9.0-SNAPSHOT";		
		String path = "C:/Users/galanisd/Desktop/OMTDSHARE_GalaxyWrappers/omtds-dkpro-core-1.9.0-SNAPSHOT";		
		
		String outPath = path + "_" + "wrappers/"; 
		GalaxyWrapperGenerator generator = new GalaxyWrapperGenerator(outPath);
		
		File omtdsFilesDir = new File(path + "/");
		File [] componentFiles = omtdsFilesDir.listFiles();
		
		for(int i = 0; i < componentFiles.length; i++){
			File componentFile = componentFiles[i];
			
			System.out.println(componentFiles[i].getAbsolutePath() + " ...start processing...\n" );
			String generatedXML = generator.generate(componentFile);			
			System.out.println(componentFiles[i].getAbsolutePath() + "\n" + generatedXML);
		}
	}
}
