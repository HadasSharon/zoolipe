package com.mycompany.api.endpoint.restForMouser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseForRest {
	
	public Connection getConnection()
	{
		Connection connection = null;
		try
		{
		String connectionURL = "jdbc:mysql://localhost:3306/broadleaf";
		
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		connection = DriverManager.getConnection(connectionURL, "root", "root");
	    return connection;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
		e.printStackTrace();
		}
		 return connection;
	}

}
