package ro.ase.ism.sap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.UnexpectedException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TestCrypto {
	

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, KeyStoreException, CertificateException, UnrecoverableKeyException, SignatureException {
		// TODO Auto-generated method stub
		byte[] hashValue = null;
		File file = new File("msg.txt");
		if(!file.exists()) {
			System.out.println("The file does not exist");
			return;
		}

		FileInputStream fileStream = new FileInputStream(file);
		byte[] inputBuffer = new byte[8];
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		int noBytes;
	
		while((noBytes = fileStream.read(inputBuffer)) != -1) {
			sha.update(inputBuffer,0, noBytes);
		}
		
		hashValue = sha.digest();
		
		System.out.println("\nHex value for: " + "SHA256");
		for(byte value : hashValue) {
			System.out.printf("0x%02x ",value);
			}

	//////////////////////////////////////////////////////////////////////////////////////////////
	
	File outputFile = new File("enc_msg.aes");
	if (!outputFile.exists()) {
		System.out.println("File does not exist");
		outputFile.createNewFile();
	}
		FileOutputStream fos = new FileOutputStream(outputFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

	Cipher cipher = Cipher.getInstance("AES" + "/CBC/PKCS5Padding");
	
	//get blocksize
	int blockSize = cipher.getBlockSize();
	
	byte[] iv = new byte[]{0b0000_0000,0b0000_0000,0b0000_0000,0b0000_0000,0b0000_0000,(byte) 0xCC,0b0000_0000,0b0000_0000,
			   0b0000_0000,0b0000_0000,0b0000_0000,0b0000_0000,0b0000_0000,0b0000_0000,0b0000_0000,0b0000_0000};
	
	//create the key
	
	SecretKeySpec key = new SecretKeySpec("passwordsecurity".getBytes(), "AES");
	
	//init the cipher with the generated IV
	IvParameterSpec ivParam = new IvParameterSpec(iv);
	cipher.init(Cipher.ENCRYPT_MODE, key, ivParam);
	
	//write it in the output file
	//fos.write(iv);
	
	byte[] inputBufferAES = new byte[blockSize];
	byte[] outputBuffer;
	int noBytesAES;
	
	while((noBytesAES = fileStream.read(inputBufferAES))!=-1) {
		outputBuffer = cipher.update(inputBufferAES,0,noBytesAES);
		fos.write(outputBuffer);
	}
	outputBuffer=cipher.doFinal();
	fos.write(outputBuffer);
	
	fileStream.close();
	fos.close();

//////////////////////////////////////////////////////////////////////////////
	
	File ksFile = new File("myexam.keystore");
	if(!ksFile.exists()) {
		System.out.println("File not found!");
		throw new FileNotFoundException();
	}
	FileInputStream fis = new FileInputStream(ksFile);
	
	KeyStore ks = KeyStore.getInstance("pkcs12");

	ks.load(fis, "myexam".toCharArray());
	
	if(ks==null) {
		System.out.println("No key Store available!");
		throw new UnexpectedException("No keystore!");
	}
	
	
		PrivateKey myPrivKey = (PrivateKey) ks.getKey("teiid","myexam".toCharArray());
	
	
//////////////////////////////////////////////////////////////////////////////
	
		Signature signature = Signature.getInstance("SHA1withRSA");
		signature.initSign(myPrivKey);
		
		File fileSign = new File("enc_msg.aes");
		if(!file.exists()) {
			System.out.println("File is missing!");
			throw new FileNotFoundException();
		}
		
		FileInputStream fisSign = new FileInputStream(fileSign);

		byte[] fileContent = fisSign.readAllBytes();
		signature.update(fileContent);
		byte[] signValue = signature.sign();

		
		System.out.println("\nDigital signature of msg_enc_aes");
		for(byte value : signValue) {
			System.out.printf("0x%02x ",value);
			}

///////////////////////////////////////////////////////

				PublicKey myPubKey = ks.getCertificate("teiid").getPublicKey();
			
			
	
		signature.initVerify(myPubKey);;

		signature.update(fileContent);
		
		//if(signature.verify(signValue)) {
			System.out.println("\nThe signature corresponds to the file ? -->"+ signature.verify(signValue));
		//	}
		fisSign.close();
	}

	
	
}

