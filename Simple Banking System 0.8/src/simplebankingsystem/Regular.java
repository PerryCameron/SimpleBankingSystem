package simplebankingsystem;

@SuppressWarnings("serial")
public final class Regular extends Account {

	final protected double fixedInterest = 0.06;
	final protected double fixedCharge = 10;
    
	// default constructor
	public Regular(int accountNumber, double balance, Customer customer) {
		super(accountNumber, balance, customer);
	}
	
	// constructor
	public Regular(int accountNumber, double balance, Customer customer,
			double fixedInterest, double transactionFee) {
		super(accountNumber, balance, customer);
	}

	/**
	 * @return the fixedInterest
	 */
	public double getFixedInterest() {
		return fixedInterest;
	}

	/**
	 * @return the transactionFee
	 */
	public double getFixedCharge() {
		return fixedCharge;
	}
	
	/**
	 * @return calculated compound interest
	 */
	 public double calculateInterest() {
		    return (Math.pow((1 + (fixedInterest/12)), 12) * balance) - balance;
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
		String dollarSign = "$";
		double appliedFee = fixedCharge / 12;
		double interest = calculateInterest();
		balance += interest;
		balance -= appliedFee;
		return String.format("--Regular account-- \n%s%.2f Earned monthly interest \n%s%.2f Fixed fee \n%s%.2f final balance\n", dollarSign, interest, dollarSign, appliedFee, dollarSign, balance);
	}
		
	
	
	@Override
	public String toString() {
		return "Regular [fixedInterest=" + fixedInterest + ", fixedCharge=" + fixedCharge + "]" + super.toString();
	}

	@Override
	public String getAccountType() {
		return "Regular";
	}
}
