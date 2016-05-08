package exception;

import java.util.Scanner;

import adapter.BuildAuto;
import adapter.CreateAuto;
import model.Automobile;

/**This is Helper class
 * @author Amrata Kasture
 *
 */
public class FixAutoProperties {
	ErrorTracker et;
	
	//This method fixes missing BasePrice through User Input
	public String fixBasePrice(int en){
		et = ErrorTracker.BASE_PRICE_MISSING;
		System.out.println(et.getDescription());
		System.out.println("Provide the Base Price value below:");
		Scanner sc = new Scanner(System.in);
		return sc.next();
	}
	
	//This method fixes missing Option Values through User Input
	public String fixOptions(int en){
		et = ErrorTracker.OPTION_VALUES_MISSING;
		System.out.println(et.getDescription());
		System.out.println("Add the missing Options below in appropriate format");
		Scanner sc = new Scanner(System.in);
		return sc.nextLine();
	}
}
