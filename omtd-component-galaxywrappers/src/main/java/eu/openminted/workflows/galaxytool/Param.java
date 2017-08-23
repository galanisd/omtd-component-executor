package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlAttribute;

public class Param {

	private String name;
	private String type;
	private String label;
	
	private String value;
	
	private String description;
	
	public String getDescription() {
		return description;
	}

	@XmlAttribute(name = GalaxyCons.description)
	public void setDescription(String description) {
		this.description = description;
	}

	private String optional;
	
	
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

	public String getOptional() {
		return optional;
	}

	@XmlAttribute(name = GalaxyCons.optional)
	public void setOptional(String optional) {
		this.optional = optional;
	}

	public String getValue() {
		return value;
	}

	@XmlAttribute(name = GalaxyCons.value)
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
