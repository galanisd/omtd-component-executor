package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author galanisd
 *
 */
public class Collection {
	
	private String name;
	private String type;
	private String label;
	
	private DiscoverDatasets discoverDatasets;
	
	public String getName() {
		return name;
	}
	
	@XmlAttribute(name = GalaxyCons.name)
	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}
	
	@XmlAttribute(name = GalaxyCons.type)
	public void setType(String type) {
		this.type = type;
	}
	
	@XmlAttribute(name = GalaxyCons.label)
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public DiscoverDatasets getDiscoverDatasets() {
		return discoverDatasets;
	}

	@XmlElement(name = GalaxyCons.discover_datasets)
	public void setDiscoverDatasets(DiscoverDatasets discoverDatasets) {
		this.discoverDatasets = discoverDatasets;
	}
	
}
