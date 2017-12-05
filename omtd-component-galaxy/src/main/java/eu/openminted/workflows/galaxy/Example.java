package eu.openminted.workflows.galaxy;

public class Example {

	public static void main(String args[]) {
		GalaxyWrapperGeneratorMain main = new GalaxyWrapperGeneratorMain();
		//main.main(exampleArgsDKPRO());
		//main.main(exampleArgsGATE());
		main.main(exampleArgsAlvisNLP());

	}

	public static String[] exampleArgsDKPRO() {
		String root = "/home/ilsp/Desktop/OMTDTemp/";
		//String omtdShareDescFolder = "DKPro_omtds-dkpro-core-1.9.0-SNAPSHOT";
		String omtdShareDescFolder = "DKPro_omtds-dkpro-core-1.9.0-SNAPSHOT-20170925-1";

		String galaxyWrappersFolderInGalaxy = "omtdDKPro";
		
		//String dockerImage = "snf-765691.vm.okeanos.grnet.gr/openminted/omtd-component-executor";
		String dockerImage = "snf-1301.ok-kno.grnetcloud.net/openminted/omtd-component-executor";
		
		String ComponentID = "dkpro-core";
		String ComponentVersion = "1.9.0-SNAPSHOT";
				
		String[] myArgs = { root, omtdShareDescFolder, galaxyWrappersFolderInGalaxy, dockerImage + "-" +  ComponentID + ":" + ComponentVersion};
		return myArgs;
	}

	public static String[] exampleArgsGATE() {
		String root = "/home/ilsp/Desktop/OMTDTemp/";
		//String omtdShareDescFolder = "annie-descriptors";
		String omtdShareDescFolder = "GATE_annie-omtds";
		
		String galaxyWrappersFolderInGalaxy = "omtdGATE";
		String dockerImage = "snf-765691.vm.okeanos.grnet.gr/openminted/omtd-workflows-executor";
		String ComponentID = "gate";
		String ComponentVersion = "1.8.0";
				
		String[] myArgs = { root, omtdShareDescFolder, galaxyWrappersFolderInGalaxy, dockerImage + "-" +  ComponentID + ":" + ComponentVersion};

		return myArgs;
	}
	
	public static String[] exampleArgsAlvisNLP() {
		String root = "/home/ilsp/Desktop/OMTDTemp/";
		String omtdShareDescFolder = "AlvisNLP_eu.openminted.share";
		String galaxyWrappersFolderInGalaxy = "AlvisNLP";
		
		//String dockerImage = "snf-765691.vm.okeanos.grnet.gr/openminted/omtd-component-executor";
		String dockerImage = "snf-1301.ok-kno.grnetcloud.net/openminted/omtd-component-executor";
		
		String ComponentID = "AlvisNLP";
		String ComponentVersion = "1.0.0";
				
		String[] myArgs = { root, omtdShareDescFolder, galaxyWrappersFolderInGalaxy, dockerImage + "-" +  ComponentID + ":" + ComponentVersion};
		return myArgs;
	}

}