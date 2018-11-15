
/**
 * Original source code from 
 * http://www.vogella.com/tutorials/MySQLJava/article.html
 * 
**/

import java.sql.Connection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class MySQLAccess {
	Transaction trxn = new Transaction();

	public Connection setupConnection() throws Exception {

		Connection connection = null;
		try {
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

	/*
	 * public Collection<Transaction> getAllTransactions(Connection connection) {
	 * Statement statement = null; ResultSet resultSet = null;
	 * Collection<Transaction> results = null; // Result set get the result of the
	 * SQL query try { // Statements allow to issue SQL queries to the database
	 * statement = connection.createStatement(); resultSet =
	 * statement.executeQuery("select * from transaction"); results =
	 * createTrxns(resultSet);
	 * 
	 * if (resultSet != null) { resultSet.close(); }
	 * 
	 * if (statement != null) { statement.close(); }
	 * 
	 * } catch (SQLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } finally { statement = null; resultSet = null; } return
	 * results;
	 * 
	 * }
	 * 
	 * public Collection<Transaction> createTrxns(ResultSet resultSet) throws
	 * SQLException { Collection<Transaction> results = new
	 * ArrayList<Transaction>();
	 * 
	 * // ResultSet is initially before the first data set while (resultSet.next())
	 * { // It is possible to get the columns via name // also possible to get the
	 * columns via the column number // which starts at 1 // e.g.
	 * resultSet.getSTring(2); trxn.setId(resultSet.getInt("id"));
	 * trxn.setNameOnCard(resultSet.getString("NameOnCard"));
	 * trxn.setCardNumber(resultSet.getString("CardNumber"));
	 * trxn.setPrice(resultSet.getFloat("UnitPrice"));
	 * trxn.setQuantity(resultSet.getInt("Quantity"));
	 * trxn.setTotalPrice(resultSet.getFloat("totalprice"));
	 * trxn.setExpiryDate(resultSet.getString("ExpDate"));
	 * trxn.setCreatedBy(resultSet.getString("CreatedBy"));
	 * 
	 * trxn.setCardNumber(resultSet.getString("CardNumber")); results.add(trxn);
	 * 
	 * // TODO /* String ID = resultSet.getString("ID"); String ExpDate =
	 * resultSet.getString("ExpDate"); String UnitPrice =
	 * resultSet.getString("UnitPrice"); Integer qty = resultSet.getInt("Quantity");
	 * String totalPrice = resultSet.getString("TotalPrice"); Date createdOn =
	 * resultSet.getDate("CreatedOn"); String createdBy =
	 * resultSet.getString("CreatedBy");
	 * 
	 * 
	 * }
	 * 
	 * return results;
	 * 
	 * }
	 */
	public Transaction getTransaction(int id, Connection connection) throws SQLException {
		Transaction trxn = new Transaction();

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

	public boolean updateTransaction(Connection connection, Transaction trxn) throws Exception {
		int count = 0;
		Statement statement = null;
		ResultSet resultSet = null;
		ResultSet resultSet1 = null;
		// Collection<Transaction> results = null;
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
				char option = in.next().charAt(0);
				if (option == 'y') {
					createTransaction(connection);
				}
			} else {
				System.out.println(
						"Select the option \n 1.New Name on Card \n 2.New Card Number \n 3.New Price \n 4.New Quantity \n 5.New Expiry Date");
				System.out.println("What field you want to update (1-7)");
				int user_input = in.nextInt();
				if (user_input == 1) {

					System.out.println("Enter new Name on card");
					String user_value = in.next();
					
					while(user_value.matches(".*[;:!@#$%^*+?<>].*")) {
						System.out.println("invalid name");
						System.out.println("Enter new Name on card");
						user_value = in.next();
					}
					trxn.setNameOnCard(user_value);
					PreparedStatement preparestatement = connection
							.prepareStatement("UPDATE transaction Set NameOnCard=? WHERE ID=?");
					preparestatement.setString(1, trxn.getNameOnCard());
					preparestatement.setInt(2, trxn.getId());
					preparestatement.execute();
				} else if (user_input == 2) {
					String CardType;
					System.out.println("Enter new Card Number");
					String user_value = in.next();
					while (user_value.matches(".*[;:!@#$%^*+?<>].*")) {
						System.out.println("invalid number");
						System.out.println("Enter new Card Number");
					    user_value = in.next();
					}
					if (user_value.matches("^[5][1-5].*") && user_value.length() ==16) {
						CardType = "MasterCard";
					} else if (user_value.matches("^[4].*") && user_value.length() == 16) {
						CardType = "Visa";
					} else if (user_value.matches("^[3][4|7].*") && user_value.length() == 15) {
						CardType = "American Express";
					} else {
						CardType = "Other";
					}
					trxn.setCardNumber(user_value);
					trxn.setCardType(CardType);
					PreparedStatement preparestatement = connection
							.prepareStatement("UPDATE transaction Set CardNumber=?, CardType=? WHERE ID=?");
					preparestatement.setString(1, trxn.getCardNumber());
					preparestatement.setString(2, trxn.getCardType());
					preparestatement.setInt(3, trxn.getId());
					preparestatement.execute();
				} else if (user_input == 3) {
					resultSet1 = statement.executeQuery("select * from transaction where ID=" + trxn.getId());
					while (resultSet1.next()) {
						System.out.println("Enter new Price");
						float user_value = in.nextFloat();
                        trxn.setPrice(user_value);
						float total_price = resultSet1.getFloat(5) * user_value;
                        trxn.setTotalPrice(total_price);
						PreparedStatement preparestatement = connection
								.prepareStatement("UPDATE transaction Set UnitPrice=?, TotalPrice=? WHERE ID=?");

						preparestatement.setFloat(1, trxn.getPrice());
						preparestatement.setFloat(2, trxn.getTotalPrice());
						preparestatement.setInt(3, trxn.getId());
						preparestatement.execute();
					}

				} else if (user_input == 4) {
					resultSet1 = statement.executeQuery("select * from transaction where ID=" + trxn.getId());
					while (resultSet1.next()) {
						System.out.println("Enter new Quantity");
						int user_value = in.nextInt();
						trxn.setQuantity(user_value);
						float total_price = resultSet1.getFloat(4) * user_value;
						trxn.setTotalPrice(total_price);
						PreparedStatement preparestatement = connection
								.prepareStatement("UPDATE transaction Set Quantity=?, TotalPrice=? WHERE ID=?");
						preparestatement.setInt(1, trxn.getQuantity());
						preparestatement.setFloat(2, trxn.getTotalPrice());
						preparestatement.setInt(3, trxn.getId());
						preparestatement.execute();
					}
				} else if (user_input == 5) {

					System.out.println("ExpiryDate: \n Enter month(mm): \n ");
					int month = in.nextInt();
					while (month > 12 || month < 0) {
						System.out.println("Enter correct month");
						System.out.println("ExpiryDate: \n Enter month(mm): \n ");
						month = in.nextInt();
						
					}
					System.out.println("Enter year(yyyy): \n");
					int year = in.nextInt();
					while (year < 2015 || year > 2032) {
						System.out.println("Enter correct year");
						System.out.println("Enter year(yyyy): \n");
						year = in.nextInt();
						
					}

					String expiryDate = month + "/" + year;
					if (expiryDate.matches(".*[;:!@#$%^*+?<>].*")) {
						System.out.println("invalid expiryDate");
						return false;
					}
					trxn.setExpiryDate(expiryDate);
					// float total_price= resultSet.getInt(5) * user_value;
					PreparedStatement preparestatement = connection
							.prepareStatement("UPDATE transaction Set ExpDate=? WHERE ID=?");
					preparestatement.setString(1, trxn.getExpiryDate());

					preparestatement.setInt(2, trxn.getId());
					preparestatement.execute();
					System.out.println("Updated Successfully");
				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getAnonymousLogger().log(Level.SEVERE,"Exception found " + e);
		}
		return true;
	}

	public boolean removeTransaction(int id, Connection connection) throws SQLException {
		try {
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

			} else {

				PreparedStatement preparestatement = connection.prepareStatement("Delete from transaction where ID=?");
				preparestatement.setInt(1, id);
				preparestatement.execute();
				System.out.println("Delete Exectuted Successfully");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getAnonymousLogger().log(Level.SEVERE,"Exception found " + e);
		}

		return true;
	}
	public Transaction CreateObject(Transaction trxn) throws Exception {
		//Transaction trxn = new Transaction();
		

			try {
				Scanner in = new Scanner(System.in);

				System.out.println("ID: ");
				int id = in.nextInt();
				trxn.setId(id);

				System.out.println("Name on card: ");
				String nameOnCard = in.next();
				if (nameOnCard.matches(".*[;:!@#$%^*+?<>].*")) {
					System.out.println("invalid name");
					//return false;
				}
				trxn.setNameOnCard(nameOnCard);
				System.out.println("Card Number: ");
				String cardNumber = in.next();
				if (cardNumber.matches(".*[;:!@#$%^*+?<>].*")) {
					System.out.println("invalid number");
					//return false;
				}
				trxn.setCardNumber(cardNumber);
				String CardType = "";
				if (cardNumber.matches("^[5][1-5].*") && cardNumber.length() == 16) {
					CardType = "MasterCard";
					//trxn.setCardType(CardType);
				} else if (cardNumber.matches("^[4].*") && cardNumber.length() == 16) {
					CardType = "Visa";
					//trxn.setCardType(CardType);
				} else if (cardNumber.matches("^[3][4|7].*") && cardNumber.length() == 15) {
					CardType = "American Express";
					//trxn.setCardType(CardType);
				} else {
					CardType = "Other";
					//trxn.setCardType(CardType);
				}
				trxn.setCardType(CardType);
				System.out.println("\nUnit Price \n");
				float price = in.nextFloat();
				trxn.setPrice(price);
				System.out.println("Quantity: \n");
				int quantity = in.nextInt();
				trxn.setQuantity(quantity);
				System.out.println("Month [MM]: \n");
				int month = in.nextInt();
				if (month > 12 || month < 0) {
					System.out.println("Incorrect Entry");
				}
				System.out.println("Year [YYYY]: \n");
				int year = in.nextInt();
				if (year < 2015 || year > 2032) {
					System.out.println("Incorrect Entry");
				}
				String expiryDate = month + "/" + year;
				trxn.setExpiryDate(expiryDate);
				if (expiryDate.matches(".*[;:!@#$%^*+?<>].*")) {
					System.out.println("\nInvalid Entry \n");

				}

				float totalPrice = price * quantity;
				trxn.setTotalPrice(totalPrice);
				String username = System.getProperty("user.name");
				trxn.setCreatedBy(username);

				return trxn;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return trxn;
	}
	public boolean createTransaction(Connection connection) throws Exception {
		
		int count = 0;
		String CardType = "";
		Statement statement = null;
		ResultSet resultSet = null;
		Collection<Transaction> results = null;
		try {
		Transaction trxn = new Transaction();
		
		CreateObject(trxn);
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

				}
			}
			if (count == 0) {
			

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
				char option = in.next().charAt(0);
				if (option == 'y') {
					updateTransaction( connection,trxn);
				}
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
