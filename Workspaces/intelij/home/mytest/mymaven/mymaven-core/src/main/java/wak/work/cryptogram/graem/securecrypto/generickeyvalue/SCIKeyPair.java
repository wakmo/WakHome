/*
 *
 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   SCIKeyPair.java %
 *  Created:	 Mon Oct 27 19:27:09 2003
 *  Created By:	 %created_by:  build %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  SCIKeyPair.java~3:java:UKPMA#1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

package wak.work.cryptogram.graem.securecrypto.generickeyvalue;

public class SCIKeyPair implements java.io.Serializable
{
    static final long serialVersionUID = -2248045182452709597L;

    private SCIKey protectedKey;
    private SCIKey authenticKey;

    private SCIKeyPair()
    {
    }

    public SCIKeyPair(SCIKey protectedKey, SCIKey authenticKey)
    {
        this.protectedKey = protectedKey;
        this.authenticKey = authenticKey;
    }

    public SCIKey getProtectedKey()
    {
        return protectedKey;
    }

    public SCIKey getAuthenticKey()
    {
        return authenticKey;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("protected (private) key: ").append(protectedKey);
        sb.append("; authentic (public) key: ").append(authenticKey);

        return sb.toString();
    }
}