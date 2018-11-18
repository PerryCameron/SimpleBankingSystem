package simplebankingsystem;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Account implements Serializable {

	//Instance variables:
	protected int accountNumber;
	protected double balance;
	private java.util.Date dateCreated;
	protected int transactionCount;
	
	// Aggregation relationships
	protected Customer customer;
	
	// constructor for benefit of sub-classes
	public Account(int accountNumber, double balance, Customer customer) {
		super();
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.customer = customer;
		System.out.printf("Account Number set to: %d\n", accountNumber);
		setDateCreated(new java.util.Date());
	}
	
	//  this function gets overridden for Gold accounts
	public void withdrawFunds(double withdrawAmount) {
		balance -= withdrawAmount;	
	}
	
	/**
	 * @param depost funds in account
	 */
	public void depositFunds(double depositAmount) {
		balance += depositAmount;
		
	}
	/**
	 * @return the accountNumber
	 */
	public int getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param balance2 the accountNumber to set
	 */
	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	
	public int getCustomerID() {
		return this.customer.customerID;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	// for benefit of sub-classes
	protected abstract String monthlyUpdate();

	@Override
	public String toString() {
		return "Accounts [accountNumber=" + accountNumber + ", balance=" + balance + ", dateCreated=" + dateCreated
				+ ", transactionCount=" + transactionCount + ", customer=" + customer + "]";
	}

	/**
	 * @return the dateCreated
	 */
	public java.util.Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(java.util.Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the transactionCount
	 */
	public int getTransactionCount() {
		return transactionCount;
	}

	/**
	 * @param transactionCount the transactionCount to set
	 */
	public void setTransactionCount(int transactionCount) {
		this.transactionCount += transactionCount;
	}

	public String getAccountType() {
		return "Account";
	}

}
