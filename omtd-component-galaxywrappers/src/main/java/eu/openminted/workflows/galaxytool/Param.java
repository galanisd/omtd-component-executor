package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlAttribute;

public class Param {

	private String name;
	private String type;
	private String label;
	
	private String value;	
	private String description;	
	private String optional;
	
	private String format; 
	private String multiple;
	
	private String collection_type;
	
	
	public String getCollection_type() {
		return collection_type;
	}

	@XmlAttribute(name = GalaxyCons.description)
	public void setCollection_type(String collection_type) {
		this.collection_type = collection_type;
	}

	public String getDescription() {
		return description;
	}

	@XmlAttribute(name = GalaxyCons.description)
	public void setDescription(String description) {
		this.description = description;
	}

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

	public String getFormat() {
		return format;
	}

	@XmlAttribute(name = GalaxyCons.format)
	public void setFormat(String format) {
		this.format = format;
	}

	public String getMultiple() {
		return multiple;
	}

	@XmlAttribute(name = GalaxyCons.multiple)
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	
	
}
