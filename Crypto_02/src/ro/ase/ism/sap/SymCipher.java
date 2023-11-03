package ro.ase.ism.sap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SymCipher {
	
	
	public static void encryptECB(
	String inputFileName,
	String password,
	String outputFileName,
	String encAlgorithm) 
			throws IOException, 
			NoSuchAlgorithmException, 
			NoSuchPaddingException, 
			InvalidKeyException, 
			BadPaddingException, 
			IllegalBlockSizeException {
		
		//managing files
		File inputFile = new File(inputFileName);
		
		if(!inputFile.exists()) {
			System.out.println("File does not exist! ");
			throw new FileNotFoundException();
		}
		FileInputStream fis = new FileInputStream(inputFile);
		BufferedInputStream bis = new BufferedInputStream(fis);

		File outputFile = new File(outputFileName);
		if(!outputFile.exists()) {
			System.out.println("File does not exist");
			outputFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(outputFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		
		//create the cipher:
		Cipher cipher = Cipher.getInstance(encAlgorithm+"/ECB/PKCS5Padding"); //PKCS1 is not reversable, PKCS5 is
		///init the cipher
		SecretKeySpec key = new SecretKeySpec(password.getBytes(), encAlgorithm);
		cipher.init(Cipher.ENCRYPT_MODE,key);
		
		//read and encrypt:
		byte[] inputBuffer = new byte[cipher.getBlockSize()];//getblocksize helps us to not hardcode blocksize of each algorithm
		int noBytes = 0;
		byte[] outputBuffer;
		while((noBytes = fis.read(inputBuffer)) != -1) {
			outputBuffer = cipher.update(inputBuffer, 0, noBytes);
			fos.write(outputBuffer);
		}
		outputBuffer = cipher.doFinal();// in case the final block is different size than 8
		fos.write(outputBuffer);
		
		fis.close();
		fos.close();
	}
	
	public static void decryptECB(
			String inputFileName,
			String password,
			String outputFileName,
			String encAlgorithm) 
					throws IOException, 
					NoSuchAlgorithmException, 
					NoSuchPaddingException, 
					InvalidKeyException, 
					BadPaddingException, 
					IllegalBlockSizeException {
				
				//managing files
				File inputFile = new File(inputFileName);
				
				if(!inputFile.exists()) {
					System.out.println("File does not exist! ");
					throw new FileNotFoundException();
				}
				FileInputStream fis = new FileInputStream(inputFile);
				BufferedInputStream bis = new BufferedInputStream(fis);

				File outputFile = new File(outputFileName);
				if(!outputFile.exists()) {
					System.out.println("File does not exist");
					outputFile.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(outputFile);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				
				//create the cipher:
				Cipher cipher = Cipher.getInstance(encAlgorithm+"/ECB/PKCS5Padding"); //PKCS1 is not reversable, PKCS5 is
				///init the cipher
				SecretKeySpec key = new SecretKeySpec(password.getBytes(), encAlgorithm);
				cipher.init(Cipher.DECRYPT_MODE,key);
				
				//read and encrypt:
				byte[] inputBuffer = new byte[cipher.getBlockSize()];//getblocksize helps us to not hardcode blocksize of each algorithm
				int noBytes = 0;
				byte[] outputBuffer;
				while((noBytes = fis.read(inputBuffer)) != -1) {
					outputBuffer = cipher.update(inputBuffer, 0, noBytes);
					fos.write(outputBuffer);
				}
				outputBuffer = cipher.doFinal();// in case the final block is different size than 8
				fos.write(outputBuffer);
				
				fis.close();
				fos.close();
			}
	public static void encryptCBC(String inputFileName,
			String password,
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
	//in ECB, if the string is the same character over and over - the cipher text will follow a pattern, so we will need CBC and an IV to fix that
	//CBC requires an IV value- it's not a secret
	//option1 - predefined value
	//option2 - generate a random one
	
	//decide how to handle it
	//option 1 - hardcode the value -> not an option for option2(generated random) it cannot be hardcoded	
	//option 2 - write it in the cipher text - at the beginning
	
	//we go for a random one that we will write at the beginning of the file
	//create the Cipher
	Cipher cipher = Cipher.getInstance(encAlgorithm + "/CBC/PKCS5Padding");
	
	//get blocksize
	int blockSize = cipher.getBlockSize();
	
	//generate a random IV
	SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
	byte[] iv = new byte[blockSize];
	sr.nextBytes(iv);
	
	//create the key
	SecretKeySpec key = new SecretKeySpec(password.getBytes(), encAlgorithm);
	
	//init the cipher with the generated IV
	IvParameterSpec ivParam = new IvParameterSpec(iv);
	cipher.init(Cipher.ENCRYPT_MODE, key, ivParam);
	
	//write it in the output file
	fos.write(iv);
	byte[] inputBuffer = new byte[blockSize];
	byte[] outputBuffer;
	int noBytes;
	
	while((noBytes = fis.read(inputBuffer))!=-1) {
		outputBuffer = cipher.update(inputBuffer,0,noBytes);
		fos.write(outputBuffer);
	}
	outputBuffer=cipher.doFinal();
	fos.write(outputBuffer);
	
	fis.close();
	fos.close();
	
			
	}
	
	public static void decryptCBC(String inputFileName,
			String password,
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
		SecretKeySpec key = new SecretKeySpec(password.getBytes(), encAlgorithm);
		//we know the IV is the first block in the received file
		byte[] iv = new byte[blockSize];
		fis.read(iv);
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
	
	
	public static PBESpec encryptPBE(
			String inputFileName,
			String password,
			String outputFileName,
			String encAlgorithm) 
					throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, 
					InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
		
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
		
		//init PBE cipher 
		Cipher cipher = Cipher.getInstance(encAlgorithm);
		//create the PBE based on user string password
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[cipher.getBlockSize()]; 
		secureRandom.nextBytes(salt);
		int noIterations = 1000;
		int blockSize = cipher.getBlockSize();
		
		PBEKeySpec pbeKey = new PBEKeySpec(password.toCharArray(), salt, noIterations,blockSize);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encAlgorithm);
		Key key = keyFactory.generateSecret(pbeKey);
		
		cipher.init(Cipher.ENCRYPT_MODE, key);
		//print the PBE details
		System.out.println("Using PBE with thee user password " + password);
		System.out.println("The PBE generated key is " + pbeKey.toString());
		Util.printHex(cipher.getIV(), "PBE generated IV ");
		Util.printHex(key.toString().getBytes(), "PBE generated key ");
		Util.printHex(salt, "Random salt ");
		System.out.println("\nNo iterations " + noIterations);
		
		PBESpec pbeSpecs = new PBESpec(cipher.getParameters().getEncoded(),cipher.getIV(),salt,noIterations);
		
		
		//encryption
		
		
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
		
		
		return pbeSpecs;
		
		
	}
	
	
	public static void decryptPBE(
			String inputFileName,
			String password,
			String outputFileName,
			String encAlgorithm, PBESpec pbeSpec) 
					throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, 
					InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException {
		
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
		Cipher cipher = Cipher.getInstance(encAlgorithm);
		int blockSize = cipher.getBlockSize();
		
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(),pbeSpec.getSalt(),pbeSpec.getNoIterations(), blockSize);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encAlgorithm);
		Key key = keyFactory.generateSecret(keySpec);
		
		AlgorithmParameters algParams = AlgorithmParameters.getInstance(encAlgorithm);
		algParams.init(pbeSpec.getParams());
		
		cipher.init(Cipher.DECRYPT_MODE, key ,algParams);
		
		//decryption
		
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
	
	}
	
	
