package eu.openminted.workflows.galaxywrappers;

import java.util.ArrayList;
import java.util.List;

import eu.openminted.registry.domain.ComponentDependencies;
import eu.openminted.registry.domain.ComponentDistributionFormEnum;
import eu.openminted.registry.domain.ComponentDistributionInfo;
import eu.openminted.registry.domain.ResourceIdentifier;

public class Utils {

	public static String getMVNCoordinatesFromResourceIdentifier(String compID){
		String coordinates = "MAVEN_COORDINATES_NA ";
		if(compID.startsWith("mvn:")){
			
			int end = compID.indexOf("#");
			
			if(end != -1){
				coordinates = compID.substring("mvn:".length(), end);
			}else{
				coordinates = compID.substring("mvn:".length());
			}
		}
		
		return coordinates;
	}
	
	public static String getTypesystemsStr(eu.openminted.registry.domain.Component componentMeta){
		String concTypesystems = "";
		
		ArrayList<String> list = getTypesystems(componentMeta);
		for(int i = 0; i < list.size(); i++){
			
			String val = list.get(i);
			String coord = Utils.getMVNCoordinatesFromResourceIdentifier(val);
			String normCoord = Utils.normalizeCoordinates(coord);
			if(i == 0){
				concTypesystems = normCoord;
			}else{
				concTypesystems = concTypesystems + ";" + normCoord;	
			}
		}
	
		return concTypesystems;
	}
	
	public static ArrayList<String> getTypesystems(eu.openminted.registry.domain.Component componentMeta){
		ArrayList<String> typesystems = new ArrayList<String>();
		ComponentDependencies componentDependencies = componentMeta.getComponentInfo().getComponentDependencies();	
		if(componentDependencies != null){
			List<ResourceIdentifier> list = componentDependencies.getTypesystem().getResourceIdentifiers();
			for(int i = 0; i< list.size(); i++){
				typesystems.add(list.get(i).getValue());
			}
		}
		
		return typesystems;
	}
	
	public static boolean isDocker(List<ComponentDistributionInfo> componentDistributionInfos) {
		ComponentDistributionInfo distributionInfo = componentDistributionInfos.get(0);
		return distributionInfo.getComponentDistributionForm() == ComponentDistributionFormEnum.DOCKER_IMAGE;
	}

	public static boolean isWebService(List<ComponentDistributionInfo> componentDistributionInfos) {
		ComponentDistributionInfo distributionInfo = componentDistributionInfos.get(0);
		return distributionInfo.getComponentDistributionForm() == ComponentDistributionFormEnum.WEB_SERVICE;
	}

	public static String getURL(List<ComponentDistributionInfo> componentDistributionInfos){
		return componentDistributionInfos.get(0).getDistributionLocation();
	}
	
	public static String normalizeCoordinates(String coordinates) {
		return coordinates.replaceAll(":", "_");
	}

	public static String getShortNameFromComponentID(String componentID) {
		// This works for java components
		String shortName = componentID.substring(componentID.lastIndexOf(".") + 1);
		return shortName;
	}

	public static String getClassNameFromComponentID(String componentID) {
		String shortName = componentID.substring(componentID.lastIndexOf("#") + 1);
		return shortName;
	}

	public static String getCommand(List<ComponentDistributionInfo> componentDistributionInfos) {
		return componentDistributionInfos.get(0).getCommand();
	}

	public static String getImage(List<ComponentDistributionInfo> componentDistributionInfos) {

		// for(int i = 0; i < componentDistributionInfos.size(); i++){
		// ComponentLoc loc =
		// componentDistributionInfos.get(0).getComponentLoc();
		// }

		// return
		// componentDistributionInfos.get(0).getComponentLoc().getDistributionURL();
		return componentDistributionInfos.get(0).getDistributionLocation();
	}
}
