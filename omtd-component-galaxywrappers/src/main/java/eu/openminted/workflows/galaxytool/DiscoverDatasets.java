package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * @author galanisd
 *
 */
public class DiscoverDatasets {
	
	private String pattern;
	private String directory;
	private String format;
	private String visible;	
	
	public String getPattern() {
		return pattern;
	}
	
	@XmlAttribute(name = GalaxyCons.pattern)
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}	
	
	public String getDirectory() {
		return directory;
	}
	
	@XmlAttribute(name = GalaxyCons.directory)
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getFormat() {
		return format;
	}
	
	@XmlAttribute(name = GalaxyCons.format)
	public void setFormat(String format) {
		this.format = format;
	}
	public String getVisible() {
		return visible;
	}
	
	@XmlAttribute(name = GalaxyCons.visible)
	public void setVisible(String visible) {
		this.visible = visible;
	}
	
	
}
