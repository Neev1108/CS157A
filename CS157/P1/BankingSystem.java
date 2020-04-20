import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.sql.SQLException;
import java.io.*;
import java.sql.*;
/**
 * Manage connection to database and perform SQL statements.
 */
public class BankingSystem {
	// Connection properties
	private static String driver;
	private static String url;
	private static String username;
	private static String password;
	
	// JDBC Objects
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;

	/**
	 * Initialize database connection given properties file.
	 * @param filename name of properties file
	 */
	public static void init(String filename) {
		try {
			Properties props = new Properties();						// Create a new Properties object
			FileInputStream input = new FileInputStream(filename);	// Create a new FileInputStream object using our filename parameter
			props.load(input);										// Load the file contents into the Properties object
			driver = props.getProperty("jdbc.driver");				// Load the driver
			url = props.getProperty("jdbc.url");						// Load the url
			username = props.getProperty("jdbc.username");			// Load the username
			password = props.getProperty("jdbc.password");			// Load the password
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test database connection.
	 */
	public static void testConnection() {
		System.out.println(":: TEST - CONNECTING TO DATABASE");
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			con.close();
			System.out.println(":: TEST - SUCCESSFULLY CONNECTED TO DATABASE");
			} catch (Exception e) {
				System.out.println(":: TEST - FAILED CONNECTED TO DATABASE");
				e.printStackTrace();
			}
	  }

	/**
	 * Create a new customer.
	 * @param name customer name
	 * @param gender customer gender
	 * @param age customer age
	 * @param pin customer pin
	 */
	public static void newCustomer(String name, String gender, String age, String pin) 
	{
         System.out.println(":: CREATE NEW CUSTOMER - RUNNING");
		try {
				
				 String query = "INSERT into P1.Customer(Name, Gender, Age, Pin)" +  " values (' "+ name+ "','" + gender + "',"+ age + ","+ pin +")";
				 Class.forName(driver);                                                                  //load the driver
				 Connection con = DriverManager.getConnection(url, username, password); 
				 Statement stmt = con.createStatement();
				 stmt.executeUpdate(query);
				 
				 String getID ="SELECT ID from P1.Customer WHERE name = '"+ name + "'";
int ID = 100;
ResultSet rs = stmt.executeQuery(getID);
				 
								 
			        System.out.println(":: CREATE NEW CUSTOMER - SUCCESS");
                    System.out.println();
			        
				 
			 }
			 catch (Exception e) {
		            System.out.println(e.getMessage());
		            System.out.println(":: FAILED TO CREATE NEW CUSTOMER");
		        }
	}

	/**
	 * Open a new account.
	 * @param id customer id
	 * @param type type of account
	 * @param amount initial deposit amount
	 */
	public static void openAccount(String id, String type, String amount) 
	{
         System.out.println(":: OPEN ACCOUNT - RUNNING");
		try {
				
				String query = "INSERT into P1.Account (ID, Balance, Type, Status) " + "values (" +id + "," + amount + ",'" + type +"','A')";
				 Class.forName(driver);                                                                  //load the driver
				 Connection con = DriverManager.getConnection(url, username, password); 
				 Statement stmt = con.createStatement();
				 stmt.executeUpdate(query);
				 String num = "SELECT Number from P1.Account WHERE ID ='" + id + "'";
				 ResultSet rs = stmt.executeQuery(num);
				int accountNum = 1000;
				 while(rs.next()) {
						accountNum = rs.getInt("Number");
					}
				 System.out.println(":: OPEN ACCOUNT - SUCCESS");
				 
				 System.out.println("Your account number is: " + accountNum);
                System.out.println();
				 
			 }
			 catch (Exception e) {
		            System.out.println(e.getMessage());
		            System.out.println(":: FAILED TO OPEN A NEW ACCOUNT");
			 }   
			
	}

	/**
	 * Close an account.
	 * @param accNum account number
	 */
	public static void closeAccount(String accNum) 
	{
        System.out.println(":: CLOSE ACCOUNT - RUNNING");
        try {
            String query = "UPDATE P1.Account SET Status = 'I', Balance = 0 WHERE Number = " + accNum;
            
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            System.out.println(":: CLOSE ACCOUNT - SUCCESS");
            System.out.println();
            
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
        }
	}

	/**
	 * Deposit into an account.
	 * @param accNum account number
	 * @param amount deposit amount
	 */
	public static void deposit(String accNum, String amount) 
	{
        System.out.println(":: DEPOSIT ACCOUNT - RUNNING");
		try {
				 String query = "UPDATE P1.Account SET Balance = Balance + " +  amount + " WHERE Number = " + accNum;
				 
				 Class.forName(driver);     
				 Connection con = DriverManager.getConnection(url, username, password); 
				 Statement stmt = con.createStatement();
				 stmt.executeUpdate(query);
				 System.out.println(":: DEPOSIT ACCOUNT - SUCCESS");
				System.out.println();
	 }
			 catch (Exception e) {
		            System.out.println(e.getMessage());
		            System.out.println();
		        }
	}

	/**
	 * Withdraw from an account.
	 * @param accNum account number
	 * @param amount withdraw amount
	 */
	public static void withdraw(String accNum, String amount) 
	{
        System.out.println(":: WITHDRAW - RUNNING");
		try {
				 String query = "UPDATE P1.Account SET Balance = Balance - " +  amount + " WHERE Number = " + amount;
				 
				 Class.forName(driver);     
				 Connection con = DriverManager.getConnection(url, username, password); 
				 Statement stmt = con.createStatement();
				 stmt.executeUpdate(query);
				 System.out.println(":: WITHDRAW - SUCCESS");
				System.out.println();
	 }
			 catch (Exception e) {
		            System.out.println(e.getMessage());
		        }
	}

	/**
	 * Transfer amount from source account to destination account. 
	 * @param srcAccNum source account number
	 * @param destAccNum destination account number
	 * @param amount transfer amount
	 */
	public static void transfer(String srcAccNum, String destAccNum, String amount) 
	{
        System.out.println(":: TRANSFER - RUNNING");
		try {
				 String query = "UPDATE P1.Account SET Balance = Balance - " +  amount + " WHERE Number = " + srcAccNum;
				 String query2 = "UPDATE P1.Account SET Balance = Balance + " +  amount + " WHERE Number = " + destAccNum;
				 
				 Class.forName(driver);     
				 Connection con = DriverManager.getConnection(url, username, password); 
				 Statement stmt = con.createStatement();
				 stmt.executeUpdate(query);
				 stmt.executeUpdate(query2);
				 System.out.println(":: TRANSFER - SUCCESS");
				System.out.println();
	 }
			 catch (Exception e) {
		            System.out.println(e.getMessage());
		        }
	}

	/**
	 * Display account summary.
	 * @param cusID customer ID
	 */
	public static void accountSummary(String cusID) 
	{
System.out.println("Following is your account summary for the customer ID" + cusID);
 System.out.println(":: ACCOUNT SUMMARY - RUNNING");
		try {
				 String query = "SELECT sum(Balance) AS TOTAL FROM P1.Account WHERE ID = " + cusID;
				 String query2 = "SELECT Number, Balance from P1.Account WHERE ID ="+ cusID;
				 Class.forName(driver);     
				 Connection con = DriverManager.getConnection(url, username, password); 
				 Statement stmt = con.createStatement();
				 
				 int total = 0;
				 ResultSet rs = stmt.executeQuery(query);
				 while(rs.next()) {
					 total = rs.getInt("TOTAL");
				 }
				 
				 ResultSet rs2 = stmt.executeQuery(query2);
				 int accountNum = 0;
				 int balance = 0;
				 while(rs2.next()) {
						accountNum = rs2.getInt("Number");
						balance = rs2.getInt("Balance");
						System.out.println(accountNum + ",\t" + balance); 
					}
				 
				 System.out.println();
				 System.out.println("Your total balance from all your accounts is " + total);
				 System.out.println(":: ACCOUNT SUMMARY - SUCCESS");
            System.out.println();
				
	 }
			 catch (Exception e) {
		            System.out.println(e.getMessage());
		        }

	}

	/**
	 * Display Report A - Customer Information with Total Balance in Decreasing Order.
	 */
	public static void reportA() 
	{
	System.out.println(":: REPORT A - RUNNING");
			try {
				
				 String query = "SELECT C.ID, Name, Age, Gender, sum(balance) as \"TOTAL BALANCE\" FROM P1.Customer as C, P1.Account as A where C.ID = A.ID "
                + "GROUP by C.ID, Name, Age, Gender order by \"TOTAL BALANCE\" DESC ";
				 Class.forName(driver);     
				 Connection con = DriverManager.getConnection(url, username, password); 
				 Statement stmt = con.createStatement();
				 
				 ResultSet rs = stmt.executeQuery(query);
				 int custID = 0;
				 int age = 0;
				 int total = 0;
				 String name;
				 String gender;
				 while(rs.next()) {
					 custID = rs.getInt("ID");
					 name = rs.getString("Name");
					 age = rs.getInt("Age");
					 gender = rs.getString("Gender");
					 total = rs.getInt("TOTAL BALANCE");
					 
					 System.out.println(custID + ",\t" + name + ",\t" + age + ",\t" + gender + ",\t" );
					 
					
					
					 
					 
				 }
				 System.out.println(":: REPORT A - SUCCESS");
				 System.out.println();
				 
				
	 }
			 catch (Exception e) {
		            System.out.println(e.getMessage());
		        }
	
	}

	/**
	 * Display Report B - Customer Information with Total Balance in Decreasing Order.
	 * @param min minimum age
	 * @param max maximum age
	 */
	public static void reportB(String min, String max) 
	{
        System.out.println(":: REPORT B - RUNNING");
		try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);
            Statement stmt = con.createStatement();
				String query = "SELECT AVG(Balance) as average FROM reportB WHERE age>" + min + " AND age<"+ max;
				 
				 ResultSet rs = stmt.executeQuery(query);
				 int average = 0;
				 if(rs.next()) {
					 average = rs.getInt("average");
						
					}
				
					
				 
				 System.out.println("The Average balance in the indicated range is " + average);
				 System.out.println(":: REPORT B - SUCCESS");
            System.out.println();
			 }
			
			 catch (Exception e) {
		            System.out.println(e.getMessage());
		        }
	}

public static boolean authenticateUser(String ID, String pin){
    try{
        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, username, password);
        Statement stmt = con.createStatement();
        String query = "SELECT PIN FROM P1.CUSTOMER WHERE ID = " + ID;
        rs = stmt.executeQuery(query);
        if (rs.next()) {
            int result = rs.getInt(1);
            if(Integer.valueOf(pin) == result) return true;
            else {
                System.out.println("Invalid ID/Pin");
                return false;
            }
        }
    }
    catch (Exception e) {
        System.out.println("error");
    }
    return false;
}
    
    

    
public static void showCustomers(String dummy) {
			try {
				System.out.println("\n" + "\n" );
				System.out.println("Following is the Customer table: ");
				String query = "SELECT * FROM P1.Customer";
				 Class.forName(driver);     
				 Connection con = DriverManager.getConnection(url, username, password); 
				 Statement stmt = con.createStatement();
				 
				 ResultSet rs = stmt.executeQuery(query);
				 int custID = 0;
				 int age = 0;
				 int pin = 0;
				 String name;
				 String gender;
				 while(rs.next()) {
					 custID = rs.getInt("ID");
					 name = rs.getString("Name");
					 age = rs.getInt("Age");
					 gender = rs.getString("Gender");
					 pin = rs.getInt("Pin");
					 
					 System.out.println(custID + ",\t" + name + ",\t" + gender + ",\t" + age + ",\t" + pin);
				 }
			System.out.println();
				 
				
				 
				
			}
			catch (Exception e) {
	            System.out.println(e.getMessage());

	        }
		}
		
		public static void showAccounts(String ID) {
			 System.out.println("\n" + "\n" );
			 System.out.println("Following is the Account table: ");
			 
			 try {
			 String query = "SELECT Number,Balance,Type,Status FROM P1.Account WHERE ID = " + ID;
			 Class.forName(driver);     
			 Connection con = DriverManager.getConnection(url, username, password); 
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery(query);
			 int Number = 0;
			 int Balance = 0;
             System.out.println("NUMBER\tID\t\tBALANCE\tTYPE\tSTATUS");
			 String type;
			 String status;
			 while(rs.next()) {
				 Number = rs.getInt("Number");
				 Balance = rs.getInt("Balance");
				 type = rs.getString("Type");
				 status = rs.getString("Status");
				 
				 System.out.println(Number + ",\t" + ID + ",\t" + Balance + ",\t" + type + ",\t" + status);
                 
		}
                 System.out.println();
			 }
			 
			 catch (Exception e) {
		            System.out.println(e.getMessage());

		        }
		}
    

public static void getID(String name) {
			try {
                String query ="SELECT ID from P1.Customer WHERE name ='"+ name + "'";
				 Class.forName(driver);     
				 Connection con = DriverManager.getConnection(url, username, password); 
				 Statement stmt = con.createStatement();
			    ResultSet rs = stmt.executeQuery(query);
                int ID = 100;
			    if(rs.next()) {
					ID = rs.getInt("ID");
                    System.out.println("Your Customer ID is: " + ID);
		}
            
			System.out.println();
			}
			
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}


}
