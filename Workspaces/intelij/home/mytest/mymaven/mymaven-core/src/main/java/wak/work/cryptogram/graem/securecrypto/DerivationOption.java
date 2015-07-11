package wak.work.cryptogram.graem.securecrypto;

/**
 * EMV methods to be used to derive a unique key from the Master Key:
 * 'A: EMV4.1 Book 2 Option A method
 * 'B: EMV 4.1 Book 2 Option B method
 * See also: EMV Specification Update Bulletin No. 46 First Edition October 2005
 * @author andreas.heilemann 
 */
public enum DerivationOption {
	A, 
	B;
	
	@Override
    public String toString() {
		return name();
	}
}
