package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlElement;

public class Requirements {

	private Container container;

	public Container getContainer() {
		return container;
	}

	@XmlElement(name = GalaxyCons.container)
	public void setContainer(Container container) {
		this.container = container;
	}
	
}
