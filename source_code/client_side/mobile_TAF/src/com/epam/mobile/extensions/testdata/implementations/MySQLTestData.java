package com.epam.mobile.extensions.testdata.implementations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.epam.mobile.extensions.testdata.TestDataConstants;
import com.epam.mobile.extensions.testdata.TestData;

public class MySQLTestData extends TestData{

	private Connection db_connection = null;
	
	public MySQLTestData(Connection conn) {
		IMPLEMENTATION_TYPE = TestDataConstants.MYSQL_TEST_DATA;
		db_connection = conn;
		
		System.out.println(IMPLEMENTATION_TYPE + " is created.");
	}

	public void findValue() {
		String value = ""; 
		try {
			value = getDBValue(this.getKey());			
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		this.setValue(value);
	}

	public String getValue(String key) {
		this.setKey(key);
		
		findValue();
		
		return this.getValue();
	}
	
	public String getDBValue(String key) throws Exception {
		System.out.println("");
		System.out.print("DB raw: ");
		Statement stmt = db_connection.createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * from " + 
						TestDataConstants.MYSQL_DB_NAME + "." + TestDataConstants.MYSQL_DB_TABLE_NAME +
						              " where " +
						"keys_='" + key + "'");
		ArrayList<String> result = readDBRaw(rs);
		if (result.size() > 0) {
			for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
				String element = (String) iterator.next();
					System.out.print(element + " ");
			}
		}
		System.out.println("");
		
		return result.get(result.size() - 1);
	}
	
	public ArrayList<String> readDBRaw(ResultSet rs) throws SQLException{
		ArrayList<String> data = new ArrayList<String>();			
		
		while (rs.next()) {
			String id = rs.getString("keys_");
			String name = rs.getString("values_");
			data.add(id);
			data.add(name);
			//this.write();				
		}
		
		return data;			
	}
}
