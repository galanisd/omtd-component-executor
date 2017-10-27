package eu.openminted.workflows.galaxywrappers;

public class Utils {

	public static String normalizeCoordinates(String coordinates){
		return coordinates.replaceAll(":", "_");
	}
	
	public static String getShortNameFromComponentID(String componentID){
		// This works for java components
		String shortName = componentID.substring(componentID.lastIndexOf(".") + 1);
		return shortName;
	}
	
	public static String getClassNameFromComponentID(String componentID){
		String shortName = componentID.substring(componentID.lastIndexOf("#") + 1);
		return shortName;
	}
	
}
