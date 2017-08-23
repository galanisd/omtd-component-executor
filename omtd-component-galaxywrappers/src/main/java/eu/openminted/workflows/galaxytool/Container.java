package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;


public class Container {
	
	private String value;
	private String type;
		
	public String getValue() {
		return value;
	}
	
	@XmlValue
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	
	@XmlAttribute(name = GalaxyCons.type)
	public void setType(String type) {
		this.type = type;
	}
}
