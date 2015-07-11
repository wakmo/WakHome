package wak.work.cryptogram.graem.securecrypto.thales;

import java.util.LinkedList;
import java.util.List;

public class OptionalHeaderBlocks {

	private final List<String> blocks;
	private final int totalLen;

	/**
	 * @param numOfBlocks the number of optional header blocks within the key block
	 * @param keyBlock the whole key block
	 */
	public OptionalHeaderBlocks(int numOfBlocks, String keyBlock) {
		blocks = new LinkedList<String>();
		int startIndex = KeyBlock.HEADER_LEN;
		for (int i=0; i<numOfBlocks; i++) {
			startIndex = addOptionalBlock(startIndex, keyBlock);
		}
		totalLen = startIndex - KeyBlock.HEADER_LEN;
	}

	/**
	 * 
	 * @param startIndex start index of this OptionalBlock within the whole key block
	 * @param keyBlock the whole keyblock (incl. header)
	 * @return endIndex endIndex of this OptionalBlock (which is the startIndex of next OptionalBlock)
	 */
	private int addOptionalBlock(int startIndex, String keyBlock) {
		final int len = Integer.parseInt(keyBlock.substring(2, 4));
		final int endIndex = startIndex + len;
		blocks.add(keyBlock.substring(startIndex, endIndex));
		return endIndex;
	}

	public List<String> getBlocks() {
		return blocks;
	}

	/**
	 * @return total length of all optional header blocks
	 */
	public int getTotalLen() {
		return totalLen;
	}
}
