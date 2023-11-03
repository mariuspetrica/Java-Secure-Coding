package ro.ase.ism.sap;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SaltFunction {

	public static byte[] getRandomSalt(int noBytes) throws NoSuchAlgorithmException {
		byte[] randomSalt = new byte[noBytes];
		
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.nextBytes(randomSalt);
		
		return randomSalt;
	}
}
