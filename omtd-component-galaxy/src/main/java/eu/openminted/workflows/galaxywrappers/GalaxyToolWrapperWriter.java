package eu.openminted.workflows.galaxywrappers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import eu.openminted.workflows.galaxytool.Tool;

public class GalaxyToolWrapperWriter {

	private Marshaller jaxbMarshaller;
	private JAXBContext jaxbContext;
	
	public GalaxyToolWrapperWriter(){
		try{			
			//System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
			jaxbContext	 = JAXBContext.newInstance(Tool.class);
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);		
		}catch(Exception e){
			//log
			e.printStackTrace();
		}
	}
	
	public void write(Tool tool, String galaxyWrapperPath){
		
		try {
			FileOutputStream out = new FileOutputStream(galaxyWrapperPath); 
			jaxbMarshaller.marshal(tool, out);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
