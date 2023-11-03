package ro.ase.ism.sap;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import org.bouncycastle.jcajce.provider.digest.GOST3411.HashMac;

public class TestCrypto {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, IOException, InvalidKeyException, InvalidKeySpecException 
	{
		
		//final String BCProvider = "BC";
		final String StandardProvider = "SunJCE"; //sun standard provider
		
		//(Sun, SunJSSE, SunJCE, SunRsaSign
		//load BC provider
		Providers.loadBCProvier(StandardProvider);
		Providers.checkProvider(StandardProvider);
		Providers.checkProvider("Sun");
		Providers.checkProvider("SunJSSE");
		Providers.checkProvider("SunRsaSign");
		//Message Digest
		byte[] mdValue = HashFunction.getHash("message.txt", StandardProvider, "SHA-256");
		byte[] mdValue2 = HashFunction.getHash("message.txt", StandardProvider, "MD5");
		Util.printHex(mdValue, "SHA-256");
		Util.printHex(mdValue2, "MD5");
		
		byte[] mdValue3 = HashFunction.getHash("This is a secret message".getBytes(), StandardProvider, "SHA-256");
		Util.printHex(mdValue3, "SHA-256");
		
		//MAC
		byte[] macValue = HashMACFunction.getHashMAC("message.txt", "password".getBytes(), StandardProvider, "HmacSHA1");
		Util.printHex(macValue, StandardProvider);
		
		//PBKDF2
		PBKDF2Function pbk = new PBKDF2Function(10000, 128, "PBKDF2WithHmacSHA1", StandardProvider);
		char[] userPassword = "123456".toCharArray();
		byte[] salt = SaltFunction.getRandomSalt(16);
		
		long tStart = System.currentTimeMillis();
		
		byte[] saltedHash = pbk.getHash(userPassword, salt);
		
		long tFinal = System.currentTimeMillis();
		Util.printHex(saltedHash, "PBKDF2WithHmacSHA1");
		System.out.println("\nTime(ms) needed to hash the user password: " +( tFinal-tStart));
	
		
	}

}
