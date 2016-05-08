package adapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;

import dbOperations.DBOps;
import dbOperations.DbCRUD;

/**Abstract Class hiding actual implementations from BuildAuto API Class
 * @author Amrata Kasture
 *
 */

import model.Automobile;
import scale.EditOptions;
import util.FileIO;


/**
 * An Abstract class including definitions of all functions from createAuto and updateAuto interface
 * @author amrata
 *
 */

public abstract class ProxyAutomobile {
	static int  count = 0 ;
	//private static Automobile auto1;
	protected static LinkedHashMap<String,Automobile> lhm = new LinkedHashMap<String,Automobile>();
	
	//Creating method to return Auto object to allow storing them in Linked HashMap in Drive 
	public void buildAuto(String filename,String fileType){
		Automobile automob = new Automobile();
		FileIO fio = new FileIO();
		DBOps dbo = new DbCRUD();
		if(fileType.equalsIgnoreCase("txt")){
		automob = fio.readFileNBuildObj(filename);
		//buildAuto(filename);
		//automob = auto1;
		//return automob;
		lhm.put(automob.getMake()+automob.getModel()+automob.getName(),automob);
		 if(dbo.AddCarModel(automob)==1)
			  System.out.println("Automobile "+automob.getMake()+automob.getModel()+automob.getName()+" inserted successfully");
		/* if(md.AddRowOptionSets("CarModels", automob)==1)
			  System.out.println("Option Sets inserted successfully");
		 if(md.AddRowOptions("CarModels", automob)==1)
			  System.out.println("Options inserted successfully");
		 if(md.AddRowConfigurations("CarModels", automob)==1)
			  System.out.println("Configurations inserted successfully for"+automob.getMake()+">>"+automob.getModel()+">>"+automob.getName());
		*/
		}else if(fileType.equalsIgnoreCase("prop")){
			automob = fio.readPropFileNBuildObj(filename);
			//buildAuto(filename);
			//automob = auto1;
			//return automob;
			lhm.put(automob.getMake()+automob.getModel()+automob.getName(),automob);
			 if(dbo.AddCarModel(automob)==1)
				  System.out.println("Automobile "+automob.getMake()+automob.getModel()+automob.getName()+" inserted successfully");
			/* if(md.AddRowOptionSets("CarModels", automob)==1)
				  System.out.println("Option Sets inserted successfully");
			 if(md.AddRowOptions("CarModels", automob)==1)
				  System.out.println("Options inserted successfully");
			 if(md.AddRowConfigurations("CarModels", automob)==1)
				  System.out.println("Configurations inserted successfully for"+automob.getMake()+">>"+automob.getModel()+">>"+automob.getName());
		*/
		}
		
		
	}
	
	public boolean buildAutoFromProp(Properties PRO){
		Automobile automob = new Automobile();
		FileIO fio = new FileIO();
		automob = fio.readPropFileNBuildObj(PRO);
		lhm.put(automob.getMake()+automob.getModel()+automob.getName(),automob);
		if(searchAuto(PRO.getProperty("CarMake")+PRO.getProperty("CarModel")+PRO.getProperty("Style"))== true){
    		return true;
    	}
    	return false;
	}
	
	
	public void deleteAuto(String autoName){
		DBOps dbo = new DbCRUD();
		lhm.remove(autoName);
		 if(dbo.deleteModel(autoName)==1)
			  System.out.println("Automobile "+autoName+" deleted successfully");
	}
	
	//AutoServer Interface's buildAutoFromProp() Implementation
		/*public boolean buildAutoFromProp(Properties clientProp){
	    	LinkedProperties props= new LinkedProperties(); 
	    	props = (LinkedProperties) clientProp;
	    	String filename = props.getProperty("CarMake")+props.getProperty("CarModel")+props.getProperty("Style");
	    	buildAuto(filename,"prop");
	    	//"ToyotaPriusBasic.prop"
	    	if(searchAuto(filename)== true){
	    		return true;
	    	}
	    	return false;
		}*/
		
		public void buildAutoFromPropFile(String filename) {
			buildAuto(filename,"prop");
		}
		
		
	//List All Models from LinkedHashMap
		public String[] listAllModels(){
			String[] carModels = new String[lhm.size()];
			Iterator<Automobile> it = lhm.values().iterator();
			int i = 0;
			while (it.hasNext())
			{
				Automobile tempAuto = it.next(); 
				//System.out.println(tempAuto.getMake()+">>"+tempAuto.getModel()+">>"+tempAuto.getName());
				carModels[i] = tempAuto.getMake()+">>"+tempAuto.getModel()+">>"+tempAuto.getName();
				i++;
			}
			return carModels;
		}
	
	//Serialize and Send Client requested Automobile Object	
		public String serializeAndSend(String ModelName){
			Iterator<Automobile> it = lhm.values().iterator();
			while (it.hasNext())
			{
				Automobile tempAuto = it.next(); 
				if(ModelName.matches(tempAuto.getModel()+"\\s*"+tempAuto.getName())){
					try(FileOutputStream fs = new FileOutputStream(ModelName+".ser")) {
			            ObjectOutputStream os = new ObjectOutputStream(fs);
			            os.writeObject(tempAuto);
			            os.close();
			        } catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
					
					break;
				}
			}
			return ModelName+".ser";
		}
		
	//CreateAuto Interface's buildAuto method implementation
	/*public void buildAuto(String filename){
		FileIO fio = new FileIO();
		auto1 = fio.readFileNBuildObj(filename);
		
	}*/
		
		//searchAuto method implementation
		public boolean searchAuto(String modelName){
			
			/*if(auto1.getName().equals(modelName)){
				auto1.printAutoComplete();
			}*/
			//--------------------WIP----------------------
			Iterator<Automobile> it = lhm.values().iterator();
			while (it.hasNext())
			{
				Automobile tempAuto = it.next(); 
				if(modelName.matches(tempAuto.getMake()+"\\s*"+tempAuto.getModel()+"\\s*"+tempAuto.getName())){
					return true;
				}
			}
			return false;
		}	
		
		
		//Search and Return Automobile
		public Automobile searchAndReturnAuto(String modelName){
			
			Iterator<Automobile> it = lhm.values().iterator();
			while (it.hasNext())
			{
				Automobile tempAuto = it.next(); 
				if(modelName.matches(tempAuto.getMake()+"\\s*"+tempAuto.getModel()+"\\s*"+tempAuto.getName())){
					return tempAuto;
				}
			}
			return null;
		}
	
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
		DBOps dbo = new DbCRUD();
		/*if(auto1.getName().equals(Modelname)){
			auto1.updateOptSetName(OptionSetname,newName);
		}*/
		while (it.hasNext())
		{
			Automobile tempAuto = it.next(); 

			if(Modelname.matches(tempAuto.getModel()+"\\s*"+tempAuto.getName()) || Modelname.matches(tempAuto.getMake()+"\\s*"+tempAuto.getModel()+"\\s*"+tempAuto.getName())){
				tempAuto.updateOptSetName(OptionSetname,newName);
				if(dbo.updateModelOSName(tempAuto,OptionSetname,newName)==1)
					  System.out.println("Automobile "+Modelname+" Option-Set Name Updated successfully");
				break;
			}
		}
		
		
	}
	
	//UpdateAuto Interface's updateOptionPrice method implementation
	public void updateOptionPrice(String Modelname, String Optionname, String Option, float newprice){
		Iterator<Automobile> it = lhm.values().iterator();
		DBOps dbo = new DbCRUD();
		/*if(auto1.getName().equals(Modelname)){
			auto1.updateOptionSetUpdateOptionPrice(Optionname,Option,newprice);
		}*/
		while (it.hasNext())
		{
			Automobile tempAuto = it.next(); 

			if(Modelname.matches(tempAuto.getModel()+"\\s*"+tempAuto.getName()) || Modelname.matches(tempAuto.getMake()+"\\s*"+tempAuto.getModel()+"\\s*"+tempAuto.getName())){
				tempAuto.updateOptionSetUpdateOptionPrice(Optionname,Option,newprice);
				if(dbo.updateDBOptionPrice(tempAuto,Optionname,Option,newprice)==1)
					  System.out.println("Automobile "+Modelname+" Option Price Updated successfully");
				
				break;
			}
		}
	}
	
	public void updateBasePrice(String Modelname, float baseprice){
		Iterator<Automobile> it = lhm.values().iterator();
		DBOps dbo = new DbCRUD();
		/*if(auto1.getName().equals(Modelname)){
			auto1.updateOptionSetUpdateOptionPrice(Optionname,Option,newprice);
		}*/
		while (it.hasNext())
		{
			Automobile tempAuto = it.next(); 
			if(Modelname.matches(tempAuto.getModel()+"\\s*"+tempAuto.getName()) || Modelname.matches(tempAuto.getMake()+"\\s*"+tempAuto.getModel()+"\\s*"+tempAuto.getName())){
				tempAuto.setBasePrice(baseprice);
				if(dbo.updateModelBasePrice(tempAuto)==1)
					  System.out.println("Automobile "+Modelname+" BasePrice Updated successfully");
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
