package eu.openminted.workflows.galaxywrappers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

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
	
	public String serialize(Tool tool){
		try {
		    StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(tool, sw);
			return sw.toString();
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public void write(Tool tool, String galaxyWrapperPath) throws Exception{
		
		try {
			FileOutputStream out = new FileOutputStream(galaxyWrapperPath); 
			out.flush();
			out.write(serialize(tool).getBytes());
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
