package ro.ase.ism.sap;

import java.security.Provider;
import java.security.Security;

public class Providers {
	public static void loadBCProvier(String providerName) {
		Provider provider = Security.getProvider(providerName);
		if(provider != null) {
			System.out.println(providerName + " is already available!");
		}else {
			//load it
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			//do a second check
			provider = Security.getProvider(providerName);
			if(provider != null) {
				System.out.println(providerName + " is loaded!");
				}else {
					System.out.println("We have a problem! ");
				}
		}
	}
	public static void checkProvider(String providerName) {
		Provider provider = Security.getProvider(providerName);
		if(provider != null) {
			System.out.println(providerName + " is already available!");
		}else {
			System.out.println(providerName + " is not available!");
		}
	}
	
	
	
	
}
