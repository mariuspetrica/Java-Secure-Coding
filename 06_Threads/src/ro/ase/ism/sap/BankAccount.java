package ro.ase.ism.sap;

public class BankAccount {
	double balance;

	public BankAccount(double balance) {
		this.balance = balance;
	}
	public double getBalance() {
		return this.balance;
	}
	public void pay(double amount) {
		System.out.println("Trying to pay " +amount);
		if(amount <= this.getBalance()) {
			System.out.println("Paying " + amount);
			this.balance -= amount;
			System.out.println("Current balance: "+ balance);
		}
		else {
			System.out.println("Not enough money!");
		}
	}
}
