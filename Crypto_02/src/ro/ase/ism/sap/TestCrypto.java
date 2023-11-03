package ro.ase.ism.sap;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class TestCrypto {

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, IOException, InvalidAlgorithmParameterException, InvalidKeySpecException {
		System.out.println("Starting ENCRYPT ECB...");
		SymCipher.encryptECB("message.txt", "password", "message.enc","DES");
		System.out.println("ENCRYPT ECB -> That's it!");
	//message.txt size is 25bytes so it has padding to reach 32 bytes(the size of the .enc file)
		System.out.println("Starting DECRYPT ECB...");
		SymCipher.decryptECB("message.enc", "password", "decryptedmessage.txt","DES");
		System.out.println("DECRYPT -> That's it!");
	
		System.out.println("Starting ENCRYPT CBC ...");
		SymCipher.encryptCBC("message.txt", "password12345678", "messageCBC.enc", "AES");
		System.out.println("ENCRYPT CBC -> That's it!");
		
		System.out.println("Starting DECRYPT CBC ...");
		SymCipher.decryptCBC("messageCBC.enc", "password12345678", "decryptedCBCmessage.txt", "AES");
		System.out.println("DECRYPT CBC -> That's it!");
		
		System.out.println("Starting ENCRYPT ECB...");
		Simulare.encryptECB("message.txt", "password", "messageSimulare.enc","DES");
		System.out.println("ENCRYPT ECB -> That's it!");
		System.out.println("Starting DECRYPT ECB...");
		Simulare.decryptECB("messageSimulare.enc", "password", "decryptedmessageSimulare.txt","DES");
		System.out.println("DECRYPT -> That's it!");
		
		//what do we do if the user password is not = with the block size?
		//what do you do if the key is a string based on?
		//solution is: to extend/reduce passwords to required size
		//solution to convert string based passwords to binary values
		//PASSWORD BASED ENCRYPTION = PBE;
		
		System.out.println("Starting ENCRYPT PBE ...");
		PBESpec pbeSpecs = SymCipher.encryptPBE("message.txt", "a", "messagePBE.enc", "PBEWithHmacSHA256AndAES_128");
		System.out.println("salt is " + pbeSpecs.getSalt().toString());
		System.out.println("ENCRYPT PBE -> That's it!");
		
		System.out.println("Starting DECRYPT PBE...");
		SymCipher.decryptPBE("messagePBE.enc", "a", "decryptedPBEmessage.txt", "PBEWithHmacSHA256AndAES_128", pbeSpecs);
		System.out.println("DECRYPT PBE -> That's it!");
	}

}
