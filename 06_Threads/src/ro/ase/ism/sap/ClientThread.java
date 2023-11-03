package ro.ase.ism.sap;

import java.util.Random;

public class ClientThread implements Runnable{

	String name;
	BankAccount account;
	
	
	public ClientThread(String name, BankAccount account) {
		super();
		this.name = name;
		this.account = account;
	}


	@Override
	public void run() {
		while(account.getBalance() > 0) {//infinite loop
		Random  random = new Random();
		double amount = random.nextInt(10);
		System.out.println(this.name + "is doing a payment!");
		account.pay(amount);
		}
		
	}

}
