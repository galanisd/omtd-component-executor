package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = GalaxyCons.section)
public class SectionWithDir extends GenericSection{
	
	private ToolDir toolWithDir;

	public ToolDir getToolWithDir() {
		return toolWithDir;
	}

	@XmlElement(name = GalaxyCons.toolDir)
	public void setToolWithDir(ToolDir toolWithDir) {
		this.toolWithDir = toolWithDir;
	}
	
	
}
