package org.omtd.component.webservice;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiReader;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;
import eu.openminted.remoteexecution.client.RemoteComponent;

public class WebServiceClient {

	private String inputFolder;
	private String outputFolder;
	private String webServiceURL;
	
	public WebServiceClient(String inputFolder, String outputFolder, String webServiceURL) {
		this.inputFolder = inputFolder;
		this.outputFolder = outputFolder;
	}

	public void run() throws UIMAException, IOException {

		//CollectionReader reader = createReader(PdfReader.class, PdfReader.PARAM_SOURCE_LOCATION, inputFolder,
		//		PdfReader.PARAM_PATTERNS, "[+]**/*.pdf");
		
		CollectionReader reader = CollectionReaderFactory.createReader(XmiReader.class,
				ResourceCollectionReaderBase.PARAM_SOURCE_LOCATION, inputFolder,
				ResourceCollectionReaderBase.PARAM_PATTERNS, "[+]**/*.xmi");
		
		AnalysisEngine remoteComponent = createEngine(RemoteComponent.class, RemoteComponent.URL_PARAM, webServiceURL);

		AnalysisEngine writer = createEngine(XmiWriter.class, XmiWriter.PARAM_TARGET_LOCATION, outputFolder,
				XmiWriter.PARAM_OVERWRITE, Boolean.TRUE);

		SimplePipeline.runPipeline(reader, remoteComponent, writer);

	}
}
