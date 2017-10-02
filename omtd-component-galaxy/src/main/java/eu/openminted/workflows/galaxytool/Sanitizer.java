package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlElement;

public class Sanitizer {

	private Valid valid;

	public Valid getValid() {
		return valid;
	}

	@XmlElement(name = GalaxyCons.valid)
	public void setValid(Valid valid) {
		this.valid = valid;
	}

}
