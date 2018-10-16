
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

	public boolean updateTransaction(int id, Connection connection) throws SQLException {
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
				if (id == id_c) {
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
						"Select the option \n 1.New ID \n 2.New Name on Card \n 3.New Card Number \n 4.New Price \n 5.New Quantity \n 6.New Expiry Date");
				System.out.println("What field you want to update (1-7)");
				int user_input = in.nextInt();
				if (user_input == 1) {

					System.out.println("Enter new ID:");
					int user_value = in.nextInt();
					PreparedStatement preparestatement = connection
							.prepareStatement("UPDATE transaction Set ID=? WHERE ID=?");
					preparestatement.setInt(1, user_value);
					preparestatement.setInt(2, id);
					preparestatement.execute();
				} else if (user_input == 2) {

					System.out.println("Enter new Name on card");
					String user_value = in.next();
					if (user_value.matches(".*[;:!@#$%^*+?<>].*")) {
						System.out.println("invalid name");
						return false;
					}
					PreparedStatement preparestatement = connection
							.prepareStatement("UPDATE transaction Set NameOnCard=? WHERE ID=?");
					preparestatement.setString(1, user_value);
					preparestatement.setInt(2, id);
					preparestatement.execute();
				} else if (user_input == 3) {
					String CardType;
					System.out.println("Enter new Card Number");
					String user_value = in.next();
					if (user_value.matches(".*[;:!@#$%^*+?<>].*")) {
						System.out.println("invalid number");
						return false;
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
					PreparedStatement preparestatement = connection
							.prepareStatement("UPDATE transaction Set CardNumber=?, CardType=? WHERE ID=?");
					preparestatement.setString(1, user_value);
					preparestatement.setString(2, CardType);
					preparestatement.setInt(3, id);
					preparestatement.execute();
				} else if (user_input == 4) {
					resultSet1 = statement.executeQuery("select * from transaction where ID=" + id);
					while (resultSet1.next()) {
						System.out.println("Enter new Price");
						float user_value = in.nextFloat();

						float total_price = resultSet1.getFloat(5) * user_value;

						PreparedStatement preparestatement = connection
								.prepareStatement("UPDATE transaction Set UnitPrice=?, TotalPrice=? WHERE ID=?");

						preparestatement.setFloat(1, user_value);
						preparestatement.setFloat(2, total_price);
						preparestatement.setInt(3, id);
						preparestatement.execute();
					}

				} else if (user_input == 5) {
					resultSet1 = statement.executeQuery("select * from transaction where ID=" + id);
					while (resultSet1.next()) {
						System.out.println("Enter new Quantity");
						int user_value = in.nextInt();
						float total_price = resultSet1.getFloat(4) * user_value;
						PreparedStatement preparestatement = connection
								.prepareStatement("UPDATE transaction Set Quantity=?, TotalPrice=? WHERE ID=?");
						preparestatement.setInt(1, user_value);
						preparestatement.setFloat(2, total_price);
						preparestatement.setInt(3, id);
						preparestatement.execute();
					}
				} else if (user_input == 6) {

					System.out.println("ExpiryDate: \n Enter month(mm): \n ");
					int month = in.nextInt();
					if (month > 12 || month < 0) {
						System.out.println("Enter correct month");
						return false;
					}
					System.out.println("Enter year(yyyy): \n");
					int year = in.nextInt();
					if (year < 2015 || year > 2032) {
						System.out.println("Enter correct year");
						return false;
					}

					String expiryDate = month + "/" + year;
					if (expiryDate.matches(".*[;:!@#$%^*+?<>].*")) {
						System.out.println("invalid expiryDate");
						return false;
					}
					// float total_price= resultSet.getInt(5) * user_value;
					PreparedStatement preparestatement = connection
							.prepareStatement("UPDATE transaction Set ExpDate=? WHERE ID=?");
					preparestatement.setString(1, expiryDate);

					preparestatement.setInt(2, id);
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

	public boolean createTransaction(Connection connection) throws SQLException {

		int count = 0;
		String CardType = "";
		Statement statement = null;
		ResultSet resultSet = null;
		Collection<Transaction> results = null;
		// Result set get the result of the SQL query
		try {
			// Statements allow to issue SQL queries to the database
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from transaction");

			String username = System.getProperty("user.name");
			Scanner in = new Scanner(System.in);
			System.out.println("Enter your ID: ");
			int id = in.nextInt();
			while (resultSet.next()) {
				int id_check = resultSet.getInt("id");
				if (id == id_check) {
					count++;

				}
			}
			if (count == 0) {
			

			

					System.out.println("Enter your name on card: ");
					String nameOnCard = in.next();
					if (nameOnCard.matches(".*[;:!@#$%^*+?<>].*")) {
						System.out.println("invalid name");
						return false;
					}
					System.out.println("Card Number: ");
					String cardNumber = in.next();
					if (cardNumber.matches(".*[;:!@#$%^*+?<>].*")) {
						System.out.println("invalid number");
						return false;
					}
					if (cardNumber.matches("^[5][1-5].*") && cardNumber.length() == 16) {
						CardType = "MasterCard";
					} else if (cardNumber.matches("^[4].*") && cardNumber.length() == 16) {
						CardType = "Visa";
					} else if (cardNumber.matches("^[3][4|7].*") && cardNumber.length() == 4) {
						CardType = "American Express";
					} else {
						String CardNumber = "Other";
					}
					System.out.println("UnitPrice: ");
					float price = in.nextFloat();
					System.out.println("Quantity: ");
					int quantity = in.nextInt();
					System.out.println("ExpiryDate: \n Enter month(mm): \n ");
					int month = in.nextInt();
					if (month > 12 || month < 0) {
						System.out.println("Enter correct month");
						return false;
					}
					System.out.println("Enter year(yyyy): \n");
					int year = in.nextInt();
					if (year < 2015 || year > 2032) {
						System.out.println("Enter correct year");
						return false;
					}

					String expiryDate = month + "/" + year;

					if (expiryDate.matches(".*[;:!@#$%^*+?<>].*")) {
						System.out.println("invalid expiryDate");
						return false;
					}
					float totalPrice = price * quantity;

					PreparedStatement preparedStatement = connection.prepareStatement(
							"insert into  transaction values (?, ?, ?, ? , ?, ?,?,now(),'" + username + "',?)");

					// Parameters start with 1
					preparedStatement.setInt(1, id);
					preparedStatement.setString(2, nameOnCard);
					preparedStatement.setString(3, cardNumber);
					preparedStatement.setFloat(4, price);
					preparedStatement.setInt(5, quantity);
					preparedStatement.setFloat(6, totalPrice);
					preparedStatement.setString(7, expiryDate);
					preparedStatement.setString(8, CardType);
					preparedStatement.executeUpdate();
					System.out.println("Created Successfully");
				

				

			} else {
				System.out.println("The id already exist,Press (y) to update the row with this id");
				char option = in.next().charAt(0);
				if (option == 'y') {
					updateTransaction(id, connection);
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
