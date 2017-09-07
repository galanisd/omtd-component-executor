package eu.openminted.workflows.dkpro.executor;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;

import java.io.File;

import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiReader;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;

public class UIMAFitRunner {
	
	public void uimaFitRun(String className, String inputDir, String outputDir) throws Exception{
		
		// The pipeline should have a writer.
		createOutputFolder(outputDir);		
		AnalysisEngine writerEngine = createEngine(XmiWriter.class, XmiWriter.PARAM_TARGET_LOCATION, outputDir,
				XmiWriter.PARAM_OVERWRITE, Boolean.TRUE);
		
		// The pipeline should have a reader.
		CollectionReader reader = null;
		
		// The pipeline should have anaysis engines.
		AnalysisEngine [] engines = null;
		
		// An uknown class.		
		Class<?> klass = Class.forName(className);
		
		// Check if the uknown class klass is a UIMA Reader.
		Class<? extends CollectionReader> classThatExtendsCollectionReader = getReader(klass);
		
		// If so, only read and write. (Read -> Write)
		if(classThatExtendsCollectionReader != null){	
			//reader = CollectionReaderFactory.createReader(classThatExtendsCollectionReader, ResourceCollectionReaderBase.PARAM_SOURCE_LOCATION, inputDir);			
			reader = CollectionReaderFactory.createReader(classThatExtendsCollectionReader, ResourceCollectionReaderBase.PARAM_SOURCE_LOCATION, inputDir, 
					ResourceCollectionReaderBase.PARAM_PATTERNS, "[+]**/*.pdf", // hardwired: TO-BE-CHANGED
					ResourceCollectionReaderBase.PARAM_LANGUAGE, "en"); // hardwired: TO-BE-CHANGED

			engines = new AnalysisEngine[1];
			engines[0] = writerEngine;
	
		}else{// Otherwise: Read -> Process -> Write
			reader = CollectionReaderFactory.createReader(XmiReader.class, ResourceCollectionReaderBase.PARAM_SOURCE_LOCATION, inputDir, 
					ResourceCollectionReaderBase.PARAM_PATTERNS, "[+]**/*.xmi");
			
			AnalysisEngine componentEngine = createEngine(getComponent(klass));
					
			engines = new AnalysisEngine[2];			
			engines[0] = componentEngine;
			engines[1] = writerEngine;
		}
			
		SimplePipeline.runPipeline(reader, engines);
		
	}

	public Class<? extends CollectionReader> getReader(Class<?> klass){
		try{
			Class<? extends CollectionReader> classThatExtendsCollectionReader = klass.asSubclass(CollectionReader.class);
			return classThatExtendsCollectionReader;
		}catch(ClassCastException e){
			return null;
		}
	}
	
	public Class<? extends AnalysisComponent> getComponent(Class<?> klass){
		try{
			Class<? extends AnalysisComponent> classThatExtendsAnalysisComponent = klass.asSubclass(AnalysisComponent.class);
			return classThatExtendsAnalysisComponent;
		}catch(ClassCastException e){
			return null;
		}
	}
	//XmiReader.class
	private File createOutputFolder(String outputFolder){
		File outputFolderFile = new File(outputFolder);

		if (!outputFolderFile.exists()) {
			outputFolderFile.mkdirs();
		}
		
		return outputFolderFile;
	}

}
