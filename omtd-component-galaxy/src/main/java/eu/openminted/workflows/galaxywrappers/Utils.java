package eu.openminted.workflows.galaxywrappers;

import java.util.List;

import eu.openminted.registry.domain.ComponentDistributionInfo;

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

	public static String getCommand(List<ComponentDistributionInfo> componentDistributionInfos){
		return componentDistributionInfos.get(0).getCommand();
	}
}
