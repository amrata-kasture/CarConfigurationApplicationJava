package util;

/* Class handling all file related operations like reading input file and creating Automobile object from file data.
* Also performs Serialization and deserialization of created Auto objects. 
* @version 0.0 28 Jan 2016
* @author Amrata Kasture  */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import adapter.FixAuto;
import exception.AutoException;
import exception.ErrorTracker;
import model.Automobile;

public class FileIO {
		//Function'readFileNBuildObj' will read input text file and parse for setting properties of Automotive object
	public Automobile readFileNBuildObj(String filename){
		 
		Logger logger = LoggerLocator.getLocator().getLogger();
		    
		Automobile atm = new Automobile();
		BufferedReader br = null;
		String[] modelColors = new String[10];
		boolean eof = false;
		String refModelSpec;
		try {
			File inFile = new File(filename);
			boolean doesExists = inFile.exists();
			if(!doesExists) throw new FileNotFoundException();
			boolean isReadable = inFile.canRead();
			if(!isReadable) throw new RuntimeException();
			
			FileReader fr = new FileReader(filename);
			br = new BufferedReader(fr);
			//br.close();// For testing of IO Exception : Exception No. 2
			LineNumberReader  lnr = new LineNumberReader(new FileReader(new File(filename)));
			lnr.skip(Long.MAX_VALUE);
			int lineCount = lnr.getLineNumber() + 1;//+1 for index starts at 0  
			lnr.close();
			atm = new Automobile(lineCount-4);//This code should be refactored with arrayList
			int currLine = 1;
			while(!eof){
				
				refModelSpec = br.readLine();
				if (refModelSpec == null)
					eof = true; 
				else{
						//Commented code can be used for extensive File Format checking in future
				    	//if(refModelSpec.matches("([A-Za-z])\\w+\t[|]([A-Za-z=\\s])\\w+\\s*[=]*\\s*[,]*.*"))
				    	/*if(!refModelSpec.matches("([A-Za-z])\\w+\t[|]([A-Za-z0-9=\\s])\\w+")){
				    		try {
								 token2 = st.nextToken();
							} catch (NoSuchElementException e) {
								AutoException ae = new AutoException(3);
								token2 = ae.fix(3);
							}
				    	}
					    }catch(){
					    	
					    }*/
					
						//Below Code ensures of OptionSet name exists in input File
					    StringTokenizer st = new StringTokenizer(refModelSpec,"\t+\\|");
					    String token1 = "";
					    String token2 = "";
					    
					    String[] parts = refModelSpec.split("\\|");
					    String part1 = parts[0]; 
					     
						if(part1.trim().length()==0){
							try{
									throw new RuntimeException();
								}
							catch (RuntimeException e) {
								FixAuto ae = new AutoException(ErrorTracker.INCOMPATIBLE_FILE_FORMAT);
								logger.log(Level.SEVERE, ErrorTracker.INCOMPATIBLE_FILE_FORMAT.getDescription(),ae);
								token1 = ae.wrapFix(5);
								logger.log(Level.SEVERE,"FIXED: "+ErrorTracker.INCOMPATIBLE_FILE_FORMAT.getDescription(),ae);
							}
						}else{
							token1 = st.nextToken();
						} 
						
						
						if(token1.equals("Price")){
							try {
								 token2 = st.nextToken();
							} catch (NoSuchElementException e) {
								FixAuto ae = new AutoException(ErrorTracker.BASE_PRICE_MISSING);
								logger.log(Level.SEVERE, ErrorTracker.BASE_PRICE_MISSING.getDescription(),ae);
								token2 = ae.wrapFix(3);
								logger.log(Level.SEVERE,"FIXED: "+ErrorTracker.BASE_PRICE_MISSING.getDescription(),ae);
							}
						}else{
							try {
								 token2 = st.nextToken();
							} catch (NoSuchElementException e) {
								FixAuto ae = new AutoException(ErrorTracker.OPTION_VALUES_MISSING);
								logger.log(Level.SEVERE, ErrorTracker.OPTION_VALUES_MISSING.getDescription(),ae);
								token2 = ae.wrapFix(4);
								logger.log(Level.SEVERE,"FIXED: "+ErrorTracker.OPTION_VALUES_MISSING.getDescription(),ae);
							}
						}
						
						if(currLine == 1){
							atm.setMake(token2);
							//System.out.println("Name of RefModel is "+atm.getName());
						}
						if(currLine == 2){
							atm.setModel(token2);
							//System.out.println("Price of RefModel is "+atm.getBasePrice());
						}
						if(currLine == 3){
							atm.setName(token2);
							//System.out.println("Name of RefModel is "+atm.getName());
						}
						if(currLine == 4){
							atm.setBasePrice(Float.parseFloat(token2));
							//System.out.println("Price of RefModel is "+atm.getBasePrice());
						}
						while(currLine>4 && currLine<=lineCount){
							atm.setOpSet(token1, token2);
							//System.out.println("Property of RefModel is "+token2);
							break;
						}
						currLine++;
					}
				}
		}catch (FileNotFoundException e) {
			//e.printStackTrace();
			FixAuto ae = new AutoException(ErrorTracker.WRONG_FILE_PATH);
			logger.log(Level.SEVERE, ErrorTracker.WRONG_FILE_PATH.getDescription(),ae);
			atm = readFileNBuildObj(ae.wrapFix(1));
			logger.log(Level.SEVERE,"FIXED: "+ErrorTracker.WRONG_FILE_PATH.getDescription(),ae);
		}catch (IOException e){
			//e.printStackTrace();
			FixAuto ae = new AutoException(ErrorTracker.IO_ISSUE);
			logger.log(Level.SEVERE, ErrorTracker.IO_ISSUE.getDescription(),ae);
			String action = ae.wrapFix(2);
			if(action.equals("Done")){
				atm = readFileNBuildObj(filename);
				logger.log(Level.SEVERE,"FIXED: "+ErrorTracker.IO_ISSUE.getDescription(),ae);
			} 
			else System.exit(0);
		}catch (RuntimeException e){
			e.printStackTrace();
			FixAuto ae = new AutoException(ErrorTracker.FILE_ACCESS_DENIED);
			logger.log(Level.SEVERE, ErrorTracker.FILE_ACCESS_DENIED.getDescription(),ae);
			String action = ae.wrapFix(6);
			if(action.equals("Done")){
				atm = readFileNBuildObj(filename);
				logger.log(Level.SEVERE,"FIXED: "+ErrorTracker.FILE_ACCESS_DENIED.getDescription(),ae);
			} 
			else System.exit(0);
		}/*catch (FileNotFoundException e) {
			e.printStackTrace();
			//System.out.println("");
			System.exit(0);
		} catch (IOException e){
			e.printStackTrace();
			//System.out.println("");
			System.exit(0);
		}*/
		
		return atm;
	}
	
	//The method serializeAuto is serializing Automotive objects and storing them with .dat extension 
	public void serializeAuto(Automobile auto){
		try(FileOutputStream fs = new FileOutputStream(auto.getName()+".dat")) {
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(auto);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	//The method deserializeAuto is deserializing Automotive objects from serialized .dat object files
	public void deserializeAuto(Automobile auto){
		try(FileInputStream fi = new FileInputStream(auto.getName()+".dat")) {
            ObjectInputStream os = new ObjectInputStream(fi);
            Automobile auto1 = (Automobile)os.readObject();
            os.close();
            System.out.println(auto1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	/**
     * This Function will read Property type file and create Automobile object from it
     */
    public Automobile readPropFileNBuildObj(String filename){
    	Automobile atm = new Automobile();
    	LinkedProperties props= new LinkedProperties(); 
        FileInputStream in = null;
		try {
			in = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			props.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //This loads the entire file in memory.
        
        String CarMake = props.getProperty("CarMake"); 
        atm.setMake(CarMake);
       
              if(!CarMake.equals(null))
              {
                    atm.setModel(props.getProperty("CarModel"));
                    atm.setName(props.getProperty("Style"));
                    atm.setBasePrice(Float.parseFloat(props.getProperty("BasePrice")));
                    String oSet = "";
                	String oVal = "";
                    for(String key : props.stringPropertyNames()) {
                    	if(key.matches("Option\\d+")){
                    		oSet = props.getProperty(key);
                    		atm.addSingleOpset(props.getProperty(key));
                    	}
                    	if(key.matches("OptionValue\\d+[a-z]")){
                    		oVal = props.getProperty(key);
                    		atm.updateOptionSetAddOption(oSet, props.getProperty(key));
                    	}
                    	if(key.matches("OptionPrice\\d+[a-z]")){
                    		atm.updateOptionSetUpdateOptionPrice(oSet, oVal, Float.parseFloat(props.getProperty(key)));
                    	}
                    	  //String value = props.getProperty(key);
                    	  //System.out.println(key + " => " + value);
                    	}
              }
    	return atm;
    	
    }
    
    public Automobile readPropFileNBuildObj(Properties p){
    	Automobile atm = new Automobile();
    	LinkedProperties props= new LinkedProperties(); 
    	props = (LinkedProperties) p;
        FileInputStream in = null;
		/*try {
			in = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			props.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //This loads the entire file in memory.
        */
        System.out.println("#########");
        System.out.println(props.toString());
        String CarMake = props.getProperty("CarMake"); 
        atm.setMake(CarMake);
       
              if(!CarMake.equals(null))
              {
                    atm.setModel(props.getProperty("CarModel"));
                    atm.setName(props.getProperty("Style"));
                    atm.setBasePrice(Float.parseFloat(props.getProperty("BasePrice")));
                    String oSet = "";
                	String oVal = "";
                    for(String key : props.stringPropertyNames()) {
                    	if(key.matches("Option\\d+")){
                    		oSet = props.getProperty(key);
                    		atm.addSingleOpset(props.getProperty(key));
                    	}
                    	if(key.matches("OptionValue\\d+[a-z]")){
                    		oVal = props.getProperty(key);
                    		atm.updateOptionSetAddOption(oSet, props.getProperty(key));
                    	}
                    	if(key.matches("OptionPrice\\d+[a-z]")){
                    		atm.updateOptionSetUpdateOptionPrice(oSet, oVal, Float.parseFloat(props.getProperty(key)));
                    	}
                    	  //String value = props.getProperty(key);
                    	  //System.out.println(key + " => " + value);
                    	}
              }
    	return atm;
    	
    }
    
	
}

//For avoiding auto creation of multiple log files by different processes of Application
class LoggerLocator {

    private static LoggerLocator locator = new LoggerLocator();
    private Logger logger;

    /**
     * 
     */
    private LoggerLocator() {
    	initLogger();
    }

    /**
     * @return
     */
    public static LoggerLocator getLocator(){
    	return locator;
    }

    /**
     * @return
     */
    public Logger getLogger() {
    	return logger;
    }

    /**
     * 
     */
    private void initLogger() {
    	//logger = Logger.getLogger("My General Logger");
    	logger = Logger.getLogger("Log_File.log");
    	FileHandler fh;
    	try {
    		// This block configure the logger with handler and formatter
    		//fh = new FileHandler("mylogfile.log", true);
    		fh = new FileHandler("Log_File.log",true);  
    		logger.addHandler(fh);
    		logger.setLevel(Level.ALL);
    		logger.setUseParentHandlers(false);
    		fh.setFormatter(new SimpleFormatter());
    	} catch (final SecurityException e) {
    		// }
    	} catch (final IOException e) {
    		//
    	}
    }
    
}





