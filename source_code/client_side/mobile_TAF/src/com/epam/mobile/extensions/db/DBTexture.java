package com.epam.mobile.extensions.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class DBTexture {

	//private static String DRIVER_PREFIX_URL_KEY = "driver_prefix_url";
	
	private Connection db_connection = null;
	
	ArrayList<Statement> statements = new ArrayList<Statement>(); // list of Statements, PreparedStatements
    PreparedStatement psInsert = null;
    PreparedStatement psUpdate = null;
    Statement s = null;
    ResultSet rs = null;
    
    
    // SQL queries generator? is it relevant?
    
    protected void createTable(String tableName){
    	try {
       	 s = db_connection.createStatement();
         statements.add(s);

         // We create a table...
         s.execute("create table " + tableName + "(num int, addr varchar(40))");
         System.out.println("Created table location");
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
    protected void insertRaw(String tableName, int firstArg, String secondArg){
    	try {
            psInsert = db_connection.prepareStatement(
                    "insert into " + tableName + " values (?, ?)");
            statements.add(psInsert);

            psInsert.setInt(1, firstArg);
            psInsert.setString(2, secondArg);
            psInsert.executeUpdate();
            System.out.println("Inserted " + firstArg + " " + secondArg + 
            		"into table " + tableName);

		} catch (Exception e) {
			System.out.println(e.toString());
		}
    }
    
    // "update " + tableName + " set num=?, addr=? where num=?");
    protected void updateRaw(String tableName, 
    		String firstArgName, int firstArgValue, 
    		String secondArgName, String secondArgValue,
    		String whereArgName, int whereArgValue){
    	try {
            psUpdate = db_connection.prepareStatement(
            		"update " + tableName + 
            			" set " + 
            				firstArgName + "=?, " +
            				secondArgName + "=? " +
            			"where " +
            				whereArgName + "=?");
            statements.add(psUpdate);

            psUpdate.setInt(1, firstArgValue);
            psUpdate.setString(2, secondArgValue);
            psUpdate.setInt(3, whereArgValue);
            psUpdate.executeUpdate();
            
            System.out.println("Updated " + whereArgValue + "raw to " +
            		firstArgValue + " " + secondArgValue);
		} catch (Exception e) {
			// TODO: handle exception
		}
    }
    
	public void print(String tableName, String dbName) throws Exception {
		System.out.println("");
		System.out.println(tableName + ": ");
		Statement stmt = db_connection.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * from " + dbName + "." + tableName);
		
		String firstArg = "num";
		String secondArg = "addr";
		ArrayList<String> result = readTableRaws(rs, firstArg, secondArg);
		if (result.size() > 0) {
			for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
				String element = (String) iterator.next();
					System.out.print(element + ", ");
			}
		}
		System.out.println("");
	}
	
	public ArrayList<String> readTableRaws(ResultSet rs, 
			String firstArg, String secondArg) throws SQLException{
		ArrayList<String> data = new ArrayList<String>();			
		
		while (rs.next()) {
			String first_arg_value = String.valueOf(rs.getInt(firstArg));
			String second_arg_value = rs.getString(secondArg);
			data.add(first_arg_value);
			data.add(second_arg_value);
			//this.write();				
		}
		
		return data;			
	}
}
