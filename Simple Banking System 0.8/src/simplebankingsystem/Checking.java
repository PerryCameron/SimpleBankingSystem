package simplebankingsystem;

@SuppressWarnings("serial")
public final class Checking extends Account {

	final protected double transactionFee = 3;
	final protected int freeTransactions = 2;

	public Checking(int accountNumber, double balance, Customer customer,
			double transactionFee) {
		super(accountNumber, balance, customer);
	}

	/**
	 * @return the transactionFee
	 */
	public double getTransactionFee() {
		return transactionFee;
	}
    
	// deposit funds
	public void depositFunds(double depositAmount) {
		super.depositFunds(depositAmount);
		setTransactionCount(getTransactionCount() + 1);
	}
	
	// prevents over-drafting 
	@Override
	public void withdrawFunds(double withdrawAmount) {
		if (withdrawAmount > balance) {
			balance = 0;
		} else {
		balance -= withdrawAmount;
		}
		
	}
	
	// provides a specific monthly update for this type of account
	@Override
	public String monthlyUpdate() {
	double fees = 0;
	String dollarSign = "$";
		if (transactionCount > freeTransactions) {
			fees = transactionFee * (transactionCount - freeTransactions);
			 		withdrawFunds(fees);
			 		setTransactionCount(0); 
		} else {
			fees = 0;
		}
		
		balance -= fees;
		
		return  String.format("--Checking account-- \n %d transactions\n %s%.2f transactions fees\n %s%.2f final balance\n", transactionCount, dollarSign ,fees, dollarSign , balance);
	}
		
	@Override
	public String toString() {
		return "Checking [transactionFee=" + transactionFee + ", freeTransactions=" + freeTransactions + "]"+ super.toString();
	}

	@Override
	public String getAccountType() {
		return "Checking";
	}
	
	

}
