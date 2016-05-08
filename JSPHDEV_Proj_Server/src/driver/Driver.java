package driver;

import adapter.BuildAuto;
import adapter.CreateAuto;
import adapter.UpdateAuto;
/**
 * Driver to test DB Operations on Changes happening to Linked HashMap
 * @author Amrata Kasture
 *
 */
public class Driver {

	public static void main(String[] args) {
		
		CreateAuto ba1 = new BuildAuto();
		UpdateAuto ba2 = new BuildAuto();
		ba1.buildAuto("ToyotaPriusBasic.prop","prop");
		ba1.buildAuto("ToyotaPriusEco.prop","prop");
		ba1.buildAuto("ToyotaPriusTouring.prop","prop");
		
		ba2.deleteAuto("ToyotaPriusTouring");
		
		//ba2.updateBasePrice("ToyotaPriusTouring",23500);
		//ba2.updateOptionSetName("ToyotaPriusEco","PowerMoonroof", "SuperMoonroof");
		//ba2.updateOptionPrice("ToyotaPriusTouring", "Brakes","ABS",5000);
	}

}
