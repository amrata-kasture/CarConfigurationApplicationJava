package exception;

import java.util.Scanner;

import adapter.BuildAuto;
import adapter.CreateAuto;
import adapter.UpdateAuto;

/**This is Helper Class
 * @author Amrata Kasture
 *
 */
public class FixFileLocation {
	ErrorTracker et;
	
	//This method fixes wrong FilePath by asking user to provide correct path as Input
	public String requestPath(int en){
		et = ErrorTracker.WRONG_FILE_PATH;
		System.out.println(et.getDescription());
		System.out.println("Please provide below, accurate File Path and File Name :");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}
	
	//This method Stops execution if File input output operation has been interrupted due to some reasons
	public String ioFix(int en){
		et = ErrorTracker.IO_ISSUE;
		System.out.println(et.getDescription());
		System.out.println("Perform Manual Check for potential causes and re-run the application");
		System.out.println("Type below 'Exit' to stop the Application");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}
	
	//This method prompts User to assign required access level to input file and continues to run once access granted
	public String aceessFix(int en){
		et = ErrorTracker.FILE_ACCESS_DENIED;
		System.out.println(et.getDescription());
		System.out.println("Assign required Access permissions and type below as 'Done'");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}
}
