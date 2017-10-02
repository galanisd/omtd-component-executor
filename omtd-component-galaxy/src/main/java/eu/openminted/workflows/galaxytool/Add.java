package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlAttribute;

public class Add {

	String value;

	public String getValue() {
		return value;
	}

	@XmlAttribute(name = GalaxyCons.value)
	public void setValue(String value) {
		this.value = value;
	}
	
}
