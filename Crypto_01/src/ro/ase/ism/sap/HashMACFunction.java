package ro.ase.ism.sap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HashMACFunction {

	public static byte[] getHashMAC(String fileName, byte[] secretKey, String provider, String algorithm)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, IOException {
	
	byte[] macValue = null;
	File file = new File(fileName);

	if(!file.exists()) {
		System.out.println("File does not exist!");
		return null;
	}
	
	FileInputStream fis = new FileInputStream(file);
	//very slow
	byte[] inputBuffer = new byte[1];
	
	//create and init the Mac object
	Mac  mac = Mac.getInstance(algorithm, provider);
	mac.init(new SecretKeySpec(secretKey, algorithm));
	//read the file content and update the hash value
	
	while((fis.read(inputBuffer))!=-1) {
		mac.update(inputBuffer);
	}
	macValue=mac.doFinal();
	fis.close();
	
	return macValue;
	}	
}
