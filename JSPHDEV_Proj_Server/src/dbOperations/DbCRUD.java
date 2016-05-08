package dbOperations;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Properties;

import model.Automobile;

/**
 * This class implements DBOps interface and includes all CRUD functions responsible 
 * for persisting DB in line with LinkedHashMap.
 * @author Amrata Kasture
 *
 */


public class DbCRUD implements DBOps{  
	Calendar calendar = Calendar.getInstance();
	//java.util.Date utilDate = new java.util.Date();
   // java.sql.Date updateDate = new java.sql.Date(utilDate.getTime());
    java.sql.Timestamp updateDate = new java.sql.Timestamp(calendar.getTime().getTime());
     

    /**
     * Method responsible for creating connection
     */
	public static Connection getConnection()
			throws SQLException, IOException, ClassNotFoundException
	{  
		Properties props = new Properties();
		String fileName = "MakeDB.properties";
		FileInputStream in = new FileInputStream(fileName);
		props.load(in);

		String drivers = props.getProperty("JDBC_DRIVER");
		Class.forName("com.mysql.jdbc.Driver");
		String DBName = props.getProperty("DB_NAME");
		System.out.println("Using Databse: "+DBName);
		return DriverManager.getConnection("jdbc:mysql://localhost/CarModelInfo?user=root");
	}

	/**
     * Method responsible for creating Table is DB
     */
	public static void createTable(String tableName,
			BufferedReader in, Statement stmt)
					throws SQLException, IOException
	{  
		String line = in.readLine();
		String command = "CREATE TABLE " + tableName
				+ "(" + line + ")";
		stmt.executeUpdate(command);

		while ((line = in.readLine()) != null)
		{  
			command = "INSERT INTO " + tableName
					+ " VALUES (" + line + ")";
			stmt.executeUpdate(command);
		}
	}

	/**
     * Method responsible for Viewing DB Table data
     */
	public static void showTable(String tableName,
			Statement stmt) throws SQLException
	{  
		String query = "SELECT * FROM "+ tableName+" ;";
		ResultSet rs = stmt.executeQuery(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		while (rs.next())
		{  for (int i = 1; i <= columnCount; i++)
		{  if (i > 1) System.out.print(", ");
		System.out.print(rs.getString(i));
		}
		System.out.println();
		}
		rs.close();
	}

	/**
     * Method responsible for Adding data to CarModels,Options,OptionSets, Configurations Tables when new model added to LinkedHashMap
     */
	@Override
	public int AddCarModel(Automobile auto) {
		int res=1;
		Properties props = new Properties();
		try
		{  Connection connection = getConnection();
		Statement statement = (Statement) connection.createStatement();

		StringBuilder sb = new StringBuilder();
		sb.append(auto.getMake().trim()).append(">>").append(auto.getModel().trim()).append(">>").append(auto.getName().trim());
	

		String fileName = "CarModels.dat";
		FileInputStream in = new FileInputStream(fileName);
		props.load(in);
		
		String query =  props.getProperty("GETIDRIGHT");
		statement.executeUpdate(query);
		query =  props.getProperty("ADDCARMODELS");
		
		PreparedStatement preparedStmt = connection.prepareStatement(query);
		preparedStmt.setString (1, sb.toString());
		preparedStmt.setFloat (2, auto.getBasePrice());
		preparedStmt.setTimestamp (3, updateDate); 
		preparedStmt.execute();

				if(AddRowOptionSets("OptionSets", connection, statement, auto)==1)
			System.out.println("Option Sets inserted successfully for "+sb.toString());
		if(AddRowOptions("Options",connection, statement, auto)==1)
			System.out.println("Options inserted successfully for "+sb.toString());
		if(AddRowConfigurations("Configurations",connection, statement, auto)==1)
			System.out.println("Configurations inserted successfully for "+sb.toString());
		statement.close();
		connection.close();

		//}

		in.close();

		}
		catch (SQLException ex)
		{  res=0;
			System.out.println ("SQLException:");
			while (ex != null)
			{  
				System.out.println ("SQLState: "
						+ ex.getSQLState());
				System.out.println ("Message:  "
						+ ex.getMessage());
				System.out.println ("Vendor:   "
						+ ex.getErrorCode());
				ex = ex.getNextException();
				System.out.println ("");
			}
		}
		catch (IOException ex)
		{  res=0;
			System.out.println("Exception: " + ex);
			ex.printStackTrace ();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return res; 
	}

	/**
     * Method responsible for Adding data to OptionSets Table when new model added to LinkedHashMap
     */
	public int AddRowOptionSets(String tableName, Connection connection, Statement statement,  Automobile auto) {
		Properties props = new Properties();

		int res=1;
		String[] OSList = auto.getAllOpsetNames();
	
		String fileName = tableName + ".dat";
		FileInputStream in;
		try {
			in = new FileInputStream(fileName);
			props.load(in);
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		try {
			//	Iterator iter = lhs.iterator();
			String query =  props.getProperty("GETIDRIGHT");
			statement.executeUpdate(query);
			query =  props.getProperty("ADDOPTIONSETS");
			
			for(int i = 0;i<OSList.length;i++){
				StringBuilder sb = new StringBuilder();
				sb.append(OSList[i]); 
				PreparedStatement preparedStmt = connection.prepareStatement(query);
				preparedStmt.setString (1, sb.toString());
				preparedStmt.setTimestamp (2, updateDate); 
				preparedStmt.execute();
				
			}
		} catch (SQLException e) {
			res=0;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;

	}

	/**
     * Method responsible for Adding data to Options Tables when new model added to LinkedHashMap
     */
	public int AddRowOptions(String tableName,  Connection connection, Statement statement,  Automobile auto) {
		Properties props = new Properties();

		int res=1;
		String[] OSList = auto.getAllOpsetNames();
		String fileName = tableName + ".dat";
		FileInputStream in;
		try {
			in = new FileInputStream(fileName);
			props.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			//	Iterator iter = lhs.iterator();
			String query =  props.getProperty("GETIDRIGHT");
			statement.executeUpdate(query);
			query =  props.getProperty("ADDOPTIONS");
			
			for(int i = 0;i<OSList.length;i++){
				String[][] OList = auto.getAllOptionNames(OSList[i]);
				//System.out.println("SETName: "+OSList[i]);
				for(int j = 0;j<OList.length;j++){
					//System.out.println("^^^^^^"+Arrays.toString(OList[j]));
					String[] Ops = OList[j];
					for(int k=0; k<Ops.length;k++){
						StringBuilder sb = new StringBuilder();
						String[] temp = OList[j][k].split("=");
						PreparedStatement preparedStmt = connection.prepareStatement(query);
						//System.out.println("OName: "+temp[0]);
						//System.out.println("OPri: "+temp[1]);
						preparedStmt.setString (1, temp[0]);
						preparedStmt.setFloat (2, Float.parseFloat(temp[1]));
						preparedStmt.setTimestamp (3, updateDate); 
						preparedStmt.execute();
					}
				}
			}
		} catch (SQLException e) {
			res=0;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;


	}

	/**
     * Method responsible for Adding data to Configurations Tables when new model added to LinkedHashMap
     */

	public int AddRowConfigurations (String tableName,  Connection connection, Statement statement,  Automobile auto) {
		Properties props = new Properties();
		int res=1;
		String[] OSList = auto.getAllOpsetNames();
		String fileName = tableName + ".dat";
		FileInputStream in;
		try {
			in = new FileInputStream(fileName);
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			String configQuery =  props.getProperty("GETIDRIGHT");
			statement.executeUpdate(configQuery);
			configQuery =  props.getProperty("ADDCONFIGURATIONS");
			//System.out.println("*****"+configQuery);
			int modelId = 0;
			
			String ModelName = auto.getMake()+">>"+auto.getModel()+">>"+auto.getName();
			//System.out.println("ModelName:"+ModelName+"#");
			String query = "Select Id from CarModels where Model_Name like '"+ModelName+"';";
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()){
				modelId = rs.getInt(1);
			}

			//System.out.println("ModelID = "+modelId);
			
			for(int i = 0;i<OSList.length;i++){
				String[][] OList = auto.getAllOptionNames(OSList[i]);


				for(int j = 0;j<OList.length;j++){
					//System.out.println("^^^^^^"+Arrays.toString(OList[j]));
					query = "Select Id from OptionSets where Set_Name = '"+OSList[j]+"';";
					rs = statement.executeQuery(query);
					int setId = 0;
					while (rs.next()){
						setId = rs.getInt(1);
					}
					//System.out.println("SetID = "+setId);
					String[] Ops = OList[j];
					//System.out.println("IMPPPP"+ Arrays.toString(Ops));
					for(int k=0; k<Ops.length;k++){
						StringBuilder sb = new StringBuilder();
						String[] temp = OList[j][k].split("=");
						//System.out.println("OName: "+temp[0]);
						//System.out.println("OPri: "+temp[1]);
						query = "Select Id from Options where Option_Name = '"+temp[0]+"';";
						rs = statement.executeQuery(query);
						int OpId = 0;
						while (rs.next()){
							OpId = rs.getInt(1);
						}
						//System.out.println("OpID = "+OpId);
						PreparedStatement preparedStmt = connection.prepareStatement(configQuery);
						preparedStmt.setInt (1, modelId);
						preparedStmt.setInt(2, setId); 
						preparedStmt.setInt (3, OpId); 
						preparedStmt.setTimestamp (4, updateDate); 
						preparedStmt.execute();


					}
				}
				break;
			}
		} catch (SQLException e) {
			res=0;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;


	}

	/**
     * Method responsible for Deleting data from CarModels,Options,OptionSets, Configurations Tables when model removed from LinkedHashMap
     */
	@Override
	public int deleteModel(String autoName) {
		Properties props = new Properties();
		String[] autoNameArr = autoName.split("(?<=.)(?=\\p{Lu})");
		//System.out.println("autoNameArr ===="+Arrays.toString(autoNameArr));
		String ModelName = autoNameArr[0]+">>"+autoNameArr[1]+">>"+autoNameArr[2];
		int res =1;
		try {


			Connection connection = getConnection();
			Statement statement = (Statement) connection.createStatement();

			int modelId = 0;

			//System.out.println("ModelName:"+ModelName);
			String query = "Select Id from CarModels where Model_Name like '"+ModelName+"';";
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()){
				modelId = rs.getInt(1);
			}

			//System.out.println("ModelID = "+modelId);

			String fileName = "Configurations.dat";
			FileInputStream in;
	
				in = new FileInputStream(fileName);
				props.load(in);
			

			query =  props.getProperty("DELETEMODELCONFIGURATIONS");
			statement.executeUpdate(query+modelId);

			fileName = "Options.dat";
		
				in = new FileInputStream(fileName);
				props.load(in);
			

			query =  props.getProperty("DELETEOPTION");
			statement.executeUpdate(query);

			fileName = "OptionSets.dat";
	
				in = new FileInputStream(fileName);
				props.load(in);
			

			query =  props.getProperty("DELETEOPTIONSET");
			statement.executeUpdate(query);

			fileName = "CarModels.dat";
		
				in = new FileInputStream(fileName);
				props.load(in);
			

			query =  props.getProperty("DELETECARMODEL");
			statement.executeUpdate(query+modelId);

			rs.close();
		} catch (SQLException | ClassNotFoundException | IOException e) {
			res=0;
			e.printStackTrace();
		}
		return res;
	}

	/**
     * Method responsible for updating CarModels Table to reflect updated price in LinkedHashMap
     */
	@Override
	public int updateModelBasePrice(Automobile auto) {
		Properties props = new Properties();
		int res=1;
		try {
			Connection connection = getConnection();
			Statement statement = (Statement) connection.createStatement();
			String fileName = "CarModels.dat";
			FileInputStream in;
			in = new FileInputStream(fileName);
			props.load(in);
			int modelId = 0;
			float price = 0;
			
			String ModelName = auto.getMake()+">>"+auto.getModel()+">>"+auto.getName();
			//System.out.println("ModelName:"+ModelName+"#");
			String query = "Select Id,Model_Baseprice from CarModels where Model_Name like '"+ModelName+"';";
			ResultSet rs = statement.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next())
			{  
				for (int i = 1; i <= columnCount; i++)
				{  
					modelId = rs.getInt(1);
					price = rs.getFloat(2);
				}
			}
			
			//System.out.println("ModelID = "+modelId);
			//System.out.println("ModelPrice = "+price);
			
			if(price!=auto.getBasePrice()){
			query =  props.getProperty("UPDATEMODELBASEPRICE");
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setFloat (1, auto.getBasePrice());
			preparedStmt.setTimestamp (2, updateDate);
			preparedStmt.setInt (3, modelId);
			preparedStmt.executeUpdate();
			}
			rs.close();	
			
		} catch (SQLException | ClassNotFoundException | IOException e) {
			res=0;
			e.printStackTrace();
		}
		return res;

	}

	/**
     * Method responsible for updating OptionSets Table to reflect updated OptionSetName in LinkedHashMap
     */
	@Override
	public int updateModelOSName(Automobile auto,String OSName,String newName) {
		Properties props = new Properties();
		int res=1;	
		try {
			Connection connection = getConnection();
			Statement statement = (Statement) connection.createStatement();
			String query = "Select Id from OptionSets where Set_Name like '"+OSName+"';";
			ResultSet rs = statement.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int setId =0;
			while (rs.next()){
				setId = rs.getInt(1);
			}
		
			String fileName = "OptionSets.dat";
			FileInputStream in = new FileInputStream(fileName);
			props.load(in);
			query =  props.getProperty("UPDATEOPTIONSET");
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setString (1, newName);
			preparedStmt.setString (2,OSName);
			preparedStmt.execute();
	 }catch (SQLException | ClassNotFoundException | IOException e) {
			res=0;
			e.printStackTrace();
		}
		return res;
	}

	/**
     * Method responsible for updating Options Table to reflect updated Option price in LinkedHashMap
     */
	@Override
	public int updateDBOptionPrice(Automobile tempAuto, String optionname, String option, float newprice) {
		Properties props = new Properties();
		int res=1;	
		try {
			Connection connection = getConnection();
			Statement statement = (Statement) connection.createStatement();
			String query = "Select Id from Options where Option_Name like '"+option+"';";
			ResultSet rs = statement.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int opId =0;
			while (rs.next()){
				opId = rs.getInt(1);
			}
		
			String fileName = "Options.dat";
			FileInputStream in = new FileInputStream(fileName);
			props.load(in);
			query =  props.getProperty("UPDATEOPTIONPRICE");
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.setFloat (1, newprice);
			preparedStmt.setString (2,option);
			preparedStmt.execute();
	 }catch (SQLException | ClassNotFoundException | IOException e) {
			res=0;
			e.printStackTrace();
		}
		return res;
	}		



}

