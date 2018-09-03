package eu.openminted.workflows.galaxy;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import eu.openminted.workflows.galaxytool.GenericSection;
import eu.openminted.workflows.galaxytool.SectionWithListOfTools;
import eu.openminted.workflows.galaxytool.SectionWithDir;
import eu.openminted.workflows.galaxytool.ToolDir;
import eu.openminted.workflows.galaxytool.ToolEntry;
import eu.openminted.workflows.galaxytool.Toolbox;

public class GalaxySectionGenerator {
	
	private Marshaller jaxbMarshaller;
	private JAXBContext jaxbContext;
	
	public final static int ToolList_Type = 1;
	public final static int Dir_Type = 2;
	
	private Toolbox toolbox ;
	private SectionWithDir sectionWithDir;
	private SectionWithListOfTools sectionWithListOfTools;
	
	public GalaxySectionGenerator(){
		try{			
			jaxbContext	 = JAXBContext.newInstance(Toolbox.class);
			jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);	
			
			toolbox = new Toolbox(); 
		}catch(Exception e){
			//log
			e.printStackTrace();
		}
	}
	
	public void addSection(String sectionID, String sectionName, int sectionType){
		
		if(sectionType == ToolList_Type){
			System.out.println(sectionType);
			sectionWithListOfTools = new SectionWithListOfTools();
			sectionWithListOfTools.setId(sectionID);
			sectionWithListOfTools.setName(sectionName);
			
			ArrayList<ToolEntry> tools = new ArrayList<ToolEntry>();
			sectionWithListOfTools.setTools(tools);
			toolbox.getSectionWithListOfTools().add(sectionWithListOfTools);
		}else if(sectionType == Dir_Type){
			System.out.println(sectionType);
			sectionWithDir = new SectionWithDir();	
			sectionWithDir.setId(sectionID);
			sectionWithDir.setName(sectionName);
			
			ToolDir dir = new ToolDir();
			sectionWithDir.setToolWithDir(dir);
			toolbox.getSectionWithDir().add(sectionWithDir);
		}
	
	}
	
	public void addTool(String file){
		ToolEntry entry = new ToolEntry();
		entry.setFile(file);
		sectionWithListOfTools.getTools().add(entry);
	}	
	
	public void addDir(String dirName){
		sectionWithDir.getToolWithDir().setDir(dirName);
	}
	
	public void write(String outFile){
		try {
			FileOutputStream out = new FileOutputStream(outFile); 
			jaxbMarshaller.marshal(toolbox, out);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
