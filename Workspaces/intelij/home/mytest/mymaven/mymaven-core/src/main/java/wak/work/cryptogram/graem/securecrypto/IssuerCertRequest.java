package wak.work.cryptogram.graem.securecrypto;

import java.io.Serializable;

/**
 * @author andreas.heilemann
 * Data Structure to hold Issuer RSA Key Set and Self-Signed Issuer Public Key Certificate
 */
public class IssuerCertRequest implements Serializable {
    private static final long serialVersionUID = 8724308439237780763L;
    
	private final byte[] privateKey;
	private final byte[] publicKey;
	private final byte[] selfSignedIssuerPublicKeyCertificate;
	private final byte[] hashValue;
	private final byte[] mac;
	
	/**
	 * @param privateKey Private Key data, encrypted using LMK pair 34-35.
	 * @param publicKey Public Key, DER encoded in ASN.1 format (sequence of modulus and exponent).
	 * @param selfSignedIssuerPublicKeyCertificate Self-Signed Issuer Public Key Certificate (the concatenation of the Clear Data and the Self-Signed Certificate) depending on Scheme ID.
	 * @param hashValue Hash value required for transfer of self signed Issuer Public Key data as defined by Scheme ID.
	 * @param mac MAC on public key and authentication data calculated using LMK 36-37
	 */
	public IssuerCertRequest(
		byte[] privateKey, 
		byte[] publicKey,
        byte[] selfSignedIssuerPublicKeyCertificate, 
        byte[] hashValue,
        byte[] mac
    ) {
	    this.privateKey = privateKey;
	    this.publicKey = publicKey;
	    this.selfSignedIssuerPublicKeyCertificate = selfSignedIssuerPublicKeyCertificate;
	    this.hashValue = hashValue;
	    this.mac = mac;
    }
	
	/**
	 * Returns the Private Key data, encrypted using LMK pair 34-35.
	 * @return the Private Key data, encrypted using LMK pair 34-35.
	 */
	public byte[] getPrivateKey() {
    	return privateKey;
    }

	/**
	 * Returns the public key in ASN1 format: i.e. as Sequence of modulus and exponent
	 * @return the public key in ASN1 format: i.e. as Sequence of modulus and exponent
	 */
	public byte[] getPublicKey() {
    	return publicKey;
    }

	/**
	 * Returns the Self-Signed Issuer Public Key Certificate (the concatenation of the Clear Data and the Self-Signed Certificate) depending on Scheme ID.
	 * @return the Self-Signed Issuer Public Key Certificate (the concatenation of the Clear Data and the Self-Signed Certificate) depending on Scheme ID.
	 */
	public byte[] getSelfSignedIssuerPublicKeyCertificate() {
    	return selfSignedIssuerPublicKeyCertificate;
    }

	/**
	 * Returns the Hash value required for transfer of self signed Issuer Public Key data as defined by Scheme ID.
	 * @return the Hash value required for transfer of self signed Issuer Public Key data as defined by Scheme ID.
	 */
	public byte[] getHashValue() {
    	return hashValue;
    }

	/**
	 * Returns the MAC on public key and authentication data calculated using LMK 36-37
	 * @return the MAC on public key and authentication data calculated using LMK 36-37
	 */
	public byte[] getMac() {
    	return mac;
    }


}
