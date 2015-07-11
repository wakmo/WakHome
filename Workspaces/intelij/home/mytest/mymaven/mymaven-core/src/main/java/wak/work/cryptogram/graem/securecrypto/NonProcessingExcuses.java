package wak.work.cryptogram.graem.securecrypto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains excuses for each available CryptoModule as to why they couldn't process a
 * command.
 *
 * This is for logging and diagnostic purposes only
 *
 * User: growles
 * Date: 22-Feb-2012
 * Time: 17:10:37
 */
public class NonProcessingExcuses implements Serializable
{
    //List of CryptoModule names which couldn't process the command
    private final List<String> cmNameList;

    //Map of CryptoModule name :-> List of excuses
    private final Map<String, List<String>> excuseMap;

    private String currentCmName;


    public NonProcessingExcuses()
    {
        cmNameList = new ArrayList<String>();
        excuseMap = new HashMap<String, List<String>>();
    }

    public void newCryptoModule(String cmName) throws SecureCryptoException
    {
        if (cmNameList.contains(cmName))
        {
            throw new SecureCryptoException("Excuse list already contains a CryptoModule by the name of " + cmName);
        }

        cmNameList.add(cmName);
        excuseMap.put(cmName, new ArrayList<String>());
        currentCmName = cmName;
    }

    public void addCmIsKeyCacherExcuse()
    {
        addExcuse("Crypto Module is for Key Caching only");
    }

    public void addKeyExcuse(Key key, String reason)
    {
        addExcuse("{Key: " + key + "} : " + reason);
    }

    public void addExcuse(String reason)
    {
        List<String> excuseList = excuseMap.get(currentCmName);
        excuseList.add(reason);
    }

    public void addExcuse(Throwable x, String reason)
    {
        String lastMessage = null;

        while (x != null)
        {
            String message = x.getMessage();
            if (message != null)
            {
                lastMessage = message;
            }

            x = x.getCause();
        }

        List<String> excuseList = excuseMap.get(currentCmName);

        if (lastMessage == null)
        {
            excuseList.add(reason);
        }
        else
        {
            excuseList.add(reason + ": " + lastMessage);
        }
    }

    public void addExcuse(String reason, Object... params)
    {
        List<String> excuseList = excuseMap.get(currentCmName);

        String formattedReason = String.format(reason, params);
        excuseList.add(formattedReason);
    }

    public String toString()
    {
        StringBuffer buff = new StringBuffer();

        if (cmNameList.isEmpty())
        {
            buff.append("SCI is not currently connected to any Crypto Modules.");
        }

        for (String cmName : cmNameList)
        {
            buff.append("Crypto Module ").append(cmName).append(":\n");

            for (String excuse : excuseMap.get(cmName))
            {
                buff.append("    ").append(excuse).append("\n");
            }

            buff.append('\n');
        }

        return buff.toString();
    }
}
