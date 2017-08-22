package eu.openminted.workflows.galaxywrappers;

import java.io.File;

public class GalaxyWrapperGeneratorMain {

	public static void main(String args[]){
		
		GalaxyWrapperGenerator generator = new GalaxyWrapperGenerator();
		
		String path = "/home/ilsp/Desktop/omtds-dkpro-core-1.9.0-SNAPSHOT/";
		File omtdsFilesDir = new File(path);
		File [] componentFiles = omtdsFilesDir.listFiles();
		
		for(int i = 0; i < componentFiles.length; i++){
			File componentFile = componentFiles[i];
			String generatedXML = generator.generate(componentFile);
			
			System.out.println(componentFiles[i].getAbsolutePath() + "\n" + generatedXML);
		}
	}
}
