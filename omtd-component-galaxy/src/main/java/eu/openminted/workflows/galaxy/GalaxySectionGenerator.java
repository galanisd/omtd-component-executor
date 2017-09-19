package eu.openminted.workflows.galaxy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import eu.openminted.workflows.galaxytool.Section;
import eu.openminted.workflows.galaxytool.ToolEntry;

public class GalaxySectionGenerator {
	
	private String id;
	private String name;

	private Marshaller jaxbMarshaller;
	private JAXBContext jaxbContext;
	
	private Section section;
	
	public GalaxySectionGenerator(String id, String name){
		this.id = id;
		this.name = name;
		try{			
			jaxbContext	 = JAXBContext.newInstance(Section.class);
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);		
		}catch(Exception e){
			//log
			e.printStackTrace();
		}
		
		section = new Section();
		section.setId(id);
		section.setName(name);
		
		ArrayList<ToolEntry> tools = new ArrayList<ToolEntry>();
		section.setTools(tools);
	}
	
	public void addTool(String file){
		ToolEntry entry = new ToolEntry();
		entry.setFile(file);
		
		section.getTools().add(entry);
	}	
	
	public void write(String outFile){
		
		try {
			FileOutputStream out = new FileOutputStream(outFile); 
			jaxbMarshaller.marshal(section, out);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
