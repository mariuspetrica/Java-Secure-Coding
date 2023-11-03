package ro.ase.ism.sap;

public class UserAccount {
	public int userId;
	public String password;
	public int failedLogins;
	
	public UserAccount(int userId, String password) {
		this.userId = userId;
		this.password = password;
	}
	
	String getInfo() {
		return String.format("User id = %d, Pass =%s, Failed logins: %d",this.userId,this.password,this.failedLogins);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new UserAccount(this.userId, this.password);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		UserAccount received = (UserAccount) obj;
		return (this.userId == received.userId);
	}
	
	
	
	
	
	
	
}
