package wak.work.cryptogram.graem.securecrypto;

/**
 * Created with IntelliJ IDEA.
 * User: graeme.rowles
 * Date: 01/05/13
 * Time: 11:41
 *
 * Used by Dummy HSM - if any status object is returned then it's considered a "good" status
 * because no exception was thrown.
 *
 * Do not make this an inner class of another class is that other class is not Serializable!
 */
public class HsmGoodStatus implements SecureModuleStatus
{
}
