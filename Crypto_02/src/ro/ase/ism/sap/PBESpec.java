package ro.ase.ism.sap;

public class PBESpec {
	byte[] iv;
	byte[] salt;
	int noIterations;
	byte[] params;
	
	public PBESpec(byte[] params ,byte[] iv, byte[] salt, int noIterations) {
		
		this.salt = salt.clone();
		this.noIterations = noIterations;
		this.iv = iv.clone();
		this.params = params.clone();
	}

	public byte[] getSalt() {
		return salt;
	}

	public int getNoIterations() {
		return noIterations;
	}
	
	public byte[] getIV() {
		return this.iv;
	}
	public byte[] getParams() {
		return this.params;
	}
	
}
