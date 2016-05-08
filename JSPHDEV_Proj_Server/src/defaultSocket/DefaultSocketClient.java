package defaultSocket;
//import java.io.BufferedReader;

//import java.io.BufferedWriter;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.InetAddress;
import java.net.Socket;
//import java.net.UnknownHostException;
import java.util.Arrays;

import server.BuildCarModelOptions;
import util.LinkedProperties;

/**
 * This Class is used by ServerDriver to handles multiple client sessions and responds to each request accordingly
 * @author Amrata Kasture
 *
 */


public class DefaultSocketClient extends Thread implements SocketClientInterface, SocketClientConstants {

    //private BufferedReader reader;
    //private BufferedWriter writer;
    private Socket sock;
    private String strHost;
    private int iPort;
    private ObjectInputStream ois;
    private ObjectOutputStream oos; 
    Object obj= null;
    LinkedProperties inPro = null;
    BuildCarModelOptions bcmo = new BuildCarModelOptions();
    
    public DefaultSocketClient(String strHost, int iPort) {       
            setPort (iPort);
            setHost (strHost);
    }//constructor
    
    public DefaultSocketClient(Socket s) {   
        this.sock = s;
    }//constructor

    /*public void run(){
       if (openConnection()){
          handleSession();
          closeSession();
       }
    }*/
    
    public void run() {
        try {
            ois = new ObjectInputStream(sock.getInputStream());
            oos = new ObjectOutputStream(sock.getOutputStream());
            handleSession();
            closeSession();
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
    
    /*public boolean openConnection(){

    	   try {
    		   if(this.sock == null)
    	     sock = new Socket(strHost, iPort);                    
    	   }
    	   catch(IOException socketError){
    	     if (DEBUG) System.err.println
    	        ("Unable to connect to " + strHost);
    	     return false;
    	   }
    	   try {
    	     reader = new BufferedReader
    	      (new InputStreamReader(sock.getInputStream()));
    	     writer = new BufferedWriter
    	      (new OutputStreamWriter (sock.getOutputStream()));
    	     //ObjectOutputStream oos = new ObjectOutputStream(
         		socket.getOutputStream());
   				oos.flush(); 
   				oos.writeObject(availableModels);
				oos.flush();
				ois = new ObjectInputStream(socket.getInputStream());
				ois.skip(Long.MAX_VALUE);
			//	String clientOption = (String) ois.readObject();
   				
    	   }
    	  catch (Exception e){
    	     if (DEBUG) System.err.println
    	       ("Unable to obtain stream to/from " + strHost);
    	     return false;
    	  }
    	  return true;
    	}*/
    
   
    
    
    public void handleSession(){
    	 /* String strInput = "";
    	  if (DEBUG) System.out.println ("Handling session with "
    	            + strHost + ":" + iPort);
    	  try {
    	    while ( (strInput = ois.readObject()) != null)
    	    handleInput (strInput);
    	  }
    	  catch (IOException e){
    	  if (DEBUG) System.out.println ("Handling session with "
    	        + strHost + ":" + iPort);
    	  }*/
    	try {
			//while ((inputLine = in.readLine()) != null || (inPro = (LinkedProperties) inObj.readObject()) != null) {
			while ((obj = ois.readObject()) != null) {
				if(!(obj instanceof String)){
					inPro = (LinkedProperties) obj;
			    // outputLine = kkp.processInput(inputLine);
				boolean ACK;
				//DeserializeObject dObj= new DeserializeObject();
				
				//ACK = bcmo.buildAutoFromProp(dObj.deserializeProp(inputLine));
				System.out.println("SYSTEM CHECK1: "+ ois.toString());
				ACK = bcmo.buildAutoFromProp(inPro);
				System.out.println("SYSTEM CHECK2: "+ ACK);
				if(ACK == true){
					sendOutput("File Have been uploaded Successfully. New Model has been created.");
				}else{
					sendOutput("File Upload has been interrupted. Please verify File Format & internet connectivity.");
				}
				}else if(obj instanceof String){
					if(obj.equals("RequestingAllModels")){
						System.out.println("SYSTEM CHECK4: "+ obj.toString() );
						System.out.println(Arrays.toString(bcmo.listAllModels()));
						sendOutput(bcmo.listAllModels());
					}
						//while ((obj = ois.readObject()) != null) {
					else{
						System.out.println("SYSTEM CHECK5: "+ obj.toString() );
							String temp = (String) obj;
							String userChoice = temp.replaceAll(">>", "");;
							sendOutput(bcmo.searchAndReturnAuto(userChoice));
							System.out.println("SYSTEM CHECK 6: "+ bcmo.searchAndReturnAuto(userChoice).toString() );
						}
					
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	
    	}       

    	public void sendOutput(Object obj){
    	  try {
    	   // writer.write(strOutput, 0, strOutput.length());
    		  oos.writeObject(obj);
    		  oos.flush();
    	  }
    	  catch (IOException e){
    	    if (DEBUG) System.out.println 
    	               ("Error writing to " + strHost);
    	  }
    	}
    	
        public void handleInput(String strInput){
            System.out.println(strInput);
    }       

    public void closeSession(){
       try {
          //writer = null;
          //reader = null;
    	  oos.close();
    	  ois.close();
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
    
   /* public static void main (String arg[]){
    	  
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

