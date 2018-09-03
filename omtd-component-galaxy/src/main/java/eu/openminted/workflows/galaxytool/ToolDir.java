package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;


public class ToolDir {

	private String dir;

	public String getDir() {
		return dir;
	}

	@XmlAttribute(name = GalaxyCons.dir)
	public void setDir(String dir) {
		this.dir = dir;
	}
	
	
}
