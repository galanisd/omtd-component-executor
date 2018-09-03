package eu.openminted.workflows.galaxytool;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = GalaxyCons.toolbox)
public class Toolbox {

	ArrayList<SectionWithDir> sectionWithDir;
	ArrayList<SectionWithListOfTools> sectionWithListOfTools;
	
	public Toolbox(){
		sectionWithDir = new ArrayList<SectionWithDir>();
		sectionWithListOfTools = new ArrayList<SectionWithListOfTools>();
		
	}

	public ArrayList<SectionWithDir> getSectionWithDir() {
		return sectionWithDir;
	}

	@XmlElement(name = GalaxyCons.section)
	public void setSectionWithDir(ArrayList<SectionWithDir> sectionWithDir) {
		this.sectionWithDir = sectionWithDir;
	}

	public ArrayList<SectionWithListOfTools> getSectionWithListOfTools() {
		return sectionWithListOfTools;
	}

	@XmlElement(name = GalaxyCons.section)
	public void setSectionWithListOfTools(ArrayList<SectionWithListOfTools> sectionWithListOfTools) {
		this.sectionWithListOfTools = sectionWithListOfTools;
	}
	

}
