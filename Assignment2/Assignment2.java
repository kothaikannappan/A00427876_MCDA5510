import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Collection;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Assignment2 {
	
	
	
	
	public static void main(String[] args) throws Exception {
		try {
			
			System.setProperty("java.util.logging.config.file", "logging.properties");
			
		MySQLAccess dao = new MySQLAccess();
		Connection connection = dao.setupConnection();
        
			/*Collection<Transaction> trxns = dao.getAllTransactions(connection);
			
			for (Transaction trxn:trxns ){
				System.out.println(trxn.toString());
			}*/
		

		final long startTime = System.currentTimeMillis();
		
		
           System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			
			while(true)
			{
			System.out.println("press 1 for creating a record: \n press2 for view: \n press 3 for deleting: \n press 4 for updating \n "
					+ "press 5 to exit: ");
			Scanner in= new Scanner(System.in);
			int num = in.nextInt();
            //	dao.createTransaction(Transaction,connection,resultSet);
			
			if(num==1)
			{
			
				dao.createTransaction(connection);
				Logger.getAnonymousLogger().log(Level.INFO," The row is inserted successfully ");
				
			}
			
			if(num==2)
			{
				
				System.out.println("Enter your ID: ");
		    	 int id = in.nextInt();
				Transaction res=dao.getTransaction(id,connection);
				
				System.out.println(res.toString());
				Logger.getAnonymousLogger().log(Level.INFO," The data got displayed ");
			}
			if(num==3)
			{
				do
				{
				System.out.println("Enter your ID: ");
		    	 int id = in.nextInt();
		    	 dao.removeTransaction(id,connection);
		    	 Logger.getAnonymousLogger().log(Level.INFO," The row is deleted successfully ");
		    	 System.out.println("Press (y) to continue deleting rows and any other key to go back to main menu");
		    	 
			    	//option=in.next();
					}while(in.next().equalsIgnoreCase("y"));
			}
			if(num==4)
			{
				System.out.println("Enter your ID: ");
		    	 int id = in.nextInt();
		    	 dao.updateTransaction(id,connection);
		    	 Logger.getAnonymousLogger().log(Level.INFO," The row is updated successfully ");
			}
			
			if(num==5)
			{   final long endTime = System.currentTimeMillis();
				Logger.getAnonymousLogger().log(Level.INFO,"Total execution time: " + (endTime - startTime) + " ms");
		        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
				break;
			}
			
			}
			

			

		}
			 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getAnonymousLogger().log(Level.SEVERE,"Exception found " + e);

		}
		
	}	

}
