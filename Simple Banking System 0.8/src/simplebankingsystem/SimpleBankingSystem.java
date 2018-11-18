package simplebankingsystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public final class SimpleBankingSystem {

	static List<Account> account = new ArrayList<Account>();
	static List<Customer> customers = new ArrayList<Customer>();

	protected static int accountNumber = 1000;  // starting account number (auto-count)
	protected static int customerID = 1;
	protected final static String ACCOUNTFILE = "src/doc/AccountObjects.dat";
	protected final static String CUSTOMERFILE = "src/doc/CustomerObjects.dat";

	public static void createAccounts() {
		// test customers, accounts and transactions //
		customers.add(new Customer("Fred Flintstone", "123 test St.", customerID++));
		customers.add(new Customer("Sam Childers","1345 North St.", customerID++));
		customers.add(new Customer("Nancy Drew","5677 Emerson Ave", customerID++));
		customers.add(new Customer("J Pletch", "234 ask st.", customerID++));
		account.add(new Gold(accountNumber++, 50.0 , customers.get(0), 0.05));
		account.add(new Gold(accountNumber++, 70.0 , customers.get(1), 0.05));
		account.add(new Gold(accountNumber++, 560.0 , customers.get(0), 0.05));
		account.add(new Gold(accountNumber++, 4540.0 , customers.get(2), 0.05));
		account.add(new Regular(accountNumber++, 4540.0 , customers.get(3), 0.06, 0.0));
		account.add(new Regular(accountNumber++, 0.0 , customers.get(2), 0.06, 0.0));
		account.add(new Regular(accountNumber++, 4550.0 , customers.get(0), 0.06, 0.0));
		account.add(new Regular(accountNumber++, 34340.0 , customers.get(3), 0.06, 0.0));
		account.add(new Regular(accountNumber++, 5450.0 , customers.get(1), 0.06, 0.0));
		account.add(new Regular(accountNumber++, 3430.0 , customers.get(3), 0.06, 0.0));
		account.add(new Regular(accountNumber++, 2320.0 , customers.get(2), 0.06, 0.0));
		account.add(new Regular(accountNumber++, 1210.0 , customers.get(1), 0.06, 0.0));
		account.add(new Checking(accountNumber++, 0.0 , customers.get(0), 3.0));
		account.add(new Checking(accountNumber++, 5650.0 , customers.get(1), 3.0));
		account.add(new Checking(accountNumber++, 6550.0 , customers.get(2), 3.0));
		account.add(new Checking(accountNumber++, 4540.0 , customers.get(3), 3.0));
		account.get(2).setTransactionCount(4);
		account.get(3).setTransactionCount(10);
		account.get(14).setTransactionCount(11);
		account.get(13).setTransactionCount(45);
		account.get(12).setTransactionCount(3);
		account.get(11).setTransactionCount(15);
		account.get(4).setTransactionCount(4);
		account.get(5).setTransactionCount(10);
		account.get(6).setTransactionCount(11);
		account.get(8).setTransactionCount(45);
		account.get(9).setTransactionCount(34);
		account.get(10).setTransactionCount(15);
	}

	public int byAccountNumber(String accountNumber) {  
		int i = 0;
		boolean match = false;
		while(!match) {
			for(i = 0; i < ((List<Account>) account).size(); i++) {
				if(account.get(i).accountNumber == Integer.parseInt(accountNumber)) {
					match = true;
					break;
				}
			}
		}
		return i; 
	}

	public int byCustomerIDNumber(String customerNumber) {  
		int i = 0;
		boolean match = false;
		while(!match) {
			for(i = 0; i < ((List<Customer>) customers).size(); i++) {
				if(customers.get(i).customerID == Integer.parseInt(customerNumber)) {
					match = true;
					break;
				}
			}
		}
		System.out.println("Customer array element " + i);
		return i; 
	}

	// deposits funds in account input in by the user
	public static void depositFunds(int element, String funds) {
		double number = Double.parseDouble(funds);
		account.get(element).depositFunds(number);
		account.get(element).setTransactionCount(1);
	}


	public static void withdrawFunds(int element, String funds) {
		double number = Double.parseDouble(funds);
		account.get(element).depositFunds(-number);
		account.get(element).setTransactionCount(1);
	}

	public static boolean checkForDouble(String numberToCheck) {        
		return numberToCheck.matches("\\d+(\\.\\d+)?");  // if matches double return true
	}

	public static boolean checkForInteger(String numberToCheck) {
		return numberToCheck.matches("-?\\d+");
	}

	public static int getNewAccountNumber() { // finds last account number and adds 1
		if (account.size() == 0) {
			return accountNumber;
		} else {
		return account.get(account.size() -1).accountNumber + 1;
		}
	}
	
	public static int getNewCustomerNumber() { // finds last account number and adds 1	
		if (customers.size() == 0) {
			return customerID;
		} else {
		return customers.get(customers.size() -1).getCustomerID() + 1;
		}
	}

	//Returns sum balance of all accounts
	public static double getTotalBalance() {
		double totalSum = 0;
		for (int index = 0; index < account.size(); index++) {
			Account current = account.get(index);
			totalSum = totalSum + current.getBalance();

		}
		return totalSum;

	}
	
	//Returns average balance of accounts
	public static double getAverageBalance() {
		double averageBalance  = 0;
		double totalSum  = 0;
		for (int index = 0; index < account.size(); index++) {
			Account current = account.get(index);
			totalSum = totalSum + current.getBalance();
			averageBalance = totalSum / account.size();
		}
		return averageBalance;
	}
	
	//Returns balance of largest account
	public static double getHighestAccountBalance() {
		double max = 0;
		for (int index = 0; index < account.size(); index++) {
			Account current = account.get(index);

			if (current.getBalance() > max)
				max = current.getBalance();

		}
		return max;
	}
	
	// Returns number of zero-balance accounts
	public static int getZeroBalanceAccounts() {
		int numAccounts = 0;
		for (int index = 0; index < account.size(); index++) {
			Account zeroBal = account.get(index);
			if (zeroBal.getBalance() == 0) {
				numAccounts++;
			}
		}
		return numAccounts;
	}
	
	public static int getNumberOfGoldAccounts() {
		int goldCount = 0;
		for(Account account : account) {
			if(account instanceof Gold) {
				goldCount++;
			}
		}
		return goldCount;
	}

	public static int getNumberOfCheckingAccounts() {
		int checkingCount = 0;
		for(Account account : account) {
			if(account instanceof Checking) {
				checkingCount++;
			}

		}
		return checkingCount;
	}
	
	public static int getNumberOfRegularAccounts() {
		int regularCount = 0;
		for(Account account : account) {
			if(account instanceof Regular) {
				regularCount++;
			}
		}
		return regularCount;
	}

	public static void saveAccountObjects() {
		System.out.println();
		File g = new File(ACCOUNTFILE);
		System.out.println("saving " + ACCOUNTFILE);
		try	{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(g));
			out.writeObject(account);//Write the accounts object into the file
			out.close();
			System.out.println(ACCOUNTFILE + "Saved Sucessful!");
		} catch (Exception e) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
		// ********************************************************************
		//Retrieve the object stored in the file by opening the file then read the object from it:
	}

	@SuppressWarnings("unchecked")
	public static void openAccountObjects() {
		File g = new File(ACCOUNTFILE);
		System.out.println();
		if (g.exists()) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(g));
				account = (ArrayList<Account>) in.readObject();
				for (Account ac: account) {
					accountNumber++;
					System.out.println(ac);
					}
				in.close();
			} catch (Exception e) {
				System.out.println("Error occurred during reading the file");
				System.out.println( e.getMessage() );
				e.printStackTrace();
			}			  
		} else {
			System.out.println("There is no file " + ACCOUNTFILE);
		}
	}
	
	public static void saveCustomerObjects() {
		System.out.println();
		File g = new File(CUSTOMERFILE);
		System.out.println("saving " + CUSTOMERFILE);
		try	{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(g));
			out.writeObject(customers);//Write the accounts object into the file
			out.close();
			System.out.println(CUSTOMERFILE + "Saved Sucessful!");
		} catch (Exception e) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
			System.exit(0);
		}
		// ********************************************************************
		//Retrieve the object stored in the file by opening the file then read the object from it:
	}

	@SuppressWarnings("unchecked")
	public static void openCustomerObjects() {
		System.out.println();
		File g = new File(CUSTOMERFILE);
		if (g.exists()) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(g));
				customers = (ArrayList<Customer>) in.readObject();
				for (Customer ac: customers) {
					customerID++;
					System.out.println(ac);
					}
				in.close();
			} catch (Exception e) {
				System.out.println("Error occurred during reading the file");
				System.out.println( e.getMessage() );
				e.printStackTrace();
			}			  
		} else {
			System.out.println("There is no file " + CUSTOMERFILE);
		}
		
	}
}












