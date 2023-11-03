package ro.ase.ism.sap;

public class ThreadType2 implements Runnable{
	
	String message;
	int iterations;
	int id;
	
	
	public ThreadType2( int id, String message, int iterations) {
		this.message = message;
		this.iterations = iterations;
		this.id = id;
	}



	@Override
	public void run() {
		for(int i=0;i< this.iterations;i++) {
			System.out.println(this.id + " - " +this.message);
		}
		
	}
	
}
