package wak.work.cryptogram.graem.securecrypto;

public enum SignatureAlgorithm {
	EmvRSA(1); 
	
	final int value;
	
	SignatureAlgorithm(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
