package adapter;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**Abstract Class hiding actual implementations from BuildAuto API Class
 * @author Amrata Kasture
 *
 */

import model.Automobile;
import scale.EditOptions;
import util.FileIO;

public abstract class ProxyAutomobile {
	static int  count = 0 ;
	//private static Automobile auto1;
	protected static LinkedHashMap<String,Automobile> lhm = new LinkedHashMap<String,Automobile>();
	
	//Creating method to return Auto object to allow storing them in Linked HashMap in Drive 
	public void buildAuto(String filename,String fileType){
		Automobile automob = new Automobile();
		FileIO fio = new FileIO();
		if(fileType.equalsIgnoreCase("txt")){
		automob = fio.readFileNBuildObj(filename);
		//buildAuto(filename);
		//automob = auto1;
		//return automob;
		lhm.put(automob.getMake()+automob.getModel()+automob.getName(),automob);
		}else if(fileType.equalsIgnoreCase("prop")){
			automob = fio.readPropFileNBuildObj(filename);
			//buildAuto(filename);
			//automob = auto1;
			//return automob;
			lhm.put(automob.getMake()+automob.getModel()+automob.getName(),automob);
		}
		
		
	}
	
	//CreateAuto Interface's buildAuto method implementation
	/*public void buildAuto(String filename){
		FileIO fio = new FileIO();
		auto1 = fio.readFileNBuildObj(filename);
		
	}*/
	
	//CreateAuto Interface's printAuto method implementation
	public void printAuto(String modelName){
		
		/*if(auto1.getName().equals(modelName)){
			auto1.printAutoComplete();
		}*/
		//--------------------WIP----------------------
		Iterator<Automobile> it = lhm.values().iterator();
		while (it.hasNext())
		{
			Automobile tempAuto = it.next(); 
			if(modelName.matches(tempAuto.getModel()+"\\s*"+tempAuto.getName())){
				tempAuto.printAutoComplete();
				break;
			}
		}
		
	}
	
	//UpdateAuto Interface's updateOptionSetName method implementation
	public void updateOptionSetName(String Modelname, String OptionSetname, String newName){
		Iterator<Automobile> it = lhm.values().iterator();
		/*if(auto1.getName().equals(Modelname)){
			auto1.updateOptSetName(OptionSetname,newName);
		}*/
		while (it.hasNext())
		{
			Automobile tempAuto = it.next(); 

			if(Modelname.matches(tempAuto.getModel()+"\\s*"+tempAuto.getName())){
				tempAuto.updateOptSetName(OptionSetname,newName);
				break;
			}
		}
	}
	
	//UpdateAuto Interface's updateOptionPrice method implementation
	public void updateOptionPrice(String Modelname, String Optionname, String Option, float newprice){
		Iterator<Automobile> it = lhm.values().iterator();
		/*if(auto1.getName().equals(Modelname)){
			auto1.updateOptionSetUpdateOptionPrice(Optionname,Option,newprice);
		}*/
		while (it.hasNext())
		{
			Automobile tempAuto = it.next(); 

			if(Modelname.matches(tempAuto.getModel()+"\\s*"+tempAuto.getName())){
				tempAuto.updateOptionSetUpdateOptionPrice(Optionname,Option,newprice);
				break;
			}
		}
	}
	
	public void ThreadStart(int n,String[] params){
		count++;
		
		EditOptions eo1 = new EditOptions(n,params);
		Thread t1 = new Thread(eo1);
		t1.setName("Thread "+count +" Running Operation Number "+n);
		t1.start();
		
		
	}

	
	
	
	
}
