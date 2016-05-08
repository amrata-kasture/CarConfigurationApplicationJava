package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/* Class holds OptionSet and Option objects of Automobiles Class. 
* @version 0.0 28 Jan 2016
* @author Amrata Kasture  */

class OptionSet implements Serializable {
	private String name;
	//private Option[] opt;
	private ArrayList<Option> optAL = new ArrayList<Option>();
	protected Option OP;
	
  // 3 Constructors with all usable probabilities
	
  OptionSet(){
	    this.name = "";
	    this.optAL = new ArrayList<Option>();//Initializing empty array
	}
 
	OptionSet(String s, int size){
		this.name = s;
		this.optAL = new ArrayList<Option>();
		/*this.opt = new Option[size];
		for ( int i=0; i<opt.length; i++) {
			this.opt[i]=new Option();			
		}*/
	}
	
	OptionSet(String s){
		this.name = s;
	}
	
	OptionSet(String s, String opts){
		this.name = s;
		this.setOpt(opts);
	}
	
	OptionSet(String s, Option op){
		this.name = s;
		this.optAL.add(op);
		//this.opt[opt.length-1] = op;
	}
	
	//this constructor is for probable future use
	OptionSet(String s, ArrayList<Option> optAL){
		this.name = s;
		this.optAL = optAL;
		/*for(int j = 0;j<op.length; j++){
		  this.opt[j] = op[j];
		}*/
	}
	
	//Getters and Setters
	protected String getName() {
		return name;
	}
	
	protected void setName(String name) {
		this.name = name;
	}
	
	/*protected Option[] getOpt() {
		return opt;
	}*/
	
	public ArrayList<Option> getOptAL() {
		return optAL;
	}

	protected void setOpt(String diffOpts) {
		StringTokenizer stOpts = new StringTokenizer(diffOpts,",");
		int size = stOpts.countTokens();
		for(int i=0;i<size;i++){
			optAL.add(new Option());
		}
		int i =0;
		while(stOpts.hasMoreTokens()) { 
		  String optName= stOpts.nextToken();
		  if(!optName.contains("=")){
			  optAL.get(i).setName(optName);
			  optAL.get(i).setPrice(0);
			  optAL.get(i).toString();
			}else{
				StringTokenizer stTemp = new StringTokenizer(optName,"=");
				optAL.get(i).setName(stTemp.nextToken());
				optAL.get(i).setPrice(Float.parseFloat(stTemp.nextToken()));
			}
			i++;
		}
		/*opt =new Option[stOpts.countTokens()];
		for(int i=0;i<opt.length;i++){
			opt[i] = new Option();
		}	
		int i =0;
		while(stOpts.hasMoreTokens()) { 
		  String optName= stOpts.nextToken();
		  if(!optName.contains("=")){
			  //System.out.println("Line under TEST1: "+ optName);
			  opt[i].setName(optName);
			  opt[i].setPrice(0);
			  opt[i].toString();
			}else{
				//System.out.println("Line under TEST2: "+ optName);
				StringTokenizer stTemp = new StringTokenizer(optName,"=");
				opt[i].setName(stTemp.nextToken());
				//System.out.println(opt[i].getName());
				opt[i].setPrice(Float.parseFloat(stTemp.nextToken()));
				//System.out.println(opt[i].getPrice());
			}
		  
			i++;
		}*/
	}
	
	//Find method for getting Option Name and Price given its index as argument
	protected String getSingleOptByIndex(int index) {
		return optAL.get(index).toString();
	}
	
	//Find method for getting Option details given its name as argument
	protected String getSingleOptByName(String name) {
		for(int i=0;i<optAL.size();i++){
			if(optAL.get(i).getName().equals(name)) return optAL.get(i).toString();
		}
		return null;
	}
	
	//Create method for adding new Option 
	protected void addOpt(String optName){
		//create new array from old array and allocate one more element
		/*this.opt = Arrays.copyOf(opt, opt.length + 1); 
		this.opt[opt.length-1]=new Option();
	    opt[opt.length - 1].setName(optName);*/
		Option OP = new Option();
		OP.setName(optName);
		optAL.add(OP);
	}
	
	//Update Method for changing Option Name
	protected void updateOptName(String oldOptName, String newOptName){
		for(int i=0;i<optAL.size();i++){
			if(optAL.get(i).getName().equals(oldOptName)) optAL.get(i).setName(newOptName);;
		}
	}
	
	//Update Method for changing Option Price
	protected void updateOptPrice(String oldOptName, float newOptPrice){
		for(int i=0;i<optAL.size();i++){
			if(optAL.get(i).getName().equals(oldOptName)) optAL.get(i).setPrice(newOptPrice);;
		}
	}
	
	//Delete Method for deleting Option
	protected void deleteOption(String optName) {
		for(int i=0;i<optAL.size();i++){
			if(optAL.get(i).getName().equals(optName)){
				optAL.remove(i);
			}
		}
		
		//Optimizing array size after deletion
		/*
		int index = 0;
		int newSize = opt.length;
		for(int i=0;i<opt.length;i++){
			if(opt[i].getName().equals(optName)){
				newSize = opt.length -1;
			}
		}
		Option[] tempOpt = new Option[newSize];
		for(int i=0;i<opt.length;i++){
			if(!opt[i].getName().equals(optName)){
				tempOpt[index] = opt[i];
				index++;
			}
		}
		if(newSize == opt.length){
			System.out.println("The Option with name "+ optName +" not found");
		}
		opt = tempOpt;
		*/
		//unoptimized code as it is inserting empty string for deleted Option value
		/*int check =0;
		for(int i=0;i<opt.length;i++){
			if(opt[i].getName().equals(optName)){
				opt[i].setName("");
				opt[i].setPrice(0);
				check++;
			}
		}
		if(check == 0){
			System.out.println("The Option with name "+ optName +" not found");
		}*/
	}
	
	protected Option getOptChoice(){
		if(OP!=null) return OP;
		else return null;
	}
	
	protected void setOptChoice(String optName){
		OP = new Option(optName);
	}
	
	//Customized toString for printing Object of OptionSet
	public String toString() {
		StringBuilder sb = new StringBuilder("");
		//TEMP//sb.append(name).append(" Options and respective costs-> \t");
		//sb.append(Arrays.toString(opt)).append("\n");
		sb.append(optAL).append("\n");
		return sb.toString();
		
		//return "OptionSet [name=" + name + ", opt=" + Arrays.toString(opt) + "]";
	}

	//Inner class Option inside OptionSet
	class Option  implements Serializable {
		private String name;
		private float price;
	
		//Constructors
		Option(){
			this.name = "";
			this.price = 0;
		}
		
		Option(String name){
			this.name = name;	
		}
		
		Option(String name,float price){
			this.name = name;
			this.price = price;
		}
		
		//Getters and Setters
		protected String getName() {
			return name.toString().trim();
		}
		protected void setName(String name) {
			this.name = name;
		}
		protected float getPrice() {
			return price;
		}
		protected void setPrice(float price) {
			this.price = price;
		}

		//Customized toString for printing Object of Option using StringBuilder
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("");
			sb.append(name);
			//TEMP//append(": $").append(price);
			return sb.toString();
			//return "Option [name=" + name + ", price=" + price + "]";
		}
		
	}
}
