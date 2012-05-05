package com.epam.mobile.extensions.db; 

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class DBManager {
	String dbName = "xxx";
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

	public void addBroker(int id_broker, String name, String url, 
			String auth, String username, String pass, int id_manager) throws Exception{
		Statement stmt = connection.createStatement();
		stmt.
			executeUpdate("INSERT into " + this.dbName + ".broker (" +
					"id_broker, name, xmlRpcUrl, auth, " +
					"login, pass, id_manager_address) values (" +
					id_broker + ", " +
					"'" + name + "', " +
					"'" + url + "', " +
					"'" + auth + "', " +
					"'" + username + "', " +
					"'" + pass + "', " +
					id_manager + ")");
	}

	public void addBrokerAddress(int id_broker_address, int id_broker, String address) throws Exception{
		Statement stmt = connection.createStatement();
		stmt.
			executeUpdate("INSERT into " + this.dbName + ".broker_address (" +
					"id_broker_address, id_broker, address) values (" +
     				id_broker_address + "," +
					id_broker + "," +
					"'" + address + "')");
	}

	public void addRegionMask(int id_mask, int id_broker, int weight, String region) throws Exception{
		Statement stmt = connection.createStatement();
		stmt.
			executeUpdate("INSERT into " + this.dbName + ".host_address_mask (" +
					"id_host_address_mask, id_broker, weight, mask) values (" +
					id_mask + ", " +
					id_broker + ", " +
					weight + ", " +
					"'" + region + "')");
	}

	public void removeUser(String clientId) throws Exception{
		Statement stmt = connection.createStatement();
		stmt.
			executeUpdate("DELETE from " + this.dbName + ".principal where " +
					"clientId = '" + clientId + "'");
	}
	
	public void removeBrokerAddress(String address) throws Exception{
		Statement stmt = connection.createStatement();
		stmt.
			executeUpdate("DELETE from " + this.dbName + ".broker_address where " +
					"address = '" + address + "'");
	}
	
	public void removeBroker(String name) throws Exception{
		Statement stmt = connection.createStatement();
		stmt.
			executeUpdate("DELETE from " + this.dbName + ".broker where " +
					"name = '" + name + "'");
	}
	
	public void removeRegionMask(String mask) throws Exception{
		Statement stmt = connection.createStatement();
		stmt.
			executeUpdate("DELETE from " + this.dbName + ".host_address_mask where " +
					"mask = '" + mask + "'");
	}

	public void updateRegionMask(int id_mask, String mask) throws Exception{
		Statement stmt = connection.createStatement();
		stmt.
			executeUpdate("UPDATE " + this.dbName + ".host_address_mask " +
					"SET mask = '" + mask + "' " + 
					"WHERE id_host_address_mask=" + id_mask);
	}
	
	/*
	 *   insert into `host_address_mask` (`id_host_address_mask`, `id_broker`, `weight`, `mask`) values (3, 2, 0, '10.1.1.*');
	 */
	
	public void printBroker() throws Exception {
		System.out.println("");
		System.out.println("Broker:");
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * from " + this.dbName + ".broker");
		ArrayList<String> result = readBroker(rs);
		if (result.size() > 0) {
			for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
				String element = (String) iterator.next();
					System.out.print(element + ", ");
			}
		}
		System.out.println("");
	}

	public void printBrokerAddress() throws Exception {
		System.out.println("");
		System.out.println("Broker Address:");
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * from " + this.dbName + ".broker_address");
		ArrayList<String> result = readBrokerAddress(rs);
		if (result.size() > 0) {
			for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
				String element = (String) iterator.next();
					System.out.print(element + ", ");
			}
		}
		System.out.println("");
	}

	public void printRegionMask() throws Exception {
		System.out.println("");
		System.out.println("Region Mask:");
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * from " + this.dbName + ".host_address_mask");
		ArrayList<String> result = readRegionMask(rs);
		if (result.size() > 0) {
			for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
				String element = (String) iterator.next();
					System.out.print(element + ", ");
			}
		}
		System.out.println("");
	}
	
	public void printUser() throws Exception {
		System.out.println("");
		System.out.println("User:");
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * from " + this.dbName + ".principal");
		ArrayList<String> result = readUser(rs);
		if (result.size() > 0) {
			for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
				String element = (String) iterator.next();
					System.out.print(element + ", ");
			}
		}
		System.out.println("");
	}

	public ArrayList<String> readBroker(ResultSet rs) throws SQLException{
		ArrayList<String> data = new ArrayList<String>();			
		
		while (rs.next()) {
			String id = rs.getString("id_broker");
			String name = rs.getString("name");
			String url = rs.getString("xmlRpcUrl");
			data.add(id);
			data.add(name);
			data.add(url);
			//this.write();				
		}
		
		return data;			
	}

	public ArrayList<String> readBrokerAddress(ResultSet rs) throws SQLException{
		ArrayList<String> data = new ArrayList<String>();			
		
		while (rs.next()) {
			String id_address = rs.getString("id_broker_address");
			String id_broker = rs.getString("id_broker");
			String address = rs.getString("address");
			data.add(id_address);
			data.add(id_broker);
			data.add(address);
			//this.write();				
		}
		
		return data;			
	}
	
	public ArrayList<String> readRegionMask(ResultSet rs) throws SQLException{
		ArrayList<String> data = new ArrayList<String>();			
		
		while (rs.next()) {
			String id_mask = rs.getString("id_host_address_mask");
			String id_broker = rs.getString("id_broker");
			String mask = rs.getString("mask");
			data.add(id_mask);
			data.add(id_broker);
			data.add(mask);
			//this.write();				
		}
		
		return data;			
	}

	public ArrayList<String> readUser(ResultSet rs) throws SQLException{
		ArrayList<String> data = new ArrayList<String>();			
		
		while (rs.next()) {
			String id = rs.getString("clientId");
			String pass = rs.getString("encryptedPass");
			String limit = rs.getString("registration_limit");
			data.add(id);
			data.add(pass);
			data.add(limit);
			//this.write();				
		}
		
		return data;			
	}

	public static void main(String[] args) {
		DBManager db = new DBManager("mysql_db_test");
		db.openConnection();
	}
}
