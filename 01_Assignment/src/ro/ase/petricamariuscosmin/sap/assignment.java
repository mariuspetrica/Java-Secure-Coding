package ro.ase.petricamariuscosmin.sap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class assignment {

	public static void printHex(byte[] values, String description) {
		System.out.println("\nHex value for: " + description);
		for(byte value : values) {
			System.out.printf("%02x",value);
		}
	}
	public static String toHex(byte[] values, String description) {
		StringBuilder sb = new StringBuilder();
	    for (byte byteno : values) {
	        sb.append(String.format("%02x", byteno));
	    }
	    String finalString = sb.toString();
	    return finalString;
	}
	
	
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		long tstart = System.currentTimeMillis();
		
		String myVal="82e14169ced3bd6612336fe774e90dc7eb2e302f8bcc6aef4ef46cbf6267db34";
		
		File inputMessage = new File("ignis-10M.txt");
		if(!inputMessage.exists()) {
			System.out.println("The input file is missing!*******************");
		}else {
			FileReader reader = new FileReader(inputMessage);
			BufferedReader bReader = new BufferedReader(reader);
			
			String line="";
			while(line !=null) {
				line=bReader.readLine();
				if(line!=null) {
				//System.out.println("File line: "+line);
					
			//ADD SALT
				String newline = "ismsap" + line;
				////////////System.out.println("Newline: " + newline);
				
			//HASHING MD5
				MessageDigest messageDigestMd5 = MessageDigest.getInstance("MD5");
				messageDigestMd5.update(newline.getBytes());
				byte[] Md5Val = messageDigestMd5.digest();
				
				///////System.out.println("MD5 toHex: " + toHex(Md5Val, "MD5")); 
				
			//HASHING SHA-256
				MessageDigest messageDigestSha = MessageDigest.getInstance("SHA-256");
				messageDigestSha.update(Md5Val);
				byte[] ShaValue = messageDigestSha.digest();
	
				//String stringHash = new String(messageDigest.digest());
				//System.out.println("\nstring hash: " + stringHash);
				//printHex(ShaValue, "SHA-256");
				////////System.out.println("SHA toHex: " + toHex(ShaValue, "SHA-256")); 
				////////System.out.println("VAL toHex: " + myVal);
				////////System.out.println("Line number: " + i);
				if(toHex(ShaValue, "SHA-256").equals(myVal)) {
					
					/*
					 * File messageFile = new File("mypassword.txt"); if(!messageFile.exists()) {
					 * messageFile.createNewFile(); }
					 * 
					 * PrintWriter pWriter = new PrintWriter(messageFile); pWriter.println(line);
					 * pWriter.close();
					 */
					System.out.println("My password is: " + line);
					line = null;
					}
				}
				
			}
			reader.close();
			long tfinal = System.currentTimeMillis();
			System.out.println("Duration is : " + (tfinal-tstart));

		}
	}

}
