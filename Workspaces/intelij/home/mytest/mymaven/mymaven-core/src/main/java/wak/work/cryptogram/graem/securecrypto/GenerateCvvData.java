package wak.work.cryptogram.graem.securecrypto;

import wak.work.cryptogram.helper.ByteArrayUtilities;

/**
 * Created with IntelliJ IDEA.
 * User: graeme.rowles
 * Date: 13/06/13
 * Time: 16:30
 *
 * Converts and input expiry date and input service code to a format ready to use in a
 * CVV/CVC/iCVV calculation
 */
public class GenerateCvvData
{
    private final String expiryDate;
    private final String serviceCode;

    public GenerateCvvData(CvvType cvvType, String expiryDateMMYY, String serviceCode) throws SecureCryptoException
    {
        checkNotNull("Expiry date", expiryDateMMYY);
        if (expiryDateMMYY.length() != 4)
        {
            throw new SecureCryptoException("Expiry date is not 4 digits in length: " + expiryDateMMYY);
        }

        checkNotNull("Service code", serviceCode);
        if (serviceCode.length() != 3)
        {
            throw new SecureCryptoException("Service code is not 3 digits in length: " + serviceCode);
        }

        if (CvvType.CVV1.equals(cvvType))
        {
            //Expiry date for CVV1 is in the form YYMM - Need to confirm this is definitely correct
            expiryDate = reverseDate(expiryDateMMYY);
            this.serviceCode = serviceCode;
        }
        else if (CvvType.CVV2.equals(cvvType))
        {
            //Expiry date for CVV2 is in the form MMYY - Need to confirm this is definitely correct

            this.expiryDate = expiryDateMMYY;
            this.serviceCode = "000";
        }
        else if (CvvType.iCVV.equals(cvvType))
        {
            //Expiry date for iCVV is in the form YYMM - Need to confirm this is definitely correct

            expiryDate = reverseDate(expiryDateMMYY);
            this.serviceCode = "999";
        }
        else
        {
            throw new SecureCryptoException("Cannot handle CVV Type: " + cvvType);
        }

        checkNumeric("Expiry Date", expiryDate);
    }

    /**
     * Returns the actual service code to use - this may be the service code passed into
     * the constructor, or it may have been replaced by a value required by the particular
     * CVV type in use
     *
     * @return The Service Code to use
     */
    public String getActualServiceCode()
    {
        return serviceCode;
    }

    /**
     * Returns the actual expiry date to use - this will be the expiry date passed in, but
     * optionally flipped around (i.e. to YYMM) depending on the CVV type in use.
     *
     * @return The Service Code to use
     */
    public String getActualExpiryDate()
    {
        return expiryDate;
    }

    //Used only by RG7000 ... PS9000 does this itself
    public byte[] getCvvDataBlock(String pan) throws SecureCryptoException
    {
        checkNumeric("PAN", pan);
        if (pan.length() > 19)
        {
            throw new SecureCryptoException("PAN too long", "PAN: " + pan);
        }

        StringBuilder sb = new StringBuilder(32);
        sb.append(pan);
        sb.append(expiryDate);
        sb.append(serviceCode);

        int paddingDigits = 32 - sb.length();
        for (int i = 0; i < paddingDigits; i++)
        {
            sb.append('0');
        }

        String hex = sb.toString();
        return ByteArrayUtilities.byteify_nospaces(hex);
    }

    private static void checkNumeric(String fieldName, String value) throws SecureCryptoException
    {
        checkNotNull(fieldName, value);

        for (char c : value.toCharArray())
        {
            if (c < '0' || c > '9')
            {
                throw new SecureCryptoException(fieldName + " contains non-decimal digits",
                        fieldName + ": " + value);
            }
        }
    }

    private static void checkNotNull(String fieldName, Object value) throws SecureCryptoException
    {
        if (value == null)
        {
            throw new SecureCryptoException(fieldName + " is null");
        }
    }

    private static String reverseDate(String date)
    {
        return date.substring(2, 4) + date.substring(0, 2);
    }
}
