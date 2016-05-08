package scale;

import java.util.Iterator;
import java.util.Scanner;
import adapter.ProxyAutomobile;
import model.Automobile;

/**A class containing synchronized methods for handling concurrent updates to OptionSets and Options.
 * @author Amrata Kasture
 *
 */

public class EditOptions extends ProxyAutomobile implements Runnable  {
	// n will denote operation being called 1:updateOptionSetName, 2:updateOptionPrice
	private int n = 0;
	private String[] params = new String[4];
	
	/*private String strFun = "";
	private String ModelName = "";
	private String OptionSetName = "";
	private String newName = "";
	private String OptionName = "";
	private String  Option = "";
	private float newprice = 0;

	public EditOptions(String strFun,String Modelname, String OptionSetname, String newName){
		this.strFun = strFun;
		this.ModelName = Modelname;
		this.OptionSetName = OptionSetname;
		this.newName = newName;
	}

	public EditOptions(String strFun,String Modelname, String Optionname, String Option, float newprice){
		this.strFun = strFun;
		this.ModelName = Modelname;
		this.OptionName = Optionname;
		this.Option = Option;
		this.newprice = newprice;
	}
	
	@Override
	public void run() {
		String x= strFun;
		switch(x){
		case "updateOptionSetName": updateOptionSetName(ModelName, OptionSetName, newName);
		break;
		case "updateOptionPrice": updateOptionPrice(ModelName, OptionName, Option, newprice);
		break;
		}
	}*/

	
	public EditOptions(){}
	public EditOptions(int n,String[] params){
		this.n = n;
		this.params = params;
	}
	
	@Override
	public void run() {
		
		switch(n){
		case 1: MTupdateOptionSetName(params);
		break;
		case 2: MTupdateOptionPrice(params);
		break;
		}
	}

	
/* The one Automobile object from LinkedHash Map has been synchronized. Reasons are as stated below
 * 1. No user will access one model at a time, so no need to synchronize whole Linked Hash Map
 * 2. Object Lock over LHM would have impacted concurrency as only one user could have able to perform operation on any of four models at a time
 * 3. As threads are updating OptionSet or Option through EditOptions, so being starting point it makes more sense to do synchronization at EditOptions level 
 * 	  - than on ProxyAuto or Automobile Level   */	
	
	public void MTupdateOptionSetName(String[] params){
		String ModelName = params[0];
		String OptionSetName = params[1];
		String newName= params[2];

		Iterator<Automobile> it = lhm.values().iterator();
		/*if(auto1.getName().equals(Modelname)){
				auto1.updateOptSetName(OptionSetname,newName);
			}*/
		while (it.hasNext())
		{
			Automobile tempAuto = it.next(); 


			if(ModelName.matches(tempAuto.getModel()+"\\s*"+tempAuto.getName())){
				synchronized(tempAuto){
					System.out.println("Thread: " + Thread.currentThread().getName()+ " ENTERED & UPDATING OptionSet-Name" );
					tempAuto.updateOptSetName(OptionSetName,newName);
					randomWait();
					printAuto(ModelName); 
					System.out.println("Thread :" + Thread.currentThread().getName() +" EXITING as it FINISHED updating OptionSet-Name");
					break;
				}
			}
		}
	}

	public void MTupdateOptionPrice(String[] params){
		String Modelname = params[0];
		String Optionname = params[1]; 
		String Option=params[2];
		float newprice = Float.parseFloat(params[3]);
		Iterator<Automobile> it = lhm.values().iterator();
		/*if(auto1.getName().equals(Modelname)){
			auto1.updateOptionSetUpdateOptionPrice(Optionname,Option,newprice);
		}*/
		while (it.hasNext())
		{
			Automobile tempAuto = it.next(); 

			if(Modelname.matches(tempAuto.getModel()+"\\s*"+tempAuto.getName())){
				synchronized(tempAuto){
					System.out.println("Thread :" + Thread.currentThread().getName() +" ENTERED & UPDATING Option-price" );
					tempAuto.updateOptionSetUpdateOptionPrice(Optionname,Option,newprice);
					randomWait();
					printAuto(Modelname); 
					System.out.println("Thread :" + Thread.currentThread().getName() +" EXITING as it UPDATED Option-price");
					break;
				}
			}
		}
	}

	void randomWait() { try {
		Thread.currentThread().sleep((long)(3000*Math.random())); } catch(InterruptedException e) {
			System.out.println("Interrupted!"); }
	}




}
