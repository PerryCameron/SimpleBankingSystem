package simplebankingsystem;

@SuppressWarnings("serial")
public final class Gold extends Account {

	final protected double fixedInterest = .05;
	
	// constructor
	public Gold(int accountNumber, double balance, Customer customer,
			double fixedInterest) {
		super(accountNumber, balance, customer);
	}
	
	// default constructor
	public Gold(int accountNumber, double balance, Customer customer) {
		super(accountNumber, balance, customer);
	}

	/**
	 * @return the fixedInterest
	 */
	public double getFixedInterest() {
		return fixedInterest;
	}

	// compound interest calculator
	public double calculateInterest() {
		return (Math.pow((1 + (fixedInterest/12)), 12) * balance) - balance;
	}

	// provides a specific monthly update for this type of account
	@Override
	public String monthlyUpdate() {
		String dollarSign = "$";
		double interest = calculateInterest();
		balance += interest;
		return String.format("--Gold Account-- \n%s%.2f Earned monthly interest \n%s%.2f final balance\n", dollarSign, interest, dollarSign, balance);
	}



	@Override
	public String toString() {
		return "Gold [fixedInterest=" + fixedInterest + "]" + super.toString();
	}

	@Override
	public String getAccountType() {
		return "Gold";
	}


}
