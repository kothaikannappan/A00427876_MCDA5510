import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
//import org.relique.jdbc.csv.CsvDriver;

public class Dirwalker_linux {
	

	static int count = 0;
	static int count1 = 0;

	public void walk(String path, PrintWriter pw) throws FileNotFoundException {
		System.setProperty("java.util.logging.config.file","./logging.properties");

		
		File root = new File(path);
		File[] list = root.listFiles();

		if (list == null)
			return;

		for (File f : list) {
			if (f.isDirectory()) {
				walk(f.getAbsolutePath(), pw);
				System.out.println("Dir:" + f.getAbsoluteFile());
			} else {
				System.out.println("File:" + f.getAbsoluteFile());

				String test = f.getAbsoluteFile().toString();
				if (!test.endsWith(".csv")) {
					continue;
				}

				// This will reference one line at a time
				// String line = null;

//				try {
//					// FileReader reads text files in the default encoding.
//					FileReader fileReader = new FileReader(f.getAbsoluteFile());
//
//					// Always wrap FileReader in BufferedReader.
//					BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//					while ((line = bufferedReader.readLine()) != null) {
//						System.out.println(line);
//
//					}
//					bufferedReader.close();
//
//					// Always close files.
//				} catch (FileNotFoundException ex) {
//					System.out.println("Unable to open file '" + f.getAbsoluteFile() + "'");
//				} catch (IOException ex) {
//					System.out.println("Error reading file '" + f.getAbsoluteFile() + "'");
//					// Or we could just do this:
//					// ex.printStackTrace();
//				}

				
				
				Reader in;
				try {

					
					in = new FileReader(test);
					
					Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
					String delims = "[//]";
					
			        String[] tokens = test.split(delims);
			        
			        String Day = tokens[10].toString();
			        String Month = tokens[9].toString();
			        String Year = tokens[8].toString();
			        String Date = Year + "/" + Month +"/" + Day;
			        //System.out.println("pppppppppppppppppppppppppppppppp );= "+ day);
					//String day = f.getAbsolutePath().toString().substring(File.separator)

					for (CSVRecord record : records) {
						if (record.getRecordNumber() == 1)
							continue;
						try {
							StringBuilder sb = new StringBuilder();
							String firstname = record.get(0);
							String lastname = record.get(1);
							String Number = record.get(2);
							String Street = record.get(3);
							String city = record.get(4);
							String province = record.get(5);
							String postal_code = record.get(6);
							String country = record.get(7);
							String phone_number = record.get(8);
							String email = record.get(9);
							// System.out.println("Data: \n\n\n\n "+ firstname +" \n "+ lastname
							// +"\n\n\n\n\n\n"+ Number+ "\n\n\n\n"+ country +"\n\n\n\n\n");

							if (firstname.length() == 0 || lastname.length() == 0 || Number.length() == 0
									|| Street.length() == 0 || city.length() == 0 || province.length() == 0
									|| postal_code.length() == 0 || country.length() == 0 || phone_number.length() == 0
									|| email.length() == 0) {
//								// SkippedRowscount
								count++;
//
							}

							else {
							sb.append(firstname);
							sb.append(',');
							sb.append(lastname);
							sb.append(',');
							sb.append(Number);
							sb.append(',');

							sb.append(Street);
							sb.append(',');
							sb.append(city);
							sb.append(',');
							sb.append(province);
							sb.append(',');
							sb.append(postal_code);
							sb.append(',');
							sb.append(country);
							sb.append(',');
							sb.append(phone_number);
							sb.append(',');
							sb.append(email);
							sb.append(',');
							sb.append(Date);
							sb.append('\n');
							
							count1++;
							}
							pw.write(sb.toString());

						} catch (Exception e) {
							count++;
							// TODO: handle exception
							Logger.getAnonymousLogger().log(Level.SEVERE, e.getLocalizedMessage().toString());
						}
					}
					

				} catch (IOException e) {
					
					Logger.getAnonymousLogger().log(Level.SEVERE, e.getLocalizedMessage().toString());
				}

				// String File = f.getName();
				// String FileName = File.substring(0, File.length()-4);

				// try {
				// Load the driver.
				// Class.forName("org.relique.jdbc.csv.CsvDriver");

				// String CSVDir = f.getParent();
				// System.out.println("pppppppppppppppppppppppppppppppppppefsfseffesefesgS"+
				// f.getParent());

				// Create a connection. The first command line parameter is
				// the directory containing the .csv files.
				// A single connection is thread-safe for use by several threads.
				// Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + CSVDir);

				// Create a Statement object to execute the query with.
				// A Statement is not thread-safe.
				// Statement stmt = conn.createStatement();

				// Select the ID and NAME columns from sample.csv
				// ResultSet results = stmt.executeQuery("SELECT * FROM "+FileName);

				// Dump out the results to a CSV file with the same format
				// using CsvJdbc helper function
				// boolean append = true;
				// CsvDriver.writeToCsv(results, System.out, append);

				// Clean up
				// conn.close();
				// } catch (Exception e) {
				// e.printStackTrace();
				// }

			}

		}
		

	}

	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();
		Dirwalker_linux fw = new Dirwalker_linux();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("First Name");
			sb.append(',');
			sb.append("Last Name");
			sb.append(',');
			sb.append("Number");
			sb.append(',');

			sb.append("Street");
			sb.append(',');
			sb.append("City");
			sb.append(',');
			sb.append("Province");
			sb.append(',');
			sb.append("Postal_code");
			sb.append(',');
			sb.append("country");
			sb.append(',');
			sb.append("phone_number");
			sb.append(',');
			sb.append("email");
			sb.append(',');
			sb.append("Date");
			sb.append('\n');
			
			PrintWriter pw = new PrintWriter(new File("/Users/mcda/Documents/GitHub/A00427876_MCDA5510/Assignment1/OUTPUT.csv"));
			pw.write(sb.toString());
			fw.walk("/Users/mcda/Documents/GitHub/A00427876_MCDA5510/Assignment1/Sample Data", pw);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getAnonymousLogger().log(Level.SEVERE, e.getLocalizedMessage().toString());
		}
		final long endTime = System.currentTimeMillis();
		System.out.println("\n\n\n\n\n\n Skipped Rows = " + count + " Rows" + "\n\n\n\n Valid Rows = " + count1+ " Rows");
		Logger.getLogger("Main").log(Level.INFO,"Skipped Rows = " + count + " Rows" + "Valid Rows = " + count1+ " Rows");
		System.out.println("\n\n\n\n Total execution time:---- " + (endTime - startTime) + " ms");
		Logger.getLogger("Main").log(Level.INFO,"Total execution time:---- " + (endTime - startTime) +" ms");
	}
}