package ro.ase.ism.sap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Simulare {//ecryptECB
	
	public static void encryptECB(String inputFileName, String password, String outputFileName, String encAlg) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		File inputFile = new File(inputFileName);
		if(!inputFile.exists()) {
			inputFile.createNewFile();
			System.out.println("inputFile does not exist! New file created!");
		}
		FileInputStream fis = new FileInputStream(inputFile);
		
		File outputFile = new File(outputFileName);
		if(!outputFile.exists()) {
			outputFile.createNewFile();
			System.out.println("outputFile does not exist! New file creatd!");
		}
		FileOutputStream fos = new FileOutputStream(outputFile);
		
		Cipher cipher = Cipher.getInstance(encAlg + "/ECB/PKCS5Padding");
		SecretKeySpec key = new SecretKeySpec(password.getBytes(), encAlg);
		cipher.init(cipher.ENCRYPT_MODE, key);
		
		int noBytes = 0;
		byte[] inputBuffer = new byte[cipher.getBlockSize()];
		byte[] outputBuffer;
		while((noBytes = fis.read(inputBuffer)) != -1) {
			outputBuffer = cipher.update(inputBuffer,0,noBytes);
			fos.write(outputBuffer);
		}
		outputBuffer = cipher.doFinal();
		fos.write(outputBuffer);
		
		fis.close();
		fos.close();

		
	}
	
public static void decryptECB(String inputFileName, String password, String outputFileName, String encAlg) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		File inputFile = new File(inputFileName);
		if(!inputFile.exists()) {
			inputFile.createNewFile();
			System.out.println("inputFile does not exist! New file created!");
		}
		FileInputStream fis = new FileInputStream(inputFile);
		
		File outputFile = new File(outputFileName);
		if(!outputFile.exists()) {
			outputFile.createNewFile();
			System.out.println("outputFile does not exist! New file creatd!");
		}
		FileOutputStream fos = new FileOutputStream(outputFile);
		
		Cipher cipher = Cipher.getInstance(encAlg + "/ECB/PKCS5Padding");
		SecretKeySpec key = new SecretKeySpec(password.getBytes(), encAlg);
		cipher.init(cipher.DECRYPT_MODE, key);
		
		int noBytes = 0;
		byte[] inputBuffer = new byte[cipher.getBlockSize()];
		byte[] outputBuffer;
		
		while((noBytes = fis.read(inputBuffer))!=-1){
			outputBuffer = cipher.update(inputBuffer,0,noBytes);
			fos.write(outputBuffer);
		}
		outputBuffer = cipher.doFinal();
		fos.write(outputBuffer);
		fis.close();
		fos.close();

		
	}
}
