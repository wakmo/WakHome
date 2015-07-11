package wak.work.cryptogram.graem.securecrypto;

public enum EmvCertificateType {
	CardCertificate('0'),
	CardPINEnciphermentCertificate('1');
	
	private final char val;
	
	EmvCertificateType(char val) {
		this.val = val;
	}
	
    public char getChar() {
		return val;
	}
}
