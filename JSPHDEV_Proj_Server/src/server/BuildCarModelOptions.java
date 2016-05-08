package server;

import java.util.Properties;

import adapter.BuildAuto;
import model.Automobile;
import util.LinkedProperties;

/**Class having implementations for creating Automobile object from Properties File
 * And adding those objects to LinkedHashMap
 *
 * @author Amrata Kasture
 *
 */

public class BuildCarModelOptions implements AutoServer
{
	BuildAuto ba = new BuildAuto();
	public boolean buildAutoFromProp(Properties clientProp) {
		//ba.buildAutoFromProp(clientProp);
		Properties props= new Properties(); 
    	props = clientProp;
    	//String filename = props.getProperty("CarMake")+props.getProperty("CarModel")+props.getProperty("Style");
    	//ba.buildAuto(filename+".prop","prop");
    	ba.buildAutoFromProp(props);
    	ba.printAuto(props.getProperty("CarMake")+props.getProperty("CarModel")+props.getProperty("Style"));
    	if(ba.searchAuto(props.getProperty("CarMake")+props.getProperty("CarModel")+props.getProperty("Style"))==true){
    		return true;	
    	}
    	return false;
	} 
	
	public void buildAutoFromPropFile(String filename) {
		ba.buildAuto(filename,"prop");
	}
	
	public String[] listAllModels(){
		return ba.listAllModels();
	} 
	
	public String serializeAndSend(String ModelName){
		return ba.serializeAndSend(ModelName);
	}
	
	public Automobile searchAndReturnAuto(String modelName){
		return ba.searchAndReturnAuto(modelName);
	}
}
