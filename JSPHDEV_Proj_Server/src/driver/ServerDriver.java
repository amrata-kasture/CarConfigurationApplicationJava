package driver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import defaultSocket.DefaultSocketClient;
import server.BuildCarModelOptions;
import util.LinkedProperties;

/**Driver class for Setting up server Socket and enabling communication
 * @author Amrata Kasture
 *
 */
public class ServerDriver {
	 static AtomicInteger numClients = new AtomicInteger(0);
	 static ServerSocket serverSocket = null;
	
	public static void main(String[] args) throws IOException {
	   
        try {
            serverSocket = new ServerSocket(4444);
            newThread();
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }

	     
       

        
        /*DefaultSocketClient clientSocket = null;
        try {
        	Socket socket = serverSocket.accept();
            clientSocket = new DefaultSocketClient(socket);
            clientSocket.start();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                    System.exit(1);
        }*/
		
        
        /*ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }*/

        //  PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
     //   BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        // At this point call Deserialize object function from Deserialize class and then decide on further
      //  String inputLine = "", outputLine ="";
     //   AutoServer as = new BuildAuto();
       // as.buildAutoFromPropFile("ToyotaPriusBasic.prop");
     //   LinkedProperties inPro = null;
    //    Object obj = null;
     //   ObjectInputStream inObj = new ObjectInputStream(clientSocket.getInputStream());
      //  ObjectOutputStream OutObj = new ObjectOutputStream(clientSocket.getOutputStream());
      //  BuildCarModelOptions bcmo = new BuildCarModelOptions();
      
       
		/*	try {
				//while ((inputLine = in.readLine()) != null || (inPro = (LinkedProperties) inObj.readObject()) != null) {
				while ((obj = inObj.readObject()) != null) {
					if(!(obj instanceof String)){
						inPro = (LinkedProperties) obj;
				    // outputLine = kkp.processInput(inputLine);
					boolean ACK;
					//DeserializeObject dObj= new DeserializeObject();
					
					//ACK = bcmo.buildAutoFromProp(dObj.deserializeProp(inputLine));
					System.out.println("SYSTEM CHECK1: "+ inPro.toString());
					ACK = bcmo.buildAutoFromProp(inPro);
					System.out.println("SYSTEM CHECK2: "+ ACK);
					if(ACK == true){
						outputLine = "File Have been uploaded Successfully. New Model has been created.";
					}else{
						outputLine = "File Upload has been interrupted. Please verify File Format & internet connectivity.";
					}
				     out.println(outputLine);
				     if (outputLine.equals("Bye."))
				        break;
					}else if(obj instanceof String){
						if(obj.equals("RequestingAllModels")){
							System.out.println("SYSTEM CHECK4: "+ obj.toString() );
							System.out.println(Arrays.toString(bcmo.listAllModels()));
							OutObj.writeObject(bcmo.listAllModels());
							OutObj.flush();
						}
					}
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
        out.close();
       // in.close();
        clientSocket.closeSession();
        //clientSocket.close();
        serverSocket.close();*/
		
	}



public static void newThread(){
    Thread thread =new Thread("C"+numClients.getAndIncrement()){
        public void run()
        {   
            while(true) {
                try {
                    accept();
                } catch (Exception e) {
                    // lof the exception
                }
            }
        }
    };
    thread.start();
}

public static void accept(){
	DefaultSocketClient clientSocket = null;
    try
    {
    	Socket socket = serverSocket.accept();
    	 clientSocket = new DefaultSocketClient(socket);
        new Thread(clientSocket).start();
        System.out.println("A new client has just connected.");
    } catch(IOException e)
    {
        System.out.println("User disconnected");
        System.exit(0);
    }
}
}
