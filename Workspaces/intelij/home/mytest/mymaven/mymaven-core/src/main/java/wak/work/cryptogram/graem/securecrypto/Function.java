/*
 *

 *
 *  Project:                PROJECT NAME INSERTED HERE
 *
 *  Type:		 %cvtype:  java %
 *  Name:		 %name:   Function.java %
 *  Created:	 Thu Aug 08 16:30:36 2002
 *  Created By:	 %created_by:  izzardr %
 *  Last modified:	 %date_modified:  %
 *  CI Idenitifier:	 %full_filespec:  Function.java~1:java:1 %
 *
 *  Amendment Record
 *  Version 	Date        Author  		Description
 *
 *
 *
 *
 */

package wak.work.cryptogram.graem.securecrypto;

import java.io.Serializable;

public enum Function implements Serializable
{
    BUILD_ENCIPHER,
    BUILD_HASH,
    CALCULATE_KEY_CHECK_VALUE,
    CIPHER_IMAGE,
    CREATE_REAL_MULTOS_KTU,
    EXPORT_ENCIPHERED_KEY,
    EXTRACT_PUBLIC_KEY,
    GENERATE_DERIVED_KEY,
    GENERATE_KEY,
    GENERATE_RANDOM,
    GET_AVAILABILITY,
    GET_CAPABILITY,
    HASH_OR_MAC,
    IMPORT_ENCIPHERED_KEY,
    IMPORT_NATIVE_KEY,
    RE_ENCIPHER_DATA,
    SIGN_MESSAGE,
    TRANSLATE_MULTOS_KTU,
    VERIFY_CERTIFICATE,
    VERIFY_MESSAGE_SIGNATURE
}
