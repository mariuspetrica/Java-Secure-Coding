package ro.ase.ism.sap;

import java.io.FileNotFoundException;

public class TestExceptions {

		public static void generateException(int number) throws SmallIntException, FileNotFoundException {
			if(number<Byte.MAX_VALUE)
				throw new SmallIntException();
			if(number==200)
				throw new FileNotFoundException();
		}
	
	
	
	public static void main(String[] args){
		System.out.println("Start...");
		int value =10;
		//throw an exception:
		try {
			System.out.println("Starting try");
			value += 2;
			generateException(100);//nothing after this block executes, because it gets into catch
			//generateException(200);
			//generateException(300);
			value +=2;
			System.out.println("Ending try");
		} 
		catch (SmallIntException e) {
			System.out.println("The number was a small one");
			e.printStackTrace();
		} 
		catch (FileNotFoundException ex) {
			System.out.println("The number was 200");
			value = 200;
		}
		catch(Exception ex) {//if default catch is first, no other catch gets asked
			System.out.println("Another error " + ex.getMessage());
		}
		finally {
			System.out.println("\nAlways executed");
		}
		System.out.println("The value is " + value);
		
		System.out.println("The end");

	}

}


class SmallIntException extends Exception{
	public SmallIntException(String msg) {
	super(msg);
	}
	public SmallIntException() {
	}
}