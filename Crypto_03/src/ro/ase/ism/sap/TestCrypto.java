package ro.ase.ism.sap;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class TestCrypto {

	public static void main(String[] args) 
			throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, SignatureException {
		
		KeyStoreManager.list("ismkeystore.ks", "passks");
		
		//get and print the public key for keystore entry
		KeyStore ks = KeyStoreManager.getKeyStore("ismkeystore.ks", "passks", "pkcs12");
		PublicKey ism1Pub = KeyStoreManager.getPublicKey(ks, "ismkey1");
		Util.printHex(ism1Pub.getEncoded(), "ISM1 Public key: ");
		
		PrivateKey ism1Priv = KeyStoreManager.getPrivateKey(ks, "ismkey1", "passks");
		Util.printHex(ism1Priv.getEncoded(), "ISM1 Private key: ");
		
		PublicKey ism1PubCer = KeyStoreManager.getCertificateKey("ISMKey1CertificateX509.cer");
		Util.printHex(ism1PubCer.getEncoded(), "ISM1 Public (from .cer file) key: "); 
		
		byte[] randomAESKey = KeyStoreManager.generateRandomKey("AES", 128);
		Util.printHex(randomAESKey, "A random generated AES key" );
		
		//ENCRYPT with RSA and randomnly generated key
		//For security: only the destination will be able to get the random key: enc_pub(randomkey) -> dec_priv(randomkey)
		byte[] encAESKey = KeyStoreManager.encryptRSA(ism1PubCer, randomAESKey);
		Util.printHex(encAESKey, "Encrypted AES key ");
		
		byte[] decAESKey = KeyStoreManager.decryptRSA(ism1Priv, encAESKey);
		Util.printHex(decAESKey, "Decrypted AES key ");
		
		//For authentication: anyone can decrypt the message but they will all know it comes from you: enc_priv(randomkey) -> dec_pub(randomkey)
		byte[] message = "Hello !. How are you?".getBytes();
		byte[] encMessage = KeyStoreManager.encryptRSA(ism1Priv, message);
		byte[] initialMessage = KeyStoreManager.decryptRSA(ism1Pub, encMessage);
		System.out.println("\n" + new String(initialMessage));
		
		//digital signatures
		byte[] ds = KeyStoreManager.generateDigitalCertificate("Message.txt", ism1Priv);
		Util.printHex(ds, "Digital Signature value for Message.txt");
		
		//check the signature
		
		if(KeyStoreManager.VaildSignature("Message2.txt", ism1Pub, ds)) {
		System.out.println("\nThe signature corresponds to the file ");
		}
		else {
			System.out.println("\nVader has changed the file! ");
		//check the signature with a different public key
		}
		PublicKey webPubKey = KeyStoreManager.getCertificateKey("ism_x509_certificate.cer");
		if(KeyStoreManager.VaildSignature("Message.txt", webPubKey, ds)) {
		System.out.println("\nThe signature corresponds to the file ");
		}
		else {
			System.out.println("\nVader has changed the signature. The public key is for a different public one! ");
		}
		
		
	}

}
