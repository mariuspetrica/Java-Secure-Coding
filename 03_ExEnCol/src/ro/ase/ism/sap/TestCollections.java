package ro.ase.ism.sap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

class User{
	int id;
	String name;
	public User(int id, String name) {
		this.id= id;
		this.name=name;
	}
	@Override
	public String toString() {
		return this.name + " - " +this.id;
	}
	@Override
	public int hashCode() {//used by Hashtable 
		return this.id;
	}
	@Override
	public boolean equals(Object obj) {//this makes the MAP entries unique base on their ID, not adresses of memory
		User user = (User) obj;
		return this.id == user.id;
	}
	
	
}

public class TestCollections {

	public static void main(String[] args) {
		//lists
		ArrayList<Integer> values = new ArrayList<>();
		values.add(10);
		values.add(9);
		values.add(23);
		values.add(10);
		
		int number = 324;
		String numberText = new Integer(number).toString();
		System.out.println(numberText);
		numberText = number + "";
		System.out.println(numberText);
		
		System.out.println("The list is: ");
		for(int value: values) {
			System.out.println(" "+value);
		}
		
		
		//sets
		HashSet<Integer> uniqueValues = new HashSet<>();
		uniqueValues.add(10);
		uniqueValues.add(9);
		uniqueValues.add(23);
		uniqueValues.add(10);
		
		System.out.println("The set is: ");
		for(int value: uniqueValues) {
			System.out.println(" "+value);
		}
		
		//maps - dictionary
		Hashtable<Integer, String> users = new Hashtable<>();
		users.put(1, "John");
		users.put(5, "Alice");
		users.put(90, "Vader");
		users.put(1, "Doe");
		String userName=users.get(90);
		System.out.println("\nUsername is : " + userName);
		
		userName=users.get(95);
		System.out.println("\nUsername is : " + userName);
		
		userName=users.get(1);
		System.out.println("\nUsername is : " + userName);
		
		System.out.println("Keys are: ");
		for(int key : users.keySet()) {
			System.out.println("id = "+key);
		}
		
		//testing our own objects
		HashSet<User> dbUsers = new HashSet<>();
		dbUsers.add(new User(1,"John"));
		dbUsers.add(new User(1,"Alice"));
		dbUsers.add(new User(1,"John"));
		
		System.out.println("DB Users: ");
		for(User user : dbUsers) {
			System.out.println(user);
		}
		//hashTable
		
		Hashtable<User, String> superUsers = new Hashtable<>();
		User root = new User(1,"John");
		superUsers.put(root, "1234");
		superUsers.put(new User(2,"Alice"), "1234");
		superUsers.put(root, "123456");
		superUsers.put(new User(1,"John"), "1234567");
		System.out.println("Root user pass is : " + superUsers.get(root));
		
	}

}
