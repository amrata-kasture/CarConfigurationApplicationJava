package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Properties;

import model.Automobile;

/**
 * NOT BEING USED -- REDUNDANT CLASS - WILL BE DELETED SOON
 * @author Amrata Kasture
 *
 */
public class DeserializeObject {
	//The method deserializeAuto is deserializing Properties objects from serialized Properties object sent in ".ser" files
		public Properties deserializeProp(String Filename){
			Properties pro = new Properties();
			try(FileInputStream fi = new FileInputStream(Filename)) {
	            ObjectInputStream os = new ObjectInputStream(fi);
	            pro = (Properties) os.readObject();
	            os.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
			return pro;
		}
	
	
}
