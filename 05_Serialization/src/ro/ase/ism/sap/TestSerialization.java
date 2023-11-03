package ro.ase.ism.sap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestSerialization {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		System.out.println("Starting app...");
		DbConnection con1 = new DbConnection("localhost", 1443,"1234".getBytes(), "admin", true);
		DbConnection con2 = new DbConnection("localhost", 3306 ,"123456".getBytes(), "admin", true);
		
		//open a binary file to store the serialization objects
		File file = new File("Connections.dat");
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		DataOutputStream dos = new DataOutputStream(fos);
		
		con1.writeObject(dos);
		con2.writeObject(dos);
		dos.close();
		
		DbConnection con3 = new DbConnection("", 0, null, "", false);
		DbConnection con4 = new DbConnection("", 0, null, "", false); 
		
		FileInputStream fis = new FileInputStream(file);
		DataInputStream dis = new DataInputStream(fis);
		
		con3.readObject(dis);
		con4.readObject(dis);
		dis.close();
		
		System.out.println(con3.getServerName() + " - " + con3.isUseSecureConnection());
		System.out.println(con4.getServerName() + " - " + con4.getPassword() + " - " + con4.isUseSecureConnection());
		
		
		
		//use java serialization mechanism
		File javaFile = new File("OtherConnWithoutPass.dat");
		if(!javaFile.exists()) {
			javaFile.createNewFile();
		}
		
		fos = new FileOutputStream(javaFile);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(con1);
		oos.writeObject(con2);
		fos.close();
		
		fis = new FileInputStream(javaFile);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		DbConnection con5 = new DbConnection("", 0, null, "", false);
		DbConnection con6 = new DbConnection("", 0, null, "", false);
		
		con5 = (DbConnection) ois.readObject();
		con6 = (DbConnection) ois.readObject();
		
		System.out.println(con5.getServerName() + " - " + con5.getPassword() + " - " + con5.isUseSecureConnection());
		System.out.println(con6.getServerName() + " - " + con6.getPassword() + " - " + con6.isUseSecureConnection());
		ois.close();
		
		//getting data from the older file
		File oldFile = new File("OtherConn.dat");
		fis = new FileInputStream(oldFile);
		ois = new ObjectInputStream(fis);
		con5 = (DbConnection) ois.readObject();
		System.out.println("After adding new attribute: " + con5.getServerName() + " - " + con5.getPassword() + " - " + con5.isUseSecureConnection());
		//this was an error but because we added the serialVersionUID = 4988458112122659323l; in the DbConnection class
		fis.close();
		
		System.out.println("The end");
		
		
		
	}

}
