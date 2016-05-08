package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import model.Automobile;
import util.LinkedProperties;

/**
 * Class responsible for functionality of building prop object from properties file
 * @author Amrata Kasture
 *
 */

public class CarModelOptionsIO {
	
	public LinkedProperties buildPropObjFromPropFile(String filename){
		LinkedProperties props= new LinkedProperties(); //
	      FileInputStream in = null;
		try {
			in = new FileInputStream(filename);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	      try {
			props.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //This loads the entire file in memory.
	    
	     return props; 
	}
	
	//Serialize and Send Properties Object to Server	
			public String serializeAndSendProObj(LinkedProperties p){
				try(FileOutputStream fs = new FileOutputStream(p.getProperty("CarMake")+p.getProperty("CarModel")+p.getProperty("Style")+".serproobj")) {
		            ObjectOutputStream os = new ObjectOutputStream(fs);
		            os.writeObject(p);
		            os.close();
		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
				return p.getProperty("CarMake")+p.getProperty("CarModel")+p.getProperty("Style")+".serproobj";
			}
	
	       //Print Automobile Object
			public void extractAuto(Automobile auto){
				System.out.println(auto.toString());
			}
			
			
	

}
