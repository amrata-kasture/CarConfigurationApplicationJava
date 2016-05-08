package dbOperations;

import java.io.BufferedReader;
import java.sql.SQLException;
import java.sql.Statement;

import model.Automobile;
/**
 * Interface to access DB Operations through ProxyAutomobile
 * @author amrata
 *
 */
public interface DBOps {

	public int AddCarModel(Automobile automob);
	public int deleteModel(String autoName);
	public int updateModelOSName(Automobile tempAuto, String optionSetname, String newName);
	public int updateModelBasePrice(Automobile tempAuto);
	public int updateDBOptionPrice(Automobile tempAuto, String optionname, String option, float newprice);
	
	
	
}
