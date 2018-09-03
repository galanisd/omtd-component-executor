package eu.openminted.workflows.galaxytool;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = GalaxyCons.section)
public class SectionWithListOfTools extends GenericSection{

	private ArrayList<ToolEntry> tools;

	public ArrayList<ToolEntry> getTools() {
		return tools;
	}

	@XmlElement(name = GalaxyCons.tool)
	public void setTools(ArrayList<ToolEntry> tools) {
		this.tools = tools;
	}	
}
