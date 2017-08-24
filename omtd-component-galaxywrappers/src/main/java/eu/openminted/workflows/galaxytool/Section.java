package eu.openminted.workflows.galaxytool;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = GalaxyCons.section)
public class Section {
	
	String id;
	String name;

	ArrayList<ToolEntry> tools;
	
	public String getId() {
		return id;
	}

	@XmlAttribute(name = GalaxyCons.id)
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@XmlAttribute(name = GalaxyCons.name)
	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ToolEntry> getTools() {
		return tools;
	}

	@XmlElement(name = GalaxyCons.tool)
	public void setTools(ArrayList<ToolEntry> tools) {
		this.tools = tools;
	}	
}
