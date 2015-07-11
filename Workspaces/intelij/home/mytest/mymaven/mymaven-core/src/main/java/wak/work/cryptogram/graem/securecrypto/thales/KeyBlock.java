package wak.work.cryptogram.graem.securecrypto.thales;


public class KeyBlock {
	public final static int HEADER_LEN = 16;

	private static final int AUTHENTICATOR_LEN = 0;

	// PART 1: Header
	// the following fields form the 16 byte long header
	private final char VERSION_ID;         // version should be 0x30 (byte 0)
	private final String keyBlockLength;   // total length of keyblock (bytes 1-4)
	private final String keyUsage;         // e.g. key encryption, data encryption (bytes 5-6)
	private final char algorithm;          // e.g. DES, 3-DES, AES (byte 7)
	private final char modeOfUse;          // e.g. encrypt only (byte 8)
	private final String keyVersionNumber; // (bytes 9-10)
	private final char exportability;      // e.g. exportable under a trusted key (byte 11)
	private final int numOfBlocks;         // number of Optional Header Blocks (bytes 12-13)
	private final String lmkId;            // LMK identifier (to support multiple LMKs); numeric values (bytes 14-15)

	// PART 2: Optional Header Blocks 
	// (ASCII characters, variable length)
	private final OptionalHeaderBlocks optionalBlocks;

	// PART 3: Encrypted KeyData (variable length, ASCII encoded)
	//The only part of the keyblock that is encrypted is the Key Data, which contains the
	//actual key stored in the keyblock. The encryption algorithm supported is 3-DES
	//Cipher Block Chaining (CBC), using bytes 0-7 of the Header as the Initial Value (IV).
	//The encryption key will be derived from the LMK.
	// TODO? consists of 2 bytes length, n bytes key data, (6 - n%8) bytes padding
	private final String keyData;

	// PART 4:
	// Key Block Authenticator (8 ASCII characters)
	private final String authenticator;


	public KeyBlock(String keyBlock) {
		int pos = 0;
		VERSION_ID = keyBlock.charAt(pos++);
		this.keyBlockLength = keyBlock.substring(pos, pos += 3);
		this.keyUsage = keyBlock.substring(++pos, ++pos);
		this.algorithm = keyBlock.charAt(pos++);
		this.modeOfUse = keyBlock.charAt(pos++);
		this.keyVersionNumber = keyBlock.substring(pos++, ++pos);
		this.exportability = keyBlock.charAt(pos++);
		this.numOfBlocks = Integer.parseInt(keyBlock.substring(pos++, ++pos));
		this.lmkId = keyBlock.substring(pos++, ++pos);
		this.optionalBlocks = new OptionalHeaderBlocks(numOfBlocks, keyBlock);
		pos = HEADER_LEN + optionalBlocks.getTotalLen();
		// TODO? Evaluate key len field??? Add some checks, if param keyBlock is valid?
		this.keyData = keyBlock.substring(pos , pos = keyBlock.length() - AUTHENTICATOR_LEN); // TODO? needed with len?
		this.authenticator = keyBlock.substring(pos, keyBlock.length());
	}


	public char getVersionId() {
		return VERSION_ID;
	}


	public String getKeyBlockLength() {
		return keyBlockLength;
	}


	public String getKeyUsage() {
		return keyUsage;
	}

	/**
	 * Algorithm and key length;  permitted values:
	 * 'D1'  single length DES
	 * 'T2'  double length DES
	 * 'T3'  triple length DES
	 * @return Algorithm and key length
	 */
	public String getAlgorithmAndKeyLength() {
		final StringBuffer sb = new StringBuffer(2);
		sb.append(algorithm);
		switch(keyData.length()){
		case 16: sb.append('1'); break;
		case 24: sb.append('2'); break;
		case 32: sb.append('3'); break;
		}

		return sb.toString();
	}


	public char getModeOfUse() {
		return modeOfUse;
	}


	public String getKeyVersionNumber() {
		return keyVersionNumber;
	}


	public char getExportability() {
		return exportability;
	}


	public int getNumOfBlocks() {
		return numOfBlocks;
	}


	public String getLmkId() {
		return lmkId;
	}


	public OptionalHeaderBlocks getOptionalBlocks() {
		return optionalBlocks;
	}


	public String getKeyData() {
		return keyData;
	}


	public String getAuthenticator() {
		return authenticator;
	}

}
