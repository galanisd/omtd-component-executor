package eu.openminted.workflows.galaxytool;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class Outputs {
	
	/*
	private ArrayList<Param> params;

	public ArrayList<Param> getParams() {
		return params;
	}

	@XmlElement(name = GalaxyCons.param)
	public void setParams(ArrayList<Param> params) {
		this.params = params;
	}
	*/
	
	Collection collection;

	public Collection getCollection() {
		return collection;
	}

	@XmlElement(name = GalaxyCons.collection)
	public void setCollection(Collection collection) {
		this.collection = collection;
	}
	
	
}
