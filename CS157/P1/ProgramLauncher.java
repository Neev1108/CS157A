
/**
 * Main entry to program.
 */
import java.util.Scanner;
import java.util.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.sql.SQLException;
import java.io.*;
import java.sql.*;

public class ProgramLauncher {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    
    // JDBC Objects
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;
    
    
    public static void init(String filename) {
        try {
            Properties props = new Properties();                        // Create a new Properties object
            FileInputStream input = new FileInputStream(filename);    // Create a new FileInputStream object using our filename parameter
            props.load(input);                                        // Load the file contents into the Properties object
            driver = props.getProperty("jdbc.driver");                // Load the driver
            url = props.getProperty("jdbc.url");                        // Load the url
            username = props.getProperty("jdbc.username");            // Load the username
            password = props.getProperty("jdbc.password");            // Load the password
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public static void main(String argv[]) {
		System.out.println(":: PROGRAM START");
		
		if (argv.length < 1) {
			System.out.println("Need database properties filename");
		} else {
			BankingSystem.init(argv[0]);
			BankingSystem.testConnection();
            ProgramLauncher.init(argv[0]);
			BatchInputProcessor.run(argv[0]);
		}
        BankingSystem.showCustomers("Customers");
	System.out.println();
	System.out.println();

	boolean done = false;
        while(done != true) {
	Scanner input = new Scanner(System.in);
	System.out.println("Welcome! Please enter a number to start your service.");
	System.out.println("1. New Customer");
	System.out.println("2. Customer Login");
	System.out.println("3. Exit");

	int title = input.nextInt();
        
        
        
        
	if(title == 1) {
		try {
			System.out.println("Please input a name, gender as F or M, age, and pin. Please start by inputing name.");
			String name = input.next();
			System.out.println("Please input your gender as one character, M or F");
			String gender = input.next();
			System.out.println("Now please insert your age.");
			String age = input.next();
			System.out.println("Now please input your pin. It must be greater than 0.");
			String pin = input.next();
	        
	       
	        BankingSystem.newCustomer(name, gender, age, pin);
	        BankingSystem.getID(name);
			
		}
		catch(Exception e)	{
			System.out.println(e.getMessage());
		}
	}
    else if(title == 2) {
        boolean accountCheck = false;
        System.out.println("Please enter your custID and pin number.");
        System.out.println("Please first enter your CustID. Must be a integer over 100.");
        String newID = input.next();
        
        System.out.println("Now enter your pin. Must be over zero.");
        String newPin = input.next();
        
        int ID = Integer.valueOf(newID);
        int pin = Integer.valueOf(newPin);
        
        if (ID != 0 && pin != 0) {
         boolean accessGranted = BankingSystem.authenticateUser(newID, newPin);
           System.out.println(accessGranted);
         if(accessGranted == true) {
            
              while(accountCheck != true) {
                    System.out.println("Welcome customer. Please press a number to interact with your account.");
                    System.out.println("1. Open Account");
                    System.out.println("2. Close Account");
                    System.out.println("3. Deposit");
                    System.out.println("4. Withdraw");
                    System.out.println("5. Transfer");
                    System.out.println("6. Account Summary");
                    System.out.println("7. Exit");
                    
                    int num = input.nextInt();
                    
                    if (num == 1) {
                        
                        
                        System.out.println("Before opening a new account, please enter your ID.");
                        String custID = input.next();
                        System.out.println("Now please enter an account type. C for checking, S for savings.");
                        String type = input.next();
                        System.out.println("Now please enter a balance. This will be your initial deposit into the account.");
                        String balance = input.next();
                        
                        BankingSystem.openAccount(custID, type, balance);
                        continue;
                        
                        
                    }
                    
                    else if(num == 2) {
                        BankingSystem.showAccounts(newID);
                        System.out.println("You have chosen to close your account. Above is your accounts. Please enter the account number of your chosen account.");
                        String accountNum = input.next();
                        BankingSystem.closeAccount(accountNum);
                        continue;
                    }
                    
                    else if (num == 3) {
                        BankingSystem.showAccounts(newID);
                        System.out.println("You have chosen to deposit into your account. Above is your accounts. Please enter the account number of your chosen account.");
                        String accountNum = input.next();
                        System.out.println("Now please enter how much you wish to deposit: ");
                        String deposit = input.next();
                        
                        BankingSystem.deposit(deposit, accountNum);
                        continue;
                        
                        
                    }
                    
                    else if (num == 4) {
                        BankingSystem.showAccounts(newID);
                        System.out.println("You have chosen to withdraw from your account. Above is your accounts. Please enter the account number of your chosen account.");
                        String accountNum = input.next();
                        System.out.println("Now please enter how much you wish to withdraw: ");
                        String withdrawal = input.next();
                        
                        BankingSystem.withdraw(withdrawal, accountNum);
                        continue;
                    }
                    
                    else if (num == 5) {
                        BankingSystem.showAccounts(newID);
                        System.out.println("You have chosen to transfer from your account. Please enter the account number of your account from your list of accounts above.");
                        String sourceAccountNum = input.next();
                        System.out.println("Now please enter the account number of the account you wish to transfer to");
                        String transferAccount = input.next();
                        System.out.println("Now please enter how much you wish to transfer: ");
                        String transfer = input.next();
                        
                        BankingSystem.transfer(sourceAccountNum, transferAccount, transfer);
                        continue;
                        
                    }
                    else if (num == 6) {
                        System.out.println("Following will print your account summary for your customer ID. Please type anything to continue.");
                        String s = input.next();
                        
                        BankingSystem.accountSummary(newID);
                        continue;
                        
                    }
                    else if (num == 7) {
                        System.out.println("The banking system has just been closed. Thanks for using us!");
                        System.exit(1);
                        
                    }
      }
  }
         else {
             System.out.println("Please enter again. ");
             
         }
    
}
        else if (ID == 0 && pin == 0 ) {
            System.out.println("You have entered the admin main menu. Type a number for the following: ");
            System.out.println("1.    Account Summary for a Customer");
            System.out.println("2.    Report A :: Customer Information with Total Balance in Decreasing Order");
            System.out.println("3.    Report B :: Find the Average Total Balance Between Age Groups");
            System.out.println("4.    Exit");
            
            int num = input.nextInt();
            
            if(num == 1) {
                System.out.println("Please input the customer ID to view the account summary.");
                String viewAccountSummary = input.next();
                BankingSystem.accountSummary(viewAccountSummary);
            }
            
            if(num == 2) {
                System.out.println("You have chosen to view the report A summary. Please type anything to continue.");
                String s = input.next();
                BankingSystem.reportA();
                
            }
            
            if(num == 3) {
                System.out.print("You have chosen to find the average total between age groups. Please input a minumum age. ");
                String min = input.next();
                System.out.println("Now please input a max age.");
                String max = input.next();
                BankingSystem.reportB(min, max);
                
            }
        }
        else
            System.out.println("User not authorized. System will exit and start over.");
    }
    else if (title == 3) {
        System.out.println("The banking system has just been closed. Thanks for using us!");
        System.exit(1);
}
}
    }
     
        

}

	


