package eu.openminted.workflows.componentdependencies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author ilsp
 *
 */
@SpringBootApplication
public class DependenciesFetcherMain implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger(DependenciesFetcherMain.class);
	
	private static String prepapeForSpringBootExecutor(String classpath) {

		String listofJARSForSpringBoot = "";
		String jars[] = classpath.split(":");

		System.out.println("length:" + jars.length);
		for (int i = 0; i < jars.length; i++) {
			// System.out.println(jars[i]);

			File f = new File(jars[i]);

			if (!f.exists() || f.getName().startsWith("spring-")) {
				System.out.println("FIlTER" + jars[i]);
			} else {
				String jarNorm = "" + jars[i];
				if (i == 0) {
					listofJARSForSpringBoot = jarNorm;
				} else {
					listofJARSForSpringBoot = listofJARSForSpringBoot + "," + jarNorm;
				}
			}
		}

		return listofJARSForSpringBoot;
	}

	@Override
	public void run(String... args) throws Exception {

		// input
		ArrayList<String> coordinatesList = getCoordinatesList(args[0]);
		
		// outputs
		String dirWithJARLists = args[1];
		String repo = args[2];
		
		DependenciesFetcher fetcher = new DependenciesFetcher(repo);

		File dir = new File(dirWithJARLists);
		if(!dir.exists()){
			dir.mkdirs();
		}
		
		// For each 
		for (int i = 0; i < coordinatesList.size(); i++) {	
			
			String coordinates = coordinatesList.get(i);
			FileOutputStream stream = new FileOutputStream(
					dirWithJARLists + "classpath." + coordinates.replaceAll(":", "_"));

			System.out.println(coordinatesList.get(i) + " \n\n\n");
			// stream.write( (coordinatesList[i] + "\n").getBytes());
			stream.flush();
			String classpath = fetcher.resolveDependencies(coordinates);
			stream.write((prepapeForSpringBootExecutor(classpath) + "\n").getBytes());
			stream.flush();
			// System.out.println(classpath);
		}	 
	}
	
	// == === ==
	public static void main(String[] args) throws Exception {

		log.info("...");
		SpringApplication app = new SpringApplication(DependenciesFetcherMain.class);
		// app.setBannerMode(Banner.Mode.OFF);
		
		// String [] coordinatesList =
		// {"org.springframework:spring-context:4.3.4.RELEASE",
		// "org.apache.maven:maven-profile:2.2.1", "uk.ac.gate:gate-core:8.4.1",
		// "uk.ac.gate:gate-core:8.0",
		// "de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.io.pdf-asl:1.8.0"};
		
		//String[] myArgs = { "../../TDMClasspathLists/", "../../TDMlocalRepo/", "de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.io.pdf-asl:1.8.0",
		//		"de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.opennlp-asl:1.8.0",
		//		"de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.arktools-gpl:1.8.0",
		//		"de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.stanfordnlp-gpl:1.8.0" };
		
		//String[] myArgs = { "../../TDMClasspathLists/", "../../TDMlocalRepo/", "../../TDMCoordinatesList.txt"};
		//app.run(myArgs);
		
		app.run(args);
		log.info("DONE!");
	}
	
	private ArrayList<String> getCoordinatesList(String file){
		 
		ArrayList<String> cList = new ArrayList<String>();
		String thisLine = null;
	
		  try {
		     BufferedReader br = new BufferedReader(new FileReader(new File(file)));
		     
		     while ((thisLine = br.readLine()) != null) {
		    	cList.add(thisLine);
		        log.info(thisLine);
		     }       
		  } catch(Exception e) {
		     e.printStackTrace();
		  }
	      
	      return cList;
	   }
}
