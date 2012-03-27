package com.epam.mobile.extensions.db; 

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import java.util.Properties;

public class DBManager {
	String dbName = "parallels";
	private static String DRIVER_PREFIX_URL_KEY = "driver_prefix_url";
	
	// default settings
	String driverPrefixURL;
	String driver = "com.mysql.jdbc.Driver";
	String username = "root";
	String password = "12345";
	String dataSource = "";

	private Connection connection = null;
	Statement pstmt = null;
	ResultSet rs = null;

	public DBManager() {
		super();		
		Properties properties = new Properties();
		try {
		    properties.load(new FileInputStream("tests_settings.properties"));		    
		} catch (IOException e) {			
		}
		driverPrefixURL = (String)properties.getProperty(DRIVER_PREFIX_URL_KEY, "jdbc:mysql://localhost:3306/mysql");
	}

	public DBManager(String dbName){
		this();
		this.dbName = dbName;
	}
	
	public DBManager(String dbName, String username, String password){
		this();
		this.dbName = dbName;
		this.username = username;
		this.password = password;
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
			connection = (Connection) DriverManager.getConnection(
					driverPrefixURL + dataSource, username, password);
			System.out.println("Connected.");
			DatabaseMetaData dmd = (DatabaseMetaData) connection.getMetaData();
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

		return connection;
	}

	public Connection openConnection(String driver, String url, String user,
			String pass) {
		setConnectionURL(driver, url, user, pass);

		return openConnection();
	}

	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				System.out.println("Database connection is closed");
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error on connection close.");
			}
		}
	}

	public void addUser(String username, String password, int limit) throws Exception{
		Statement stmt = connection.createStatement();
		stmt.
			executeUpdate("INSERT into " + this.dbName + ".principal (" +
					"clientId, encryptedPass, registration_limit) values (" +
					"'" + username + "', " +
					"'"+ password + "', " +
					limit + ")");
	}
}
