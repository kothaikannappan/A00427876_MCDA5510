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
						"jdbc:mysql://localhost:3306/kk_murugappan?useUnicode=t"
								+ "rue&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
						"kk_murugappan", "A00427876");
			} catch (Exception e) {
				throw e;
			} finally {

			}
			
			return connection;
		}
}

