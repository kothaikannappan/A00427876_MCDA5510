package com.mcda5510.dao;

import java.sql.Connection;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import com.mcda5510.connect.MyConnection;
import com.mcda5510.entity.Transaction;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class MySQLAccess {
	Transaction trxn = new Transaction();

	
	
	public Transaction getTransaction(int id) throws Exception {
		Transaction trxn = new Transaction();
		MyConnection dao = new MyConnection();
		Connection connection = dao.setupConnection();
		int count = 0;
		Statement statement = null;
		ResultSet resultSet = null;
		Collection<Transaction> results = null;
		Scanner in = new Scanner(System.in);

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from transaction");

			while (resultSet.next()) {
				int id_c = resultSet.getInt(1);
				if (id == id_c) {
					statement = connection.createStatement();
					trxn.setId(resultSet.getInt("id"));
					trxn.setNameOnCard(resultSet.getString("NameOnCard"));
					trxn.setCardNumber(resultSet.getString("CardNumber"));
					trxn.setPrice(resultSet.getFloat("UnitPrice"));
					trxn.setQuantity(resultSet.getInt("Quantity"));
					trxn.setTotalPrice(resultSet.getFloat("totalprice"));
					trxn.setExpiryDate(resultSet.getString("ExpDate"));
					trxn.setCreatedBy(resultSet.getString("CreatedBy"));
					trxn.setCardNumber(resultSet.getString("CardNumber"));
					trxn.setCardType(resultSet.getString("CardType"));

				}

			}
			if (resultSet != null) {
				resultSet.close();
			}

			if (statement != null) {
				statement.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return trxn;

	}

	public boolean updateTransaction(Transaction trxn) throws Exception {
		MyConnection dao = new MyConnection();
		Connection connection = dao.setupConnection();
		String message="";
		int count = 0;
		Statement statement = null;
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		Scanner in = new Scanner(System.in);

		try {

			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from transaction");

			while (resultSet.next()) {
				int id_c = resultSet.getInt(1);
				if (trxn.getId() == id_c) {
					count++;

				}
			}
			if (count == 0) {
				System.out.println("The id doesnt exist,Press (y) to create a new row with this id");
				return false;
			} 
				else {
				
				if (trxn.getNameOnCard()!="") 
				{
					
					trxn.setNameOnCard(trxn.getNameOnCard());
					PreparedStatement preparestatement = connection
							.prepareStatement("UPDATE transaction Set NameOnCard=? WHERE ID=?");
					preparestatement.setString(1, trxn.getNameOnCard());
					preparestatement.setInt(2, trxn.getId());
					preparestatement.execute();
				} 
				if (trxn.getCardNumber() != "") 
				{
					
						
					String CardType="";
					trxn.setCardNumber(trxn.getCardNumber());
					trxn.setCardType(CardType);
					PreparedStatement preparestatement = connection
							.prepareStatement("UPDATE transaction Set CardNumber=?, CardType=? WHERE ID=?");
					preparestatement.setString(1, trxn.getCardNumber());
					preparestatement.setString(2, trxn.getCardType());
					preparestatement.setInt(3, trxn.getId());
					preparestatement.execute();
				}  
					if (trxn.getPrice() != 0)
					{
						
					
					resultSet1 = statement.executeQuery("select * from transaction where ID=" + trxn.getId());
					while (resultSet1.next()) {
						float total_price = trxn.getPrice()*resultSet1.getInt(5);
                        trxn.setTotalPrice(total_price);
						PreparedStatement preparestatement = connection
								.prepareStatement("UPDATE transaction Set UnitPrice=?, TotalPrice=? WHERE ID=?");

						preparestatement.setFloat(1, trxn.getPrice());
						preparestatement.setFloat(2, trxn.getTotalPrice());
						preparestatement.setInt(3, trxn.getId());
						preparestatement.execute();
					}

				}  
					
					if (trxn.getQuantity() != 0) 
					{
						
					resultSet1 = statement.executeQuery("select * from transaction where ID=" + trxn.getId());
					while (resultSet1.next()) {
						
						
						float total_price = resultSet1.getFloat(4) * trxn.getQuantity();
						trxn.setTotalPrice(total_price);
						PreparedStatement preparestatement = connection
								.prepareStatement("UPDATE transaction Set Quantity=?, TotalPrice=? WHERE ID=?");
						preparestatement.setInt(1, trxn.getQuantity());
						preparestatement.setFloat(2, trxn.getTotalPrice());
						preparestatement.setInt(3, trxn.getId());
						preparestatement.execute();
					}
				} 
					if (trxn.getExpiryDate() !="" ) {
						
					trxn.setExpiryDate(trxn.getExpiryDate());
					// float total_price= resultSet.getInt(5) * user_value;
					PreparedStatement preparestatement = connection
							.prepareStatement("UPDATE transaction Set ExpDate=? WHERE ID=?");
					preparestatement.setString(1, trxn.getExpiryDate());

					preparestatement.setInt(2, trxn.getId());
					preparestatement.execute();
					
					
				}
				}
		}
					 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getAnonymousLogger().log(Level.SEVERE,"Exception found " + e);
		}
		return true;
		
	}

	public boolean removeTransaction(int id) throws Exception {
		try {
			MyConnection dao = new MyConnection();
			Connection connection = dao.setupConnection();
			Scanner in = new Scanner(System.in);
			Statement statement = null;
			ResultSet resultSet = null;
			int count = 0;
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from transaction");

			while (resultSet.next()) {
				int id_check = resultSet.getInt(1);
				if (id == id_check) {
					count++;

				}
			}
			if (count == 0) {

				System.out.println("wrong ID");
                return false;
			} else {

				PreparedStatement preparestatement = connection.prepareStatement("Delete from transaction where ID=?");
				preparestatement.setInt(1, id);
				preparestatement.execute();
				System.out.println("Delete Exectuted Successfully");
				Logger.getAnonymousLogger().log(Level.INFO,"Removed Successfully ");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getAnonymousLogger().log(Level.SEVERE,"Exception found " + e);
		}

		return true;
	}
	
	public boolean createTransaction(Transaction trxn) throws Exception {
		MyConnection dao = new MyConnection();
		Connection connection = dao.setupConnection();
		int count = 0;
		//Boolean ifExist = false;
		String CardType = "";
		Statement statement = null;
		ResultSet resultSet = null;
		Collection<Transaction> results = null;
		try {
		
      
		// Result set get the result of the SQL query
		
			// Statements allow to issue SQL queries to the database
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from transaction");

			String username = System.getProperty("user.name");
			Scanner in = new Scanner(System.in);
			
			int id = trxn.getId();
			while (resultSet.next()) {
				int id_check = resultSet.getInt("id");
				if (id == id_check) {
					count++;
					return false;
					//ifExist = true;

				}
			}
		//	if (ifExist = false)
		if (count == 0) 
			{

					PreparedStatement preparedStatement = connection.prepareStatement(
							"insert into  transaction values (?, ?, ?, ? , ?, ?,?,now(),'" + username + "',?)");

					// Parameters start with 1
					preparedStatement.setInt(1, id);
					preparedStatement.setString(2, trxn.getNameOnCard());
					preparedStatement.setString(3, trxn.getCardNumber());
					preparedStatement.setFloat(4, trxn.getPrice());
					preparedStatement.setInt(5, trxn.getQuantity());
					preparedStatement.setFloat(6, trxn.getTotalPrice());
					preparedStatement.setString(7, trxn.getExpiryDate());
					preparedStatement.setString(8, trxn.getCardType());
					preparedStatement.executeUpdate();
					System.out.println("Created Successfully");
				

				

			} else {
				System.out.println("The id already exist,Press (y) to update the row with this id");
				if (resultSet != null) {
					resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getAnonymousLogger().log(Level.SEVERE,"Exception found " + e);
		} finally {
			statement = null;
			resultSet = null;
		}

		return true;
	}
}
