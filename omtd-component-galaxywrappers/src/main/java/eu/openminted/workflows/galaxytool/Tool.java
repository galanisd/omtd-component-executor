package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = GalaxyCons.tool)
public class Tool {
	
	@XmlElement(name = GalaxyCons.description)
	public String description;

	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
