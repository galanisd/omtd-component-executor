package eu.openminted.simpleworkflows.dkpro.componentdependencies;

import java.io.FileOutputStream;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.util.graph.visitor.PreorderNodeListGenerator;

/**
 * @author galanisd
 *
 */
public class DependenciesFetcher {

	private RepositorySystem repoSystem;
	private RepositorySystemSession session;
	
	public DependenciesFetcher(){
		repoSystem = newRepositorySystem();
		session = newSession(repoSystem);
	}
	
	public String resolveDependencies(String coordinates) throws Exception {
		Dependency dependency = new Dependency(new DefaultArtifact(coordinates), "compile");
		RemoteRepository central = new RemoteRepository.Builder("central", "default", "http://repo1.maven.org/maven2/")
				.build();

		CollectRequest collectRequest = new CollectRequest();
		collectRequest.setRoot(dependency);
		collectRequest.addRepository(central);
		DependencyNode node = repoSystem.collectDependencies(session, collectRequest).getRoot();

		DependencyRequest dependencyRequest = new DependencyRequest();
		dependencyRequest.setRoot(node);

		repoSystem.resolveDependencies(session, dependencyRequest);

		PreorderNodeListGenerator nlg = new PreorderNodeListGenerator();
		node.accept(nlg);
		
		String classpath = nlg.getClassPath();				
		return classpath;
	}

	private static RepositorySystem newRepositorySystem() {
		DefaultServiceLocator locator = MavenRepositorySystemUtils.newServiceLocator();
		locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
		locator.addService(TransporterFactory.class, FileTransporterFactory.class);
		locator.addService(TransporterFactory.class, HttpTransporterFactory.class);

		return locator.getService(RepositorySystem.class);
	}

	private static RepositorySystemSession newSession(RepositorySystem system) {
		DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();

		LocalRepository localRepo = new LocalRepository("target/local-repo");
		session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));

		return session;
	}
	
	// Fetch
	public static void main(String[] args) throws Exception {

		String [] coordinatesList = {"org.springframework:spring-context:4.3.4.RELEASE", "org.apache.maven:maven-profile:2.2.1", "uk.ac.gate:gate-core:8.4.1"};

		FileOutputStream stream = new FileOutputStream("target/classpaths.list");
		
		DependenciesFetcher fetcher = new DependenciesFetcher();
		
		for(int i = 0; i < coordinatesList.length; i++){
			
			System.out.println(coordinatesList[i] + " \n\n\n");
			stream.write( (coordinatesList[i] + "\n").getBytes());
			stream.flush();
			String classpath = fetcher.resolveDependencies(coordinatesList[i]);
			stream.write( (classpath + "\n").getBytes());
			stream.flush();
			System.out.println(classpath);
		}
		
	}
}
