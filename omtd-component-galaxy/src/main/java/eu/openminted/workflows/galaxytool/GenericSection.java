package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = GalaxyCons.section)
public class GenericSection {

	private String id;
	private String name;
	
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
}
