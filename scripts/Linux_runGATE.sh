#!/usr/bin/env groovy

@GrabResolver(name='gate-snapshots', root='http://repo.gate.ac.uk/content/groups/public/')
@Grab(group='uk.ac.gate', module='gate-core', version='8.5-SNAPSHOT', changing=true)
import gate.*;
import gate.creole.*;
import gate.util.persistence.PersistenceManager;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter

// example command line that will run ANNIE over all documents in the input folder and store the results in output
//    groovy GATERunner.groovy "creole://uk.ac.gate.plugins;annie;8.5-SNAPSHOT/resources/ANNIE_with_defaults.gapp" input output



//Initialise GATE: must be done before you try and use any part of GATE
Gate.init()

// Assumes three command line arguments:
//    URI of the application to run
//    input file path
//    output file path
URI application = new URI(args[0]);
File input = new File(args[1]);
File output = new File(args[2]);

// sanity check time
output.mkdirs();

URL appURL;

if (application.getScheme().equals("creole")) {
	// if the app URI is a creole:// URI then work out which plugin is being referenced...
	String[] coordinates = application.getAuthority().split(";");
	Plugin plugin = new Plugin.Maven(coordinates[0],coordinates[1],coordinates[2]);

	// load the plugin into GATE...
	Gate.getCreoleRegister().registerPlugin(plugin);

	// and finally resolve the URI fully against the plugin
	ResourceReference rr = new ResourceReference(application);
	appURL = rr.toURL();
}
else {
	// if not a creole:// URI then just convert the URI to a URL
	appURL = application.toURL();
}

// load the application that we have been asked to run now that we have the right URL
ConditionalSerialAnalyserController app = PersistenceManager.loadObjectFromUrl(appURL)

// create a corpus and add the document to it
Corpus corpus = Factory.newCorpus();

Document doc = null;

// tell the app which corpus to run over
app.setCorpus(corpus);

// export the result as GATE XML to the specified output file
DocumentExporter exporter = DocumentExporter.getInstance("gate.corpora.export.GateXMLExporter");

// iterate through the input folder to find all the files for processing
Iterator<File> itInputFiles = FileUtils.iterateFiles(input, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
while (itInputFiles.hasNext()) {

	// for each input file....
	File inputFile = itInputFiles.next();	

	try {
		// load the document
		doc = Factory.newDocument(inputFile.toURI().toURL())

		// add it to the corpus
		corpus.add(doc);

		// run the app over the corpus
		app.execute();

		// build the output filename
		File outputFile = new File(output, input.toURI().relativize(inputFile.toURI()).toString()+"."+exporter.getDefaultExtension());

		// make sure the parent directory tree exists
		outputFile.getParentFile().mkdirs();

		// export the processed document
		exporter.export(doc, outputFile);
	}
	finally {
		//clean up the document no matter what happened
		corpus.remove(doc);
		Factory.deleteResource(doc);
	}
}
