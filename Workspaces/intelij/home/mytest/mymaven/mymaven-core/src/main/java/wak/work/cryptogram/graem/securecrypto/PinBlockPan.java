package wak.work.cryptogram.graem.securecrypto;

/**
 * Created with IntelliJ IDEA.
 * User: graeme.rowles
 * Date: 12/05/14
 * Time: 11:59
 *
 * A utility class for extracting part of a PAN from a full PAN ready for use in producing a PIN Block
 */
public class PinBlockPan
{
    /**
     * The full PAN
     */
    private final String pan;


    /**
     * Constructs a new PinBlockPan instance given a full PAN
     *
     * @param pan The full PAN
     */
    public PinBlockPan(String pan)
    {
        this.pan = pan;
    }

    /**
     * Removes the final check digit, and if necessary truncates or pads with zeros on the left
     *
     * @return 12-digit PAN without a check digit, pre-padded with zeros if necessary
     */
    public String getAdjustedPan() throws SecureCryptoException
    {
        if (pan == null)
        {
            throw new SecureCryptoException("PAN is null");
        }

        String panStr = pan;
        if (panStr.length() < 13)
        {
            panStr = "0000000000000" + panStr;
        }

        //Return the rightmost 12 digits, excluding the very rightmost 1 digit
        return panStr.substring(panStr.length() - 13, panStr.length() - 1);
    }
}
