package eu.openminted.workflows.galaxywrappers;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import eu.openminted.registry.domain.Component;
import eu.openminted.registry.domain.ComponentInfo;
import eu.openminted.registry.domain.Description;
import eu.openminted.workflows.galaxytool.Tool;

/**
 * @author ilsp
 *
 */
public class GalaxyWrapperGenerator {

	private static final Logger log = Logger.getLogger(GalaxyWrapperGenerator.class.getName());

	OMTDSHAREParser omtdshareParser;
	
	private Marshaller jaxbMarshaller;
	private JAXBContext jaxbContext;
	private File outDirHandler;
	
	public GalaxyWrapperGenerator(String outDir){		
		try{
			omtdshareParser = new OMTDSHAREParser();
			jaxbContext	 = JAXBContext.newInstance(Tool.class);
			jaxbMarshaller = jaxbContext.createMarshaller();
			
			outDirHandler = new File(outDir);
			outDirHandler.mkdirs();
		}catch(Exception e){
			//log
			e.printStackTrace();
		}	
	}
	
	public String generate(File omtdShareFile){
		String xmlFile = "";
		try{
			// Parse omtd-share file.			
			Component componentMeta = omtdshareParser.parse(omtdShareFile);

			// Get Info
			ComponentInfo componentInfo = componentMeta.getComponentInfo();			
			List<Description> descriptions = componentInfo.getIdentificationInfo().getDescriptions();
			
			String desc  = "";
			if(descriptions != null && descriptions.size() > 0){				
				desc = descriptions.get(0).getValue();
			}else{
				desc = componentInfo.getIdentificationInfo().getResourceNames().get(0).getValue();
			}
			
			Tool tool = new Tool();
			tool.setDescription(desc);
			
			FileOutputStream out = new FileOutputStream(outDirHandler.getAbsolutePath() + "/" + omtdShareFile.getName() + ".xml");
			jaxbMarshaller.marshal(tool, out);
		}catch(Exception e){
			e.printStackTrace();
			log.info(e.getMessage());
			return "ERROR";
		}
		
		return xmlFile;
	}
}
