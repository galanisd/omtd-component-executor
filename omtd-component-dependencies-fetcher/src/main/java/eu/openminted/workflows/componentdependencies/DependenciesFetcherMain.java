package eu.openminted.workflows.componentdependencies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileOutputStream;

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
		String dirWithJARLists = args[0];
		String repo = args[1];
		
		DependenciesFetcher fetcher = new DependenciesFetcher(repo);

		for (int i = 2; i < args.length; i++) {

			File dir = new File(dirWithJARLists);
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			FileOutputStream stream = new FileOutputStream(
					dirWithJARLists + "classpath." + args[i].replaceAll(":", "_"));

			System.out.println(args[i] + " \n\n\n");
			// stream.write( (coordinatesList[i] + "\n").getBytes());
			stream.flush();
			String classpath = fetcher.resolveDependencies(args[i]);
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
		String[] myArgs = { "../../TDMClasspathLists/", "../../TDMlocalRepo/", "de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.io.pdf-asl:1.8.0",
				"de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.opennlp-asl:1.8.0",
				"de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.arktools-gpl:1.8.0",
				"de.tudarmstadt.ukp.dkpro.core:de.tudarmstadt.ukp.dkpro.core.stanfordnlp-gpl:1.8.0" };
		app.run(myArgs);
		log.info("DONE!");
	}
}
