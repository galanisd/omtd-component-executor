package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlAttribute;

public class Param {

	private String name;
	private String type;
	private String label;
	
	
	public String getName() {
		return name;
	}
	
	@XmlAttribute(name = GalaxyCons.name)
	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}
	
	@XmlAttribute(name = GalaxyCons.type)
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlAttribute(name = GalaxyCons.label)
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
