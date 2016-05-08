package client;

import java.util.Scanner;

import model.Automobile;

/**
 * Class for capturing User selection and providing total price
 * @author Amrata Kasture
 *
 */

public class SelectCarOption {
	
	public void selection(Automobile auto){
		Automobile tempAuto= auto;
			for(int i=0;i< tempAuto.getOpSetSize(); i++){
				Scanner scn = new Scanner(System.in);
				String oSet = tempAuto.getSingleOpsetNameByIndex(i);
				System.out.println("Select the "+oSet+" of your car");
				tempAuto.setOptionChoice(oSet, scn.nextLine());
			}
			
			System.out.println();
			System.out.println("Your Choices are as Below for:"+ tempAuto.getMake()+">>"+tempAuto.getModel()+">>"+tempAuto.getName());
			System.out.println("------------------------------------------------------------------------");
			tempAuto.printUserChoice();
			System.out.println();
			//System.out.println("User Choice: "+autoBasic.getOptionChoice("Colors"));
			//System.out.println("User Choice Cost: "+autoBasic.getOptionChoicePrice("Colors"));
			System.out.println("Your Total Car price is: "+tempAuto.getTotalPrice());
			
	}
}
