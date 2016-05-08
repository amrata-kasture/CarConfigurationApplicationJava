package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import model.OptionSet.Option;

/* Automobile Class containing all properties and related functions for Automobile object 
* @version 0.0 28 Jan 2016
* @version 0.1 04 Feb 2016
* @author Amrata Kasture  */

public class Automobile implements Serializable {
	private static final long serialVersionUID = 42L;
	private String make;
	private String model;
	private String name;
	private float basePrice;
	//private OptionSet[] opSet;
	private ArrayList<OptionSet> opSetAL = new ArrayList<OptionSet>();
	private ArrayList<Option> userChoice = new ArrayList<Option>();
	//private int counter;
	
	// 5 Constructors with all usable probabilities
	
	public Automobile(){
		//this.counter = 0;
		this.name = name;
		this.basePrice = 0;
		//Initializing empty array
		//this.opSet= new OptionSet[0];
		this.opSetAL = new ArrayList<OptionSet>();
	}
	public Automobile(int oSetSize){
		//this.counter = 0;
		this.name = name;
		this.basePrice = 0;
		this.opSetAL = new ArrayList<OptionSet>();
		/*opSet= new OptionSet[oSetSize];
		for ( int i=0; i<opSet.length; i++) {
			this.opSet[i]=new OptionSet();			
		}*/
	}
	
	public Automobile(String name,float basePrice, int size){
		this.name = name;
		this.basePrice = basePrice;
		this.opSetAL = new ArrayList<OptionSet>();
		/*opSet = new OptionSet[size];
		for ( int i=0; i<opSet.length; i++) {
			this.opSet[i]=new OptionSet();			
		}*/
	}
	
	public Automobile(String name,float basePrice){
		this.name = name;
		this.basePrice = basePrice;
		//initializing empty array as no size argument passed
		//opSet = new OptionSet[0];
		this.opSetAL = new ArrayList<OptionSet>();
	}
	
	//Future Use constructor
	public Automobile(String name,float basePrice,ArrayList<OptionSet> opSetAL){
		this.name = name;
		this.basePrice = basePrice;
		/*for ( int i=0; i<opSet.length; i++) {
			this.opSet[i]=opSet[i];			
		}*/
		this.opSetAL = opSetAL;
	} 

	//Getters and Setters
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(float basePrice) {
		this.basePrice = basePrice;
	}

	/*public OptionSet[] getOpSet() {
		return opSet;
	}*/
	
	public ArrayList<OptionSet> getOpSetAL() {
		return opSetAL;
	}
	
	public void setOpSet(String os, String oso) {
		//opSet[counter].setName(os);
		opSetAL.add(new OptionSet(os,oso));
		//opSet[counter].setOpt(oso);
		//counter++;
	}
	
	public int getOpSetSize() {
		return opSetAL.size();
	}
	
	// Finder method for OptionSet given index as argument
	public String getSingleOpsetByIndex(int index) {
		//return opSet[index].toString();
		return opSetAL.get(index).toString();
	}
	
	public String getSingleOpsetNameByIndex(int index) {
			return opSetAL.get(index).getName();
		}
	
	public String[] getAllOpsetNames() {
		String[] res=new String[opSetAL.size()];
		for(int i=0;i<res.length;i++){
			res[i]=getSingleOpsetNameByIndex(i);
		}
		return res;
	}
	
	public String[][] getAllOptionNames(String opSetName){
		String[][] res = new String[opSetAL.size()][];
		for(int i=0; i<res.length;i++){
			res[i]=opSetAL.get(i).getAllOptBySetName(opSetName);
		}
		return res;
	}
	
	public int getOpsetIndexByName(String name) {
		for(int i=0;i<opSetAL.size();i++){
			if(opSetAL.get(i).getName().equals(name)) return i;
		}
		return 0;
	}	
	
	
	//Finder method for OptionSet given name as argument
	public String getSingleOpsetByName(String name) {
		/*for(int i=0;i<opSet.length;i++){
			if(opSet[i].getName().equals(name)) return opSet[i].toString();
		}
		return opSet[0].toString();*/
		for(int i=0;i<opSetAL.size();i++){
			if(opSetAL.get(i).getName().equals(name)) return opSetAL.get(i).toString();
		}
		return null;
	}
	
	//Create Function for adding new OptionSet with its Options and their respective names and price-quotes
	public void addSingleOpsetAndOptions(String optSetName,String optName) {
		/*this.opSet = Arrays.copyOf(opSet, opSet.length + 1); 
		this.opSet[opSet.length-1]=new OptionSet();
		opSet[opSet.length - 1].setName(optSetName);
		if(!optName.contains(",")){
			updateOptionSetAddOption(optSetName,optName);
		}else{
			opSet[opSet.length - 1].setOpt(optName);
		}*/
		OptionSet OS = new OptionSet();
		OS.setName(optSetName);
		updateOptionSetAddOption(optSetName,optName);
		opSetAL.add(OS);
	}
	
	//Create Function for adding new Empty OptionSet where values can be added later
	public void addSingleOpset(String optSetName) {
		opSetAL.add(new OptionSet(optSetName));
		//OptionSet OS = new OptionSet();
		//OS.setName(optSetName);
	}
	
	//Finder method for Option by giving OptionSet Name and index of Option as argument
	public String getSingleOpsetSingleOpt(String name,int optIndex) {
		/*for(int i=0;i<opSet.length;i++){
			if(opSet[i].getName().equals(name)) return opSet[i].getSingleOptByIndex(optIndex);
		}
		return opSet[0].getSingleOptByIndex(optIndex);*/
		for(int i=0;i<opSetAL.size();i++){
			if(opSetAL.get(i).getName().equals(name)) return opSetAL.get(i).getSingleOptByIndex(optIndex);
		}
		return null;
	}
	
	//Finder method for fetching OptionSet Name by giving one of it's Option Name as argument
	public String getOptionSetNameByOptName(String optName) {
		/*for(int i=0;i<opSet.length;i++){
			Option[] tempOpt = opSet[i].getOpt();
			for(int j =0; j< tempOpt.length; j++){
			    if(tempOpt[j].getName().equals(optName)){
			    	return opSet[i].getName();
			    }
			}
		}*/
		for(int i=0;i<opSetAL.size();i++){
			ArrayList<Option> tempOpt = opSetAL.get(i).getOptAL();
			for(int j =0; j< tempOpt.size(); j++){
			    if(tempOpt.get(j).getName().equals(optName)){
			    	return opSetAL.get(i).getName();
			    }
			}
		}
		return "Results Not Found";
	}
	
	//Finder method for fetching Option Cost by giving Option Name as argument
	public float getOptionCostByName(String optName){
		for(int i=0;i<opSetAL.size();i++){
			ArrayList<Option> tempOpt = opSetAL.get(i).getOptAL();
			for(int j =0; j< tempOpt.size(); j++){
			    if(tempOpt.get(j).getName().equals(optName)){
			    	return tempOpt.get(j).getPrice();
			    }
			}
		}
		return 0;
	}
	
	//Create Function for adding Options in existing OptionSet
	public void updateOptionSetAddOption(String optSetName,String optName) {
		/*for(int i=0;i<opSet.length;i++){
			if(opSet[i].getName().equals(optSetName)){
				//Option[] tempOpt = opSet[i].getOpt();
				opSet[i].addOpt(optName);
			}
		}*/
		for(int i=0;i<opSetAL.size();i++){
			if(opSetAL.get(i).getName().equals(optSetName)){
				//Option[] tempOpt = opSet[i].getOpt();
				opSetAL.get(i).addOpt(optName);
			}
		}
	}

	//Update Function for changing Name of Reference Model
	public void updateRefModelName(Automobile auto,String newName) {
		auto.setName(newName);
	}
	
	//Update Function for changing Base Price of Reference Model
	public void updateRefModelBasePrice(Automobile auto,float newPrice) {
		auto.setBasePrice(newPrice);
	}
	
	//Update Function for changing Name of Option Set by using existing find(getter)  
	public void updateOptSetName(String oldOptSetName,String newOptSetName) {
		/*for(int i=0;i<opSet.length;i++){
			if(opSet[i].getName().equals(oldOptSetName))  opSet[i].setName(newOptSetName);
		}*/
		int validOpSetNameFound = 0;
		for(int i=0;i<opSetAL.size();i++){
			if(opSetAL.get(i).getName().equals(oldOptSetName))  {
				validOpSetNameFound++;
				opSetAL.get(i).setName(newOptSetName);
			}
		}
		if(validOpSetNameFound==0) System.out.println("The OptionSet with stated Name can not be found");
	}
	
	//Update Function for changing Name of one of the Option from given OptionSet 
	public void updateOptionSetUpdateOptionName(String optSetName,String oldOptName, String newOptName) {
		/*for(int i=0;i<opSet.length;i++){
			if(opSet[i].getName().equals(optSetName)){
				opSet[i].updateOptName(oldOptName,newOptName);
			}
		}*/
		for(int i=0;i<opSetAL.size();i++){
		if(opSetAL.get(i).getName().equals(optSetName)){
			opSetAL.get(i).updateOptName(oldOptName,newOptName);
		}
		}
	}
	
	//Update Function for changing Price of one of the Option from given OptionSet 
	public void updateOptionSetUpdateOptionPrice(String optSetName,String optName, float newOptPrice) {
		/*for(int i=0;i<opSet.length;i++){
			if(opSet[i].getName().equals(optSetName)){
				opSet[i].updateOptPrice(optName,newOptPrice);
			}
		}*/
		for(int i=0;i<opSetAL.size();i++){
			if(opSetAL.get(i).getName().equals(optSetName)){
				opSetAL.get(i).updateOptPrice(optName,newOptPrice);
			}
		}
	}
	
	
	//Delete an OptionSet from a Ref Model by passing Name of OptionSet as argument
	public void deleteOptionSet(String opSetName) {
		for(int i=0;i<opSetAL.size();i++){
			if(opSetAL.get(i).getName().equals(opSetName)){
				opSetAL.remove(i);
			}
		}
		
		
		//array size optimization after deleting OptionSet
		/*int index = 0;
		int newSize = opSet.length;
		for(int i=0;i<opSet.length;i++){
			if(opSet[i].getName().equals(opSetName)){
				newSize = opSet.length -1;
			}
		}
		OptionSet[] tempOptSet = new OptionSet[newSize];
		for(int i=0;i<opSet.length;i++){
			if(!opSet[i].getName().equals(opSetName)){
				tempOptSet[index] = opSet[i];
				index++;
			}
		}
		if(newSize == opSet.length){
			System.out.println("The OptionSet with name "+ opSetName +" not found");
		}
		opSet = tempOptSet;*/
		
		
		//unoptimized code as it is inserting empty string for deleted OptionSet 
				/*int check = 0;
				for(int i=0;i<opSet.length;i++){
					if(opSet[i].getName().equals(opSetName)){
						opSet[i].setName("");
						opSet[i].setOpt("");
						check++;
					}
				}
				if(check == 0){
					System.out.println("The OptionSet with name "+ opSetName +" not found");
				}*/
	}
		
		
	//Delete an Option from OptionSet passing their names as argument
	public void deleteOptionFromOptionSet(String opSetName,String optName) {
		/*int check = 0;
		for(int i=0;i<opSet.length;i++){
			if(opSet[i].getName().equals(opSetName)){
				opSet[i].deleteOption(optName);
				check++;
			}
		}
		if(check == 0){
			System.out.println("The OptionSet with name "+ opSetName +" not found");
		}*/
		int check = 0;
		for(int i=0;i<opSetAL.size();i++){
			if(opSetAL.get(i).getName().equals(opSetName)){
				opSetAL.get(i).deleteOption(optName);
				check++;
			}
		}
		if(check == 0){
			System.out.println("The OptionSet with name "+ opSetName +" not found");
		}
	}
	
	//Customized toString for printing Object of Automotive
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		sb.append("Make of Reference Model: ").append(make).append("\n");
		sb.append("Model of Reference Model: ").append(model).append("\n");
		sb.append("Name of Reference Model: ").append(name).append("\n");
		sb.append("Base Price of Reference Model: $").append(basePrice).append("\n");
		//sb.append("Specifications of Reference Model: \n").append(Arrays.toString(opSet)).append("\n");
		sb.append("Specifications of Reference Model: \n").append(opSetAL).append("\n");
		
		//return "Automotive [name=" + name + ", basePrice=" + basePrice + ", opSet=" + Arrays.toString(opSet) + "]";
		return sb.toString();
	}
	
	
	//Print method created for printing Automobile object
	public void printAutoComplete(){
		System.out.println(toString());
	}
	
	public String getOptionChoice(String opSetName){
		int index = getOpsetIndexByName(opSetName);
		if(userChoice.size()>0){
			if(userChoice.get(index).getName().equals("")) return null;
			else return userChoice.get(index).getName();
		}else{
			return null;
		}
	}
	
	public float getOptionChoicePrice(String opSetName){
		int index = getOpsetIndexByName(opSetName);
		String choice = getOptionChoice(opSetName);
		float choiceCost = getOptionCostByName(choice);
		//System.out.println(index+"#"+userChoice.get(index).getName()+"**"+choice);
		return choiceCost;//userChoice.get(index).getPrice();
	}
	
	public void setOptionChoice(String opSetName, String optName){
		OptionSet OS = new OptionSet();
		OS.setOptChoice(optName);
		userChoice.add(OS.getOptChoice());
		//userChoice.add(OS.new Option(optName));
	} 
	
	public float getTotalPrice(){
		float totalPrice = this.basePrice;
		for(int i=0;i<opSetAL.size();i++){
			totalPrice += getOptionChoicePrice(opSetAL.get(i).getName());
		}
		return totalPrice;
	}
	
	public void printUserChoice(){
		for(int i=0;i<opSetAL.size();i++){
			System.out.println(opSetAL.get(i).getName()+" : "+userChoice.get(i).getName());
		}
	}
	
}
