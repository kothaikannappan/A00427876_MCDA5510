package com.mcda5510.web.service;

import java.sql.Connection;

import com.mcda5510.connect.MyConnection;
import com.mcda5510.dao.MySQLAccess;
import com.mcda5510.entity.Transaction;

public class CreateObject {
	MyConnection dao = new MyConnection();
	
	MySQLAccess ms = new MySQLAccess();
	public boolean createObject(String ID, String Name, String CardNumber,String price, String quantity, String month, String year) throws Exception {
		
		Connection connection = dao.setupConnection();
		Transaction trxn = new Transaction();
		float Price;
		int Quantity;
		

			try {
				if(ID != null)
			       {
			    	   int Id = Integer.parseInt(ID);
	               trxn.setId(Id);
			       }
			       else
			       {
			    	   return false;
			       }
				if(Name != null)
                {
				
				if (Name.matches(".*[;:!@#$%^*+?<>].*")) {
					System.out.println("invalid name");
					return false;
				}
				trxn.setNameOnCard(Name);
				System.out.println("Card Number: ");
                }
				else
				{
					return false;
				}
				if(CardNumber!=null)
				{
				if (CardNumber.matches(".*[;:!@#$%^*+?<>].*")) {
					System.out.println("invalid number");
					return false;
				}
				trxn.setCardNumber(CardNumber);
				String CardType = "";
				if (CardNumber.matches("^[5][1-5].*") && CardNumber.length() == 16) {
					CardType = "MasterCard";
					//trxn.setCardType(CardType);
				} else if (CardNumber.matches("^[4].*") && CardNumber.length() == 16) {
					CardType = "Visa";
					//trxn.setCardType(CardType);
				} else if (CardNumber.matches("^[3][4|7].*") && CardNumber.length() == 15) {
					CardType = "American Express";
					//trxn.setCardType(CardType);
				} else {
					CardType = "Other";
					//trxn.setCardType(CardType);
				}
				trxn.setCardType(CardType);
				}
				else
				{
					return false;
				}
				
				
				if( price != null)
                {
                	 Price =Integer.parseInt(price);
				trxn.setPrice(Price);
                }
				else
				{
					return false;
				}
				if(quantity != null)
                {
                	 Quantity = Integer.parseInt(quantity);
				System.out.println("Quantity: \n");
				trxn.setQuantity(Quantity);
                }
                else
                {
                	 return false;
                }
				if(month !=null || year !=null)
                {
				System.out.println("Month [MM]: \n");
				int Month = Integer.parseInt(month);
				if (Month > 12 || Month < 0) {
					System.out.println("Incorrect Entry");
				}
				int Year = Integer.parseInt(year);
             
               
				System.out.println("Year [YYYY]: \n");
				
				if (Year < 2015 || Year > 2032) {
					System.out.println("Incorrect Entry");
				}
				String expiryDate = Month + "/" + Year;
				trxn.setExpiryDate(expiryDate);
				if (expiryDate.matches(".*[;:!@#$%^*+?<>].*")) {
					System.out.println("\nInvalid Entry \n");
                    return false;
				}
                }
                else
                {
                	
                	return false;
                }

				float totalPrice = Price * Quantity;
				trxn.setTotalPrice(totalPrice);
				String username = System.getProperty("user.name");
				trxn.setCreatedBy(username);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean message = ms.createTransaction(trxn);
			return message;
			
	}
	public boolean removeObject(int Id) throws Exception
	{
	boolean message;
	message=ms.removeTransaction(Id);
	return message;
	}
	
	public String getObject(int Id) throws Exception
	{
	
	Transaction t = ms.getTransaction(Id);
	System.out.println(t.toString());
	return t.toString();
	}	
	
	public boolean updateObject(String ID, String Name, String CardNumber,String price, String quantity, String month, String year)throws Exception {

		Connection connection = dao.setupConnection();
		Transaction trxn = new Transaction();
		float Price;
		int Quantity;
		       if(ID != null)
		       {
		    	   int Id = Integer.parseInt(ID);
               trxn.setId(Id);
		       }
		       else
		       {
		    	   return false;
		       }
		       
               try {
                if(Name != null)
                {
				System.out.println("Name on card: ");
				
				if (Name.matches(".*[;:!@#$%^*+?<>].*")) {
					System.out.println("invalid name");
					return false;
				}
				trxn.setNameOnCard(Name);
				System.out.println("Card Number: ");
                }
                else
                {
                	Name="";
                	trxn.setNameOnCard(Name);
                }
                if(CardNumber != null)
                {
                	
     
				if (CardNumber.matches(".*[;:!@#$%^*+?<>].*")) {
					System.out.println("invalid number");
					return false;
				}
				trxn.setCardNumber(CardNumber);
				String CardType = "";
				if (CardNumber.matches("^[5][1-5].*") && CardNumber.length() == 16) {
					CardType = "MasterCard";
					//trxn.setCardType(CardType);
				} else if (CardNumber.matches("^[4].*") && CardNumber.length() == 16) {
					CardType = "Visa";
					//trxn.setCardType(CardType);
				} else if (CardNumber.matches("^[3][4|7].*") && CardNumber.length() == 15) {
					CardType = "American Express";
					//trxn.setCardType(CardType);
				} else {
					CardType = "Other";
					//trxn.setCardType(CardType);
				}
				trxn.setCardType(CardType);
			}
			else
			{
			 	CardNumber="";
			 	trxn.setCardNumber(CardNumber);
			}
                if( price != null)
                {
                	 Price =Integer.parseInt(price);
          
				System.out.println("\nUnit Price \n");
				trxn.setPrice(Price);
                }
                else
                {
                	 Price = 0;
                	trxn.setPrice(Price);
                }
                if(quantity != null)
                {
                	 Quantity = Integer.parseInt(quantity);
				System.out.println("Quantity: \n");
				trxn.setQuantity(Quantity);
                }
                else
                {
                	 Quantity = 0;
                	trxn.setQuantity(Quantity);
                }
                if(month !=null || year !=null)
                {
				System.out.println("Month [MM]: \n");
				int Month = Integer.parseInt(month);
				if (Month > 12 || Month < 0) {
					System.out.println("Incorrect Entry");
				}
				int Year = Integer.parseInt(year);
             
               
				System.out.println("Year [YYYY]: \n");
				
				if (Year < 2015 || Year > 2032) {
					System.out.println("Incorrect Entry");
				}
				String expiryDate = Month + "/" + Year;
				trxn.setExpiryDate(expiryDate);
				if (expiryDate.matches(".*[;:!@#$%^*+?<>].*")) {
					System.out.println("\nInvalid Entry \n");
                    return false;
				}
                }
                else
                {
                	
                	String expiryDate = "";
    				trxn.setExpiryDate(expiryDate);
                }
            
				String username = System.getProperty("user.name");
				trxn.setCreatedBy(username);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			boolean message = ms.updateTransaction(trxn);
			return message;
	}
	
}
