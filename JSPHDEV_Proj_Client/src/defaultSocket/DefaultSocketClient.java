package defaultSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;

import org.apache.catalina.tribes.util.Arrays;

import client.CarModelOptionsIO;
import client.SelectCarOption;
import model.Automobile;
import util.LinkedProperties;

/**
 * DefaultSocketClient class handle multiple client sessions and respective inputs and outputs for every Client session
 *  to Server and from  Server respectively
 * @author Amrata Kasture
 *
 */
public class DefaultSocketClient extends Thread implements SocketClientInterface, SocketClientConstants {
	public static String[] list = new String[5];  
 //   private BufferedReader reader;
  //  private BufferedWriter writer;
    private Socket sock;
    private String strHost;
    private int iPort;
    private Automobile myAuto;
  //  protected static LinkedHashMap<String,Automobile> lhm = new LinkedHashMap<String,Automobile>();

    public DefaultSocketClient(String strHost, int iPort) {       
            setPort (iPort);
            setHost (strHost);
            
    }//constructor
    
    public void run(){
       if (openConnection()){
    	   this.list=listModels();
    	  
         // handleSession();
         //closeSession();
       }
    }//run
    
    public boolean openConnection(){

    	   try {
    		   if(this.sock == null)
    	     sock = new Socket(strHost, iPort);                    
    	   }
    	   catch(IOException socketError){
    	     if (DEBUG) System.err.println
    	        ("Unable to connect to " + strHost);
    	     return false;
    	   }
    	   /*try {
    	     reader = new BufferedReader
    	      (new InputStreamReader(sock.getInputStream()));
    	     writer = new BufferedWriter
    	      (new OutputStreamWriter (sock.getOutputStream()));
    	   }
    	  catch (Exception e){
    	     if (DEBUG) System.err.println
    	       ("Unable to obtain stream to/from " + strHost);
    	     return false;
    	  }*/
    	  return true;
    	}
    
   
    
    
    public void handleSession(){
    	  String strInput = "";
    	  if (DEBUG) System.out.println ("Handling session with "
    	            + strHost + ":" + iPort);
    	  try {
    		  ObjectOutputStream outObj = null;
    		  ObjectInputStream inObj = null;
    		  Object obj=null;
    		  outObj = new ObjectOutputStream(sock.getOutputStream());
              inObj = new ObjectInputStream(sock.getInputStream());
              BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
              String fromServer;
              String fromUser;
              CarModelOptionsIO cmo = new CarModelOptionsIO();
          
              System.out.println("Welcome to AK's Cars. Please enter option from Menu:\n1. Upload Properties File \n2. Configure a Car");
              while ((fromUser = stdIn.readLine()) != null) {
               
              if (fromUser != null) {
                  //System.out.println("Client: " + fromUser);
                  //out.println(fromUser);
      	    	if(fromUser.equals("1")){
      	    		System.out.println("Please provide path for Properties File to be Uploaded");
      				 while ((fromUser = stdIn.readLine()) != null) {
      					 
      					 //out.println(cmo.serializeAndSendProObj(cmo.buildPropObjFromPropFile(fromUser)));
      					
      					// out.println(cmo.buildPropObjFromPropFile(fromUser));
      					 LinkedProperties lp = new LinkedProperties();
      					 lp=cmo.buildPropObjFromPropFile(fromUser);
      					 System.out.println(lp.toString());
      					 outObj.writeObject(lp);
      					 outObj.flush();
      					 while ((fromServer = (String) inObj.readObject()) != null) {
      				        	System.out.println("Server Responded: " + fromServer);
      				        	break;
      				        	
      				           // fromUser = stdIn.readLine();    
      				        }
      					break;
      				 }
      				
      			}else if(fromUser.equals("2")){
      				System.out.println("The available Cars are:");
      				outObj.writeObject("RequestingAllModels");
      				outObj.flush();
      				 try {
      					while ((obj = inObj.readObject()) != null) {
      						//System.out.println("CLIENT CHECK 1:"+ obj);
      					    	if(obj instanceof String[]){
      					    		 String[] temp = (String[]) obj;
      					    		//System.out.println("CLIENT CHECK 2:"+ Arrays.toString(temp));
      					    		for(int i =0;i<temp.length;i++){
      					    			System.out.println(temp[i]);
      					    		}
      					    		System.out.println("Please enter the Model you would like to Configure:");
      					    		 while ((fromUser = stdIn.readLine()) != null) {
      					    			outObj.writeObject(fromUser);
      			      				    outObj.flush(); 
      			      				    break;
      					    		 }
      					    	}
      					    	if(!(obj instanceof String[])){
      					    		System.out.println("Start Configuring Your Own Car");
      					    		cmo.extractAuto((Automobile) obj);
      								//Automobile tempAuto= (Automobile)obj;
      								SelectCarOption sco = new SelectCarOption();
      								sco.selection((Automobile)obj);
      								break;
      					    	}
      					    }
      				} catch (ClassNotFoundException e) {
      					// TODO Auto-generated catch block
      					e.printStackTrace();
      				}
      			}
      	    	
              }
              continue;
              }
    		  
    	   // while ( (strInput = reader.readLine()) != null)
    	    //handleInput (strInput);
    	  }
    	  catch (IOException e){
    	  if (DEBUG) System.out.println ("Handling session with "
    	        + strHost + ":" + iPort);
    	  } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
    
    public String[] listModels(){
    	 
   		  ObjectOutputStream outObj = null;
   		  ObjectInputStream inObj = null;
   		  Object obj=null;
   		
   		 
   		  try {
			outObj = new ObjectOutputStream(sock.getOutputStream());
			inObj = new ObjectInputStream(sock.getInputStream());
			outObj.writeObject("RequestingAllModels");
			outObj.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  try {
				while ((obj = inObj.readObject()) != null) {
					//System.out.println("CLIENT CHECK 1:"+ obj);
				    	if(obj instanceof String[]){
				    		list = (String[]) obj;
				    		break;
				    }
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
			return list;
			
    }
 

    	/*public void sendOutput(String strOutput){
    	  try {
    	    writer.write(strOutput, 0, strOutput.length());
    	  }
    	  catch (IOException e){
    	    if (DEBUG) System.out.println 
    	               ("Error writing to " + strHost);
    	  }
    	}*/
    	
        public void handleInput(String strInput){
            System.out.println(strInput);
    }       

    public Automobile getAuto(String modelChosen){
    	/*Iterator<Automobile> it = lhm.values().iterator();
		while (it.hasNext())
		{
			Automobile tempAuto = it.next(); 
			if(modelChosen.matches(tempAuto.getMake()+">>"+tempAuto.getModel()+">>"+tempAuto.getName())){
				return tempAuto;
			}
		}
		return null;*/
    	System.out.println("##^^^##"+modelChosen);
    	Automobile tempAuto = new Automobile();
    	ObjectOutputStream outObj = null;
 		  ObjectInputStream inObj = null;
 		  Object obj=null;
 		 
 		  try {
			outObj = new ObjectOutputStream(sock.getOutputStream());
			inObj = new ObjectInputStream(sock.getInputStream());
			 outObj.writeObject(modelChosen);
			    outObj.flush(); 
			   
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
    		       
				    while ((obj = inObj.readObject()) != null) {
				    	System.out.println("&&&***&&&"+obj.toString());
  					    	if(!(obj instanceof String[])){
  					    		
  					    		
  								tempAuto= (Automobile)obj;
  								System.out.println("&&&___&&&"+tempAuto.toString());
  								break;
  					    	}
  					    }
				    
	    		 
			
		 }catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		myAuto = tempAuto;
    	return tempAuto;
    	
    	
    }
        
    public LinkedHashMap<String,String[]> getProps(String modelChosen){
    	Automobile colAuto =  getAuto(modelChosen);
    	System.out.println("---------"+colAuto);
    	LinkedHashMap<String,String[]> propMap = new LinkedHashMap<String,String[]>();
    	String oSet = colAuto.getSingleOpsetByName("Color");
    	propMap.put("Color",oSet.substring(1,oSet.length()-2).split(","));
    	oSet = colAuto.getSingleOpsetByName("Transmission");
    	propMap.put("Transmission",oSet.substring(1,oSet.length()-2).split(","));
    	oSet = colAuto.getSingleOpsetByName("Brakes");
    	propMap.put("Brakes",oSet.substring(1,oSet.length()-2).split(","));
    	oSet = colAuto.getSingleOpsetByName("AirBags");
    	propMap.put("AirBags",oSet.substring(1,oSet.length()-2).split(","));
    	oSet = colAuto.getSingleOpsetByName("PowerMonnroof");
    	propMap.put("PowerMonnroof",oSet.substring(1,oSet.length()-2).split(","));
    	return propMap;
       	//System.out.println("---------"+oSet);
    	//return oSet.substring(1,oSet.length()-2).split(",");
    } 
    
    
    
    
    
    public String[] getColor(String modelChosen){
    	Automobile colAuto =  getAuto(modelChosen);
    	System.out.println("---------"+colAuto);
    	String oSet = colAuto.getSingleOpsetByName("Color");
    	System.out.println("---------"+oSet);
    	return oSet.substring(1,oSet.length()-2).split(",");
    }
    
    
    public String[] getTransmission(String modelChosen){
    	Automobile colAuto =  getAuto(modelChosen);
    	System.out.println("---------"+colAuto);
    	String oSet = colAuto.getSingleOpsetByName("Transmission");
    	System.out.println("---------"+oSet);
    	return oSet.substring(1,oSet.length()-2).split(",");
    }
    
    public String[] getBrakes(String modelChosen){
    	Automobile colAuto =  getAuto(modelChosen);
    	System.out.println("---------"+colAuto);
    	String oSet = colAuto.getSingleOpsetByName("Brakes");
    	System.out.println("---------"+oSet);
    	return oSet.substring(1,oSet.length()-2).split(",");
    }

    public String[] getAirBags(String modelChosen){
		Automobile colAuto =  getAuto(modelChosen);
		System.out.println("---------"+colAuto);
		String oSet = colAuto.getSingleOpsetByName("AirBags");
		System.out.println("---------"+oSet);
		return oSet.substring(1,oSet.length()-2).split(",");
    }
    
    public String[] getPMoonRoof(String modelChosen){
		Automobile colAuto =  getAuto(modelChosen);
		
		String oSet = colAuto.getSingleOpsetByName("PowerMonnroof");
		System.out.println("---------"+oSet);
		return oSet.substring(1,oSet.length()-2).split(",");
    }
    
    public float getOpPrice(String modelChosen,String OPSet){
    	System.out.println("---------"+modelChosen +"-----------"+OPSet);
		Automobile colAuto =  myAuto;
		float opPrice = colAuto.getOptionCostByName(OPSet.trim());
		return opPrice;
    }
   
    public float getTotal(String modelChosen, String color, String transmission, String brakes, String airbags,String pmroof){
		Automobile colAuto =  myAuto;
		System.out.println("$$$$$"+modelChosen+"$$"+color+"$$"+transmission);
		colAuto.setOptionChoice("Color",color);
		colAuto.setOptionChoice("Transmission",transmission);
		colAuto.setOptionChoice("Brakes",brakes);
		colAuto.setOptionChoice("AirBags",airbags);
		colAuto.setOptionChoice("PowerMonnroof",pmroof);
		return colAuto.getTotalPrice();
    }
        
    public void closeSession(){
       try {
          //writer = null;
          //reader = null;
          sock.close();
       }
       catch (IOException e){
         if (DEBUG) System.err.println
          ("Error closing socket to " + strHost);
       }       
    }

    public void setHost(String strHost){
            this.strHost = strHost;
    }

    public void setPort(int iPort){
            this.iPort = iPort;
    }
    
    
    
    /*public static void main (String arg[]){
    	   // debug main; does daytime on local host 
    	    String strLocalHost = "";
    	  try{
    	      strLocalHost = 
    	        InetAddress.getLocalHost().getHostName();
    	  }
    	 catch (UnknownHostException e){
    	      System.err.println ("Unable to find local host");
    	 }
    	  DefaultSocketClient d = new DefaultSocketClient
    	     (strLocalHost, iDAYTIME_PORT);
    	  d.start();
    	 }*/

}// class DefaultSocketClient

