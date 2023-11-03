package ro.ase.ism.sap;

public class TestEnums {

	enum AlgorithmNames{AES,DES,DESede,RC5;
		AlgorithmNames(){
		
	}
		};
	
	enum Ciphers{
		DEFAULT_CIPHER, AES128("AES",128,1), AES256("AES",256,2);
		//DEFAULT CIPHER IS GOING TO BE CREATED BY THE DEFAULT CONSTRUCTOR CYPHER()
		public final  String name;
		public final int blockSize;
		public final int id;
		
		private Ciphers(String name, int blockSize, int id) {
			this.blockSize=blockSize;
			this.id=id;
			this.name=name;
		}
		private Ciphers() {
			this.blockSize=64;
			this.id=0;
			this.name="";
		}
		int getBlockSize() {
			return this.blockSize;
		}
		
	}
	
	public static void main(String[] args) {
		
		AlgorithmNames alg = AlgorithmNames.DES;
		System.out.println("Algorithm name is " + alg);
		Ciphers cipher1 = Ciphers.AES128;
		System.out.println("Cipher name "+ cipher1.name);
		System.out.println("Cipher id "+ cipher1.id);
		System.out.println("Cipher blockSize "+ cipher1.blockSize);
		
		Ciphers cipher2 = Ciphers.DEFAULT_CIPHER;
		Ciphers cihper3 = Ciphers.AES128;

		
		
		
	}

}
