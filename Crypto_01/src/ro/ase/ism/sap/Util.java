package ro.ase.ism.sap;

public class Util {
	public static void printHex(byte[] values, String description) {
		System.out.println("\nHex value for: " + description);
		for(byte value : values) {
			System.out.printf("0x%02x ",value);
		}
	}
}
