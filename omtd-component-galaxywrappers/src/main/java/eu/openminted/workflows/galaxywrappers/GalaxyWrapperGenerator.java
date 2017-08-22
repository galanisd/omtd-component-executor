package eu.openminted.workflows.galaxywrappers;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import eu.openminted.registry.domain.Component;

/**
 * @author ilsp
 *
 */
public class GalaxyWrapperGenerator {

	private Unmarshaller jaxbUnmarshaller;
	private JAXBContext jaxbContext;
	
	public GalaxyWrapperGenerator(){
		
		try{
			jaxbContext	 = JAXBContext.newInstance(Component.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public String generate(File omtdShareFile){
		String xmlFile = "";
		try{
			Component componentMeta = (Component) jaxbUnmarshaller.unmarshal(omtdShareFile);
			xmlFile = componentMeta.getComponentInfo().getIdentificationInfo().getDescriptions().get(0).getValue();
		}catch(Exception e){
			return "ERROR";
		}
		
		return xmlFile;
	}
}
