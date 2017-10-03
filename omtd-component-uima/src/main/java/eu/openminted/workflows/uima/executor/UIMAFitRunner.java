package eu.openminted.workflows.uima.executor;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.uima.analysis_component.AnalysisComponent;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.CollectionReaderFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;

import de.tudarmstadt.ukp.dkpro.core.api.io.ResourceCollectionReaderBase;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiReader;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;
import eu.openminted.workflows.componentargs.ComponentArgs;

/**
 * @author galanisd
 *
 */
public class UIMAFitRunner {
	
	public void uimaFitRun(ComponentArgs componentArgs) throws Exception{
		
		String className = componentArgs.getClassName();
		String inputDir = componentArgs.getInput();
		String outputDir = componentArgs.getOutput();
		
		// The pipeline should have a writer.
		createOutputFolder(outputDir);		
		AnalysisEngine writerEngine = createEngine(XmiWriter.class, XmiWriter.PARAM_TARGET_LOCATION, outputDir,
				XmiWriter.PARAM_OVERWRITE, Boolean.TRUE);
		
		// The pipeline should have a reader.
		CollectionReader reader = null;
		
		// The pipeline should have anaysis engines.
		AnalysisEngine [] engines = null;
		
		// An uknown class.		
		Class<?> uimaClass = Class.forName(className);
		
		// Check if the uknown class klass is a UIMA Reader.
		Class<? extends CollectionReader> classThatExtendsCollectionReader = getReader(uimaClass);
		
		// If so, only read and write. (Read -> Write)
		if(classThatExtendsCollectionReader != null){	
			
			// this works but some things are hardwired.
			//reader = CollectionReaderFactory.createReader(classThatExtendsCollectionReader, 
			//			ResourceCollectionReaderBase.PARAM_SOURCE_LOCATION, inputDir, 
			//			ResourceCollectionReaderBase.PARAM_PATTERNS, "[+]**/*.pdf", // hardwired: TO-BE-CHANGED
			//			ResourceCollectionReaderBase.PARAM_LANGUAGE, "en"); // hardwired: TO-BE-CHANGED

			// !!
			reader = CollectionReaderFactory.createReader(classThatExtendsCollectionReader, getParamsForUIMA(uimaClass, componentArgs, true));					
			
			engines = new AnalysisEngine[1];
			engines[0] = writerEngine;
	
		}else{// Otherwise: Read -> Process -> Write
			
			
			reader = CollectionReaderFactory.createReader(XmiReader.class, 
						ResourceCollectionReaderBase.PARAM_SOURCE_LOCATION, inputDir, 
						ResourceCollectionReaderBase.PARAM_PATTERNS, "[+]**/*.xmi");
			
			// 	this works but some things are hardwired.
			// AnalysisEngine componentEngine = createEngine(getComponent(klass));
			
			// !!
			AnalysisEngine componentEngine = createEngine(getComponent(uimaClass), getParamsForUIMA(uimaClass, componentArgs, false));
					
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

	public static Object[] getParamsForUIMA(Class<?> aClass, ComponentArgs componentArgs, boolean includeInput){
				
		Object[] nonInputParameters = getParamsForUIMA(aClass, componentArgs);
		
		if(includeInput){							
			// Input params	
			// We need a 2 * numOfUIMAParams table for them. 
			Object[] inputParam = new Object[2];			
			inputParam[0] = ResourceCollectionReaderBase.PARAM_SOURCE_LOCATION;
			inputParam[1] = componentArgs.getInput();
			
			Object[] allParameters = Stream.concat(Arrays.stream(inputParam), Arrays.stream(nonInputParameters)).toArray(Object[]::new);
			return allParameters;
		}else{
			return nonInputParameters;
		}
				
	}
	
	public static Object[] getParamsForUIMA(Class<?> aClass, ComponentArgs componentArgs){
		int numOfUIMAParams = componentArgs.getParameters().size();
		
		// We need a 2 * numOfUIMAParams table for them. 
		Object[] params = new Object[ 2 * numOfUIMAParams];		
				
		Iterator<String> it = componentArgs.getParameters().keySet().iterator();
		for(int i = 0; i < params.length; i = i + 2){
			String paramName = it.next();
			String paramValue = componentArgs.getParameters().get(paramName);
			
			params[i] = paramName;
			//params[i+1] = paramValue;
			setParamValue (aClass, paramName, paramValue, params, i + 1);
			System.out.println("--:" + params[i]);
			System.out.println("--:" + params[i+1]);

		}
		
		return params;
	}

	public static void setParamValue (Class<?> aClass, String paramName, String paramValue, Object[] params, int pos){
		List<Field> fields = getAllModelFields(aClass);
		for(int i = 0; i < fields.size(); i++){
			Field f = fields.get(i);
			Annotation annotation = (Annotation)f.getAnnotation(ConfigurationParameter.class);
			
			if(annotation instanceof ConfigurationParameter){
				ConfigurationParameter myAnnotation = (ConfigurationParameter) annotation;
				
				if(myAnnotation.name().equals(paramName)){
					System.out.println("FOUND:" + myAnnotation.name());
					
					if(f.getType().isAssignableFrom(Integer.TYPE)){
						params[pos] = Integer.parseInt(paramValue);
					}else if(f.getType().isAssignableFrom(Boolean.TYPE)){
						params[pos] = Boolean.parseBoolean(paramValue);
					}else{
						params[pos] = paramValue;
					}

				}
			}
		}
	}
	
	public static List<Field> getAllModelFields(Class<?> aClass) {
	    List<Field> fields = new ArrayList<>();
	    do {
	        Collections.addAll(fields, aClass.getDeclaredFields());
	        aClass = aClass.getSuperclass();
	    } while (aClass != null);
	    return fields;
	}
}
