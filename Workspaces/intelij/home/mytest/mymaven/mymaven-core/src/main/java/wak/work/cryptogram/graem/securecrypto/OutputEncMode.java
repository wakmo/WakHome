/*
	@author Sunil Krishnamurthy
*/

package wak.work.cryptogram.graem.securecrypto;

public class OutputEncMode implements java.io.Serializable{

	public final static OutputEncMode ENCRYPT_UNDER_LMK38_39V9 	= new OutputEncMode(0);
	public final static OutputEncMode ENCRYPT_UNDER_ZPK 		= new OutputEncMode(1);

	private final static String[] oem = {"ENCRYPT_UNDER_LMK38_39V9", "ENCRYPT_UNDER_ZPK"};

	private OutputEncMode(int value){
		this.value = value;
	}

	public static OutputEncMode getOutputEncMode(int value)throws IllegalArgumentException{
		if(value != OutputEncMode.ENCRYPT_UNDER_LMK38_39V9.getValue() && value != OutputEncMode.ENCRYPT_UNDER_ZPK.getValue()){
			throw new IllegalArgumentException("value " + value + " invalid for OutputEncMode");
		}
		switch(value){
			case 0:
				return OutputEncMode.ENCRYPT_UNDER_LMK38_39V9;
			case 1:
				return OutputEncMode.ENCRYPT_UNDER_ZPK;
		}
		// code can never reach here
		return null;
	}

	public int getValue(){
		return value;
	}

	public boolean equals(Object obj){
		if(obj != null && obj instanceof OutputEncMode){
			return ((OutputEncMode)obj).getValue() == value;
		}
		return false;
	}

	public String toString(){
		return oem[value];
	}

	private int value = 0;

}
