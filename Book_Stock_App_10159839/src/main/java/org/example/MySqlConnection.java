package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MySqlConnection {

	Connection mConnection = null;

	public MySqlConnection() {
		try {
			System.out.println("Entering in the makingConnection");
			Class.forName("com.mysql.cj.jdbc.Driver"); // Registering the JDBC Driver for mysql

			mConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/book_stock_db","root", "sunita@12345");
			System.out.println("Connection setup");


		}
		catch(Exception e) {
			System.out.println(e);
		}
	}

	public Connection getmConnection() {
		return mConnection;
	}

	public void setmConnection(Connection mConnection) {
		this.mConnection = mConnection;
	}
}
