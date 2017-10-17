package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Sanitizer {

	private Valid valid;
	private String sanitize; 
	
	public Valid getValid() {
		return valid;
	}

	@XmlElement(name = GalaxyCons.valid)
	public void setValid(Valid valid) {
		this.valid = valid;
	}

	public String getSanitize() {
		return sanitize;
	}

	@XmlAttribute
	public void setSanitize(String sanitize) {
		this.sanitize = sanitize;
	}

}
