package driver;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.util.Arrays;
import java.net.InetAddress;

//import client.CarModelOptionsIO;
import defaultSocket.DefaultSocketClient;
//import util.LinkedProperties;

/**
 * Driver class for Client
 * @author Amrata Kasture
 *
 */

public class ClientDriver {
    
	public static void main(String[] args) throws IOException {
		
		    InetAddress IP=InetAddress.getLocalHost();
        	DefaultSocketClient cSocket = null;
        	cSocket = new DefaultSocketClient(IP.getHostAddress(), 4444);
        	cSocket.start();
          

	}

}
