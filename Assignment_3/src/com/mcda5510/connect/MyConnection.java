package com.mcda5510.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
	

		public Connection setupConnection() throws Exception {

			Connection connection = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/Java_assignment2?useUnicode=t"
								+ "rue&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
						"root", "Saibaba1995");
			} catch (Exception e) {
				throw e;
			} finally {

			}
			
			return connection;
		}
}

