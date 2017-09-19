package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlAttribute;

public class ToolEntry {

	private String file;

	public String getFile() {
		return file;
	}

	@XmlAttribute(name = GalaxyCons.file)
	public void setFile(String file) {
		this.file = file;
	}
	
}
