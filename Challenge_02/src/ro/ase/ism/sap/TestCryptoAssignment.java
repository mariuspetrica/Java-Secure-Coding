package ro.ase.ism.sap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;





public class TestCryptoAssignment {
	
	private static String getFileChecksum(MessageDigest digest, File file) throws IOException
	{
	  //Get file input stream for reading the file content
	  FileInputStream fis = new FileInputStream(file);
	   
	  //Create byte array to read data in chunks
	  byte[] byteArray = new byte[1024];
	  int bytesCount = 0; 
	    
	  //Read file data and update in message digest
	  while ((bytesCount = fis.read(byteArray)) != -1) {
	    digest.update(byteArray, 0, bytesCount);
	  };
	   
	  //close the stream; We don't need it now.
	  fis.close();
	   
	  //Get the hash's bytes
	  byte[] bytes = digest.digest();
	   
	  //This bytes[] has bytes in decimal format;
	  //Convert it to hexadecimal format
	  StringBuilder sb = new StringBuilder();
	  for(int i=0; i< bytes.length ;i++)
	  {
	    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	  }
	   
	  //return complete hash
	   return sb.toString();
	}
	
	
	public static String byteArrayToHex(byte[] a) {
		   StringBuilder sb = new StringBuilder(a.length * 2);
		   for(byte b: a)
		      sb.append(String.format("%02x", b));
		   return sb.toString();
		}
	
	
	
	public static void printHex(byte[] values, String description) {
		System.out.println("\nHex value for: " + description);
		for(byte value : values) {
			System.out.printf("%02x",value);
		}
	}
	

    private static byte[] checksum(String filepath, MessageDigest md) throws IOException {
        	FileInputStream fis = new FileInputStream(filepath);
            byte[] buffer = new byte[1024];
            int nread;
            while ((nread = fis.read(buffer)) != -1) {
                md.update(buffer, 0, nread);
            }
        //return the byte array
        byte[] output = md.digest();
        fis.close();
        return output;

    }
	
    
    public static void decryptCBC(String inputFileName,
			byte[] password,
			String outputFileName,
			String encAlgorithm) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		
		File inputFile = new File(inputFileName);

		if (!inputFile.exists()) {
			System.out.println("File does not exist! ");
			throw new FileNotFoundException();
		}
		FileInputStream fis = new FileInputStream(inputFile);
		BufferedInputStream bis = new BufferedInputStream(fis);

		File outputFile = new File(outputFileName);
		if (!outputFile.exists()) {
			System.out.println("File does not exist");
			outputFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(outputFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
	
		//cipher
		Cipher cipher = Cipher.getInstance(encAlgorithm+"/CBC/PKCS5Padding");
		int blockSize = cipher.getBlockSize();
		SecretKeySpec key = new SecretKeySpec(password, encAlgorithm);
		//we know the IV is the first block in the received file
		byte[] iv = new byte[]{0b0000_0001,0b0000_0001,0b0000_0001,0b0000_0001,0b0000_0001,0b0000_0001,0b0000_0001,0b0000_0001,
							   0b0000_0001,0b0000_0001,0b0000_0001,0b0000_0001,0b0000_0001,0b0000_0001,0b0000_0001,0b0000_0001};
		//fis.read(iv);
		
		IvParameterSpec ivParam = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, key, ivParam);
		
		byte[] inputBuffer = new byte[blockSize];
		byte[] outputBuffer;
		int noBytes =0;
		
		while((noBytes = fis.read(inputBuffer))!=-1) {
			outputBuffer = cipher.update(inputBuffer,0,noBytes);
			fos.write(outputBuffer);
		}
		outputBuffer = cipher.doFinal();
		fos.write(outputBuffer);
		
		fis.close();
		fos.close();
	}
    

	
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

		byte[] SHA2val = Base64.getDecoder().decode("gU+lhni5wZ88+ALBmpTSd/KkoQ7doCLAe3zrZeNY6KQ=".getBytes());
		System.out.println(SHA2val);
		printHex(SHA2val," -> SHA256 corresponding to the key file of the user");
		
		File folder = new File("C:\\SAP\\Challenge_02\\Keys");
		File[] listOfFiles = folder.listFiles();
		String HEXSHA2val = byteArrayToHex(SHA2val);
		
		for (File file : listOfFiles) {
			System.out.println(file.getName());
		 MessageDigest md = MessageDigest.getInstance("SHA-256");
	        byte[] hex = checksum(file.getAbsolutePath(), md);
	        
	        String HEXhex = byteArrayToHex(hex);

	       // System.out.println(HEXhex + HEXSHA2val);
	        if(HEXhex.equals(HEXSHA2val)){
	        	 System.out.println("File named ----- "+ file.getName() + " ----- is the correct one! ");
	        	 System.out.println(HEXhex + " is the File hash");
	        	 System.out.println(HEXSHA2val + " is the Account hash");
	        }

		}
		
		File file = new File("C:\\SAP\\Challenge_02\\Keys\\User15.key");
		FileInputStream fis = new FileInputStream(file);
		
		byte[] password = new byte[128];
		
		password = fis.readAllBytes();
		
		decryptCBC("User15.secret", password, "User15secret.txt", "AES");
		
		    }

	}
	
	
		
