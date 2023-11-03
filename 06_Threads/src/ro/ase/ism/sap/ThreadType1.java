package ro.ase.ism.sap;

public class ThreadType1 extends Thread{

	String message;
	int iterations;
	int id;
	
	
	public ThreadType1(int id, String message, int iterations) {
		this.id=id;
		this.message = message;
		this.iterations = iterations;
	}



	@Override
	public void run() {
		for(int i=0;i< this.iterations;i++) {
			System.out.println(this.id + " - " +this.message);
		}
	}

	
	
}
