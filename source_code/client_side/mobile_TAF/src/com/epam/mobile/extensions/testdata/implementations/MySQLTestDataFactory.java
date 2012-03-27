package com.epam.mobile.extensions.testdata.implementations;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.mobile.extensions.testdata.TestDataConstants;
import com.epam.mobile.extensions.testdata.TestData;
import com.epam.mobile.extensions.testdata.TestDataFactory;

public class MySQLTestDataFactory implements TestDataFactory{
	
	private Connection db_connection = null;
	// default settings
	private String driverPrefixURL = "jdbc:mysql://localhost:3306/mysql";
	private String driver = "com.mysql.jdbc.Driver";
	private String username = "root";
	private String password = "12345";
	private String dataSource = "";
	
	private static MySQLTestDataFactory instance;
	
	private MySQLTestDataFactory() throws Exception {
	}

	public static MySQLTestDataFactory getInstance() {
		return instance;
	}

	static {
		try {
			instance = new MySQLTestDataFactory();
		} catch (Exception e) {
			e.printStackTrace();
			//System.exit(-1);
		}
	}	
	
	public TestData create() {
		initialization();
		
		return new MySQLTestData(db_connection);
	}

	public TestData create(String tdObjectPath) {
		driverPrefixURL = tdObjectPath;
		initialization();
		
		return new MySQLTestData(db_connection);
	}

	
	public TestData read() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public TestData write(String dataLine) {
		// TODO Auto-generated method stub
		return null;
	}

	private void initialization(){
		try {
			db_connection = openConnection();
			Thread.sleep(3000);
			createStubDB();
		} catch (Exception e) {
			// TODO: handle exception
		}		
	}
	
	private void createStubDB() throws Exception{
		try {
			//showDBs();
						
			//Thread.sleep(3000);
			//grantAllPrivileges();
			createDB();
			use();
			createDBTable();
			addKeyValuePair("version", "1.2232");
			//printTable();
		} catch (Exception e) {
			System.out.println(e.toString());
			closeConnection();
		}
	}
	
	@SuppressWarnings("unused")
	private void printTable() throws Exception{
		Statement stmt = db_connection.createStatement();
		stmt.
			executeUpdate("select * from " + TestDataConstants.MYSQL_DB_TABLE_NAME);
	}
	
	@SuppressWarnings("unused")
	private void grantAllPrivileges() throws Exception{
		Statement stmt = db_connection.createStatement();
		stmt.
			executeUpdate("GRANT ALL PRIVILEGES ON *.* TO" + 
					username + "@'localhost'" + " IDENTIFIED BY " + 
					"'" + password + "'");
	}
	
	private void use() throws Exception{
		Statement stmt = db_connection.createStatement();
		stmt.
			executeUpdate("USE " + TestDataConstants.MYSQL_DB_NAME);
	}
	
	private void createDB() throws Exception{
		Statement stmt = db_connection.createStatement();
		stmt.
			executeUpdate("CREATE DATABASE IF NOT EXISTS " + TestDataConstants.MYSQL_DB_NAME);
	}
	
	@SuppressWarnings("unused")
	private void showDBs() throws Exception{
		Statement stmt = db_connection.createStatement();
		stmt.
			execute("SHOW DATABASES");
	}

	private void createDBTable() throws Exception{
		Statement stmt = db_connection.createStatement();
		stmt.
		executeUpdate("CREATE TABLE IF NOT EXISTS " + 
				//Constants.MYSQL_DB_NAME + "." + 
				TestDataConstants.MYSQL_DB_TABLE_NAME +
				"(keys_ VARCHAR(254)," +
				"values_ VARCHAR(254), UNIQUE (keys_))");
	}
	
	public void addKeyValuePair(String key, String value) throws Exception{
		Statement stmt = db_connection.createStatement();
		stmt.
			executeUpdate("INSERT into " + 
					TestDataConstants.MYSQL_DB_NAME + "." + TestDataConstants.MYSQL_DB_TABLE_NAME +					
					"(keys_, values_) values " +
					"('" + key + "', " +
					"'" + value + "') " +
					" ON DUPLICATE KEY UPDATE values_='" + value + "'");
	}
	
	public void setConnectionURL(String driver, String url, String username,
			String password) {
		this.driver = driver;
		this.driverPrefixURL = url;
		this.username = username;
		this.password = password;
	}

	public Connection openConnection() {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			System.out.println("Failed to load JDBC/ODBC driver.");
			return null;
		}
		try {
			db_connection = (Connection) DriverManager.getConnection(
					driverPrefixURL + dataSource, username, password);
			System.out.println("Connected.");
			DatabaseMetaData dmd = (DatabaseMetaData) db_connection.getMetaData();
			if (dmd == null) {
				System.out.println("No Database Meta Data");
			} else {
				System.out.println("Database Product Name   : "
						+ dmd.getDatabaseProductName());
				System.out.println("Database Product Version: "
						+ dmd.getDatabaseProductVersion());
				System.out.println("Database Driver Name    : "
						+ dmd.getDriverName());
				System.out.println("Database Driver Version : "
						+ dmd.getDriverVersion());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return db_connection;
	}

	public Connection openConnection(String driver, String url, String user,
			String pass) {
		setConnectionURL(driver, url, user, pass);

		return openConnection();
	}

	public void closeConnection() {
		if (db_connection != null) {
			try {
				db_connection.close();
				System.out.println("Database connection is closed");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error on connection close.");
			}
		}
	}
	
}
