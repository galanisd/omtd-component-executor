package eu.openminted.workflows.galaxytool;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = GalaxyCons.tool)
@XmlType(propOrder={"description", "requirements", "command", "inputs", "outputs"})
public class Tool {
	
	private String id;
	private String name;
	private String version;
	
	private String description;
	
	private Requirements requirements;

	private String command;	
	
	private Inputs inputs;
	private Outputs outputs;
	
	
	public Requirements getRequirements() {
		return requirements;
	}

	@XmlElement(name = GalaxyCons.requirements)
	public void setRequirements(Requirements requirements) {
		this.requirements = requirements;
	}

	public String getDescription() {
		return description;
	}
	
	@XmlElement(name = GalaxyCons.description)
	public void setDescription(String description) {
		this.description = description;
	}
	
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

	public String getVersion() {
		return version;
	}

	@XmlAttribute(name = GalaxyCons.version)
	public void setVersion(String version) {
		this.version = version;
	}

	public String getCommand() {
		return command;
	}

	@XmlElement(name = GalaxyCons.command)
	public void setCommand(String command) {
		this.command = command;
	}

	public Inputs getInputs() {
		return inputs;
	}

	@XmlElement(name = GalaxyCons.inputs)
	public void setInputs(Inputs inputs) {
		this.inputs = inputs;
	}

	public Outputs getOutputs() {
		return outputs;
	}

	@XmlElement(name = GalaxyCons.outputs)
	public void setOutputs(Outputs outputs) {
		this.outputs = outputs;
	}
	
	
}
