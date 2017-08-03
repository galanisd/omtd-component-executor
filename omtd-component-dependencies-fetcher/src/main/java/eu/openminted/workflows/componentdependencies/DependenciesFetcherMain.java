package eu.openminted.workflows.componentdependencies;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author ilsp
 *
 */
public class DependenciesFetcherMain {
	
	private static String prepapeForSpringBootExecutor(String classpath){
		
		String listofJARSForSpringBoot = "";
		String jars [] = classpath.split(":");
		
		System.out.println("length:" + jars.length);
		for(int i = 0; i < jars.length; i++){
			//System.out.println(jars[i]);
			
			File f = new File(jars[i]);
			
			if(!f.exists() || f.getName().startsWith("spring-")){
				System.out.println("FIlTER" + jars[i]);
			}else{
				String jarNorm = "" + jars[i];
				if( i == 0){
					listofJARSForSpringBoot = jarNorm;	
				}else{
					listofJARSForSpringBoot = listofJARSForSpringBoot + "," + jarNorm;
				}
				
			}
		}
		
		return listofJARSForSpringBoot;
	}
	
	// --
	// Fetch
	public static void main(String[] args) throws Exception {

		//String [] coordinatesList = {"org.springframework:spring-context:4.3.4.RELEASE", "org.apache.maven:maven-profile:2.2.1", "uk.ac.gate:gate-core:8.4.1", "uk.ac.gate:gate-core:8.0", "de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.io.pdf-asl:1.8.0"};
		String [] coordinatesList = {"de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.io.pdf-asl:1.8.0", "de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.opennlp-asl:1.8.0"};
		
		DependenciesFetcher fetcher = new DependenciesFetcher();
		
		for(int i = 0; i < coordinatesList.length; i++){

			FileOutputStream stream = new FileOutputStream("../TDMClasspathLists/classpath." + coordinatesList[i].replaceAll(":", "_"));
			
			System.out.println(coordinatesList[i] + " \n\n\n");
			//stream.write( (coordinatesList[i] + "\n").getBytes());
			stream.flush();
			String classpath = fetcher.resolveDependencies(coordinatesList[i]);
			stream.write( (prepapeForSpringBootExecutor(classpath) + "\n").getBytes());
			stream.flush();
			//System.out.println(classpath);
		}
	}
}
