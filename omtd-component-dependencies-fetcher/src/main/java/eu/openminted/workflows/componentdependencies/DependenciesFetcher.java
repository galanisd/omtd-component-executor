package eu.openminted.workflows.componentdependencies;

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
	private String rootRepo;
	
	//public DependenciesFetcher(){
	//	this("../TDMlocalRepo");
	//}
	
	public DependenciesFetcher(String rootRepo){
		this.rootRepo = rootRepo;
		repoSystem = newRepositorySystem();
		session = newSession(repoSystem, rootRepo);
	}
	
	public String resolveDependencies(String coordinates) throws Exception {
		Dependency dependency = new Dependency(new DefaultArtifact(coordinates), "compile");
		
		RemoteRepository central = new RemoteRepository.Builder("central", "default", "http://repo1.maven.org/maven2/")
				.build();

		RemoteRepository zoid = new RemoteRepository.Builder("zoid", null, "http://zoidberg.ukp.informatik.tu-darmstadt.de/artifactory/public-snapshots/")
				.build();

		CollectRequest collectRequest = new CollectRequest();
		collectRequest.setRoot(dependency);
		
		collectRequest.addRepository(central);
		collectRequest.addRepository(zoid);
		
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

	private static RepositorySystemSession newSession(RepositorySystem system, String rootRepo) {
		DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();

		LocalRepository localRepo = new LocalRepository(rootRepo);
		session.setLocalRepositoryManager(system.newLocalRepositoryManager(session, localRepo));

		return session;
	}
	

}
