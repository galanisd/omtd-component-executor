package eu.openminted.workflows.galaxywrappers;

import java.util.List;

import eu.openminted.registry.domain.ComponentDistributionFormEnum;
import eu.openminted.registry.domain.ComponentDistributionInfo;

public class Utils {

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
