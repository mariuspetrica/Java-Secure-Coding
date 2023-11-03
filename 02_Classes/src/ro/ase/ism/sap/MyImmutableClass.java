package ro.ase.ism.sap;

public class MyImmutableClass {
	final int id;
	final String key;
	final int salt;
	
	public MyImmutableClass(int id, String key, int salt) {
		this.id = id;
		this.key = key;
		//shadowing
		//salt = salt;
		this.salt = salt;
	}
}
