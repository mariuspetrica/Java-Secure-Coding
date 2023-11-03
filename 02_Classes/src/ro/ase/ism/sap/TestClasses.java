package ro.ase.ism.sap;

public class TestClasses {

	public static void main(String[] args) throws CloneNotSupportedException {
	String fileName1 = "mysecretmsg.enc";
	byte[] byteRepresentation = fileName1.getBytes();
	//String are immutable
	fileName1 = "newFile.txt"; //creating a new String object
	String fileName2 = "newFile.txt"; //is not creating a new object but taking it from the constant pool by the Java Virtual Machine
	String fileName3 = fileName2;
	//is always checking addresses
	String fileName4 = new String("newFile.txt");
	
	if(fileName1 == fileName2) {
		System.out.println("They are equal!");
	}else System.out.println("They are different!");
	
	if(fileName1 == fileName4) {//false because we forcely created a new string.
		System.out.println("They are equal!");
	}else System.out.println("They are different!");
	
	//to check strings always use equals, not ==
	if(fileName1.equals(fileName4)){//false because we forcely created a new string.
		System.out.println("They are equal!");
	}else System.out.println("They are different!");
	
	fileName2="newFile2.txt"; //get a new object
	
	int value1 = 23;
	//auto-boxing mechanism
	Integer object1 = value1; //generates a new Integer object and incapsulates the value
	int value2 = object1; //works the other way around as well (un-boxing)
	
	Integer oVb1 = 23;
	Integer oVb2 = 23;
	 if (oVb1 == oVb2) {
		 System.out.println("They are equal! ");
	 }else
		 System.out.println("They are not equal! ");
	 
	 Integer oVb3 = 230;
	 Integer oVb4 = 230; //VALUE BIGGER THAT 127 and smaller than -127 will not be stored by the JVM constant pool, so == will not work because different objects.
	 if (oVb3 == oVb4) {
		 System.out.println("They are equal! ");
	 }else
		 System.out.println("They are not equal! ");
	//right way to do it: equals
	 if (oVb3.equals(oVb4)) {
		 System.out.println("They are equal! ");
	 }else
		 System.out.println("They are not equal! ");
	 
	 oVb3=300; //Integer is immutable -> you get a new object.
	 
	 
	 //shallow copy
	 UserAccount user1 = new UserAccount(1,"1234");
	 System.out.println("USer 1 " + user1.toString());
	
	 UserAccount user2 = new UserAccount(2,"123456");
	 System.out.println("USer 2 " + user2.toString());
	 
	 //both will refference same object
	 user2 = user1;
	 System.out.println("USer 2 " + user2.toString());
	 
	 System.out.println(user1.getInfo());
	 System.out.println(user2.getInfo());
	 user1.password = "password";
	 System.out.println(user1.getInfo());
	 System.out.println(user2.getInfo());//changing user1 pass is changing user2 password as well (references same object)
	 
	 int[] values1 = new int[] {1,2,3,4,5};
	 System.out.println("Values 1 = ");
	 for (int value: values1) {
		 System.out.print(" " +value);
	 }
	 
	 //shallow copy:
	 
	 int[] values2 = values1;
	 System.out.println("\nValues 2 = ");
	 for (int value: values2) {
		 System.out.print(" " +value);
	 }
	 values1[2] = 99;
	 System.out.println("\nValues 2 = ");
	 for (int value: values2) {
		 System.out.print(" " +value);
	 }
	 
	 //deep copy for UserAccount
	 UserAccount user3 = (UserAccount) user2.clone();
	 
	 int[] values3 = values2.clone();
	 values3 [0] = 100;
	 System.out.println("\nValues 2 = ");
	 for (int value: values2) {
		 System.out.print(" " +value);
	 }
	 System.out.println("\nValues 3 = ");
	 for (int value: values3) {
		 System.out.print(" " +value);
	 }
	}

}
