package wak.work.cryptogram.graem.securecrypto;

/**
 * Created with IntelliJ IDEA.
 * User: graeme.rowles
 * Date: 06/06/13
 * Time: 15:56
 */
public enum CvvType
{
    //Visa CVV1 / MasterCard CVC1
    CVV1,

    //Visa CVV2 / MasterCard CVC2
    CVV2,

    //Visa iCVV / MasterCard iCVC
    iCVV,

    //Visa CAVV / MasterCard AVV
    CAVV,

    //Amex CSC
    CSC,

    //Amex Enhanced CSC
    CSCenhanced,

    //Amex EVV
    AEVV
}
