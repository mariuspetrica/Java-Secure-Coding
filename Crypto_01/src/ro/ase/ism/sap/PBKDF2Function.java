package ro.ase.ism.sap;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF2Function {

		private int noIterations;
		private int outputHashSize;//output hash value (size of bits)
		private String algorithm;
		private String provider;
		
		public PBKDF2Function(int noIterations, int outputHashSize, String algorithm, String provider) {
			this.noIterations = noIterations;
			this.outputHashSize = outputHashSize;
			this.algorithm = algorithm;
			this.provider = provider;
		}
		
		public byte[] getHash(char[] userPassword, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
			
			byte[] hashValue = null;
			PBEKeySpec key = new PBEKeySpec(userPassword, salt, noIterations, outputHashSize);
			SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm,provider);
			hashValue = skf.generateSecret(key).getEncoded();
			
			
			return hashValue;
		}
		
		
		
}
