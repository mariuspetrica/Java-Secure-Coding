package ro.ase.ism.sap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class HashFunction {
	
	public static byte[] getHash(String fileName, String provider, String type) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {
		byte[] hashValue = null;
		
		
		File file = new File(fileName);
		if(!file.exists()) {
			System.out.println("File does not exist!");
			return null;
		}
		FileInputStream fis = new FileInputStream(file);
		byte [] inputBuffer = new byte[8];
		
		//MessageDigest md = MessageDigest.getInstance(type, provider);
		
		//wrong way of doing it:
		//while(fis.read(inputBuffer)!= -1) {
		//	md.update(inputBuffer);
		//}
		//hashValue = md.digest();
		//problem is we process each time 8 bytes. it works for multiple of 8 bytes only filesize
		//way to fix: either process 1 byte at a time: byte [] inputBuffer = new byte[1]; ---->> very very slow or:
	
		int noBytes;
		
		MessageDigest md = MessageDigest.getInstance(type, provider);
		while((noBytes = fis.read(inputBuffer))!= -1) {
			md.update(inputBuffer,0,noBytes);
		}
		hashValue = md.digest();
		
		
		fis.close();
		
		
		
		return hashValue;
	}
	//this works only if all the values are in "values" variable byte array
	public static byte[] getHash(byte[] values, String provider, String type) throws NoSuchAlgorithmException, NoSuchProviderException {
		byte[] hashValue = null;
		MessageDigest md = MessageDigest.getInstance(type, provider);
		hashValue = md.digest(values);
		
		return hashValue;
		
	}
	
	
	
}
