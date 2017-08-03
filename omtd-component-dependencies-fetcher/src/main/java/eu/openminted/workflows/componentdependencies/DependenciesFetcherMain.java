package eu.openminted.workflows.componentdependencies;

import java.io.File;
import java.io.FileOutputStream;

public class DependenciesFetcherMain {

	// Fetch
	public static void main(String[] args) throws Exception {

		//String [] coordinatesList = {"org.springframework:spring-context:4.3.4.RELEASE", "org.apache.maven:maven-profile:2.2.1", "uk.ac.gate:gate-core:8.4.1", "uk.ac.gate:gate-core:8.0", "de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.io.pdf-asl:1.8.0"};
		String [] coordinatesList = {"de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.io.pdf-asl:1.8.0"};
		
		DependenciesFetcher fetcher = new DependenciesFetcher();
		
		for(int i = 0; i < coordinatesList.length; i++){

			FileOutputStream stream = new FileOutputStream("target/classpath." + coordinatesList[i].replaceAll(":", "_"));
			
			System.out.println(coordinatesList[i] + " \n\n\n");
			//stream.write( (coordinatesList[i] + "\n").getBytes());
			stream.flush();
			String classpath = fetcher.resolveDependencies(coordinatesList[i]);
			stream.write( (normalize(classpath) + "\n").getBytes());
			stream.flush();
			System.out.println(classpath);
			
			normalize(classpath);
		}

		
	}
	
	
	private static String normalize(String classpath){
		
		String finalClasspath = "";
		String jars [] = classpath.split(";");
		for(int i = 0; i < jars.length; i++){
			//System.out.println(jars[i]);
			
			File f = new File(jars[i]);
			
			if(!f.exists()){
				System.out.println(jars[i]);
			}else{
				String jarNorm = "file:///" + jars[i].replaceAll("\\\\", "/");
				if( i == 0){
					finalClasspath = jarNorm;	
				}else{
					finalClasspath = finalClasspath + ";" + jarNorm;
				}
				
			}
		}
		
		return finalClasspath;
	}
}
