package eu.openminted.workflows.galaxywrappers;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import eu.openminted.registry.domain.Component;

public class OMTDSHAREParser {
	
	private Unmarshaller jaxbUnmarshaller;
	private JAXBContext jaxbContext;
	
	public OMTDSHAREParser(){		
		try{
			jaxbContext	 = JAXBContext.newInstance(Component.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		}catch(Exception e){
			//log
			e.printStackTrace();
		}	
	}
	
	public Component parse(File omtdShareFile){
		Component componentMeta = null;
		
		try {
			componentMeta = (Component) jaxbUnmarshaller.unmarshal(omtdShareFile);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return componentMeta;
	}
}
