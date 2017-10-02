package eu.openminted.workflows.galaxytool;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

public class Valid {

	private ArrayList<Add> addList;

	public ArrayList<Add> getAddList() {
		return addList;
	}

	@XmlElement(name = GalaxyCons.add)
	public void setAddList(ArrayList<Add> addList) {
		this.addList = addList;
	}
	
	
}
