/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.rnd.helper;



import org.apache.log4j.Logger;

/**
 *
 * @author wakkir.muzammil
 */
public class DFUtilities
{
    private static Logger logger = Logger.getLogger(DFUtilities.class);
           
    public static boolean IsHSMOnAutherizedMode(String hsmAutherizedMode) throws DFUtilitiesException
    {
        boolean IsAuthModeOn=false;
        
        if(hsmAutherizedMode==null || hsmAutherizedMode.trim().length()<1)
        {
            return false;
        }               
        hsmAutherizedMode=hsmAutherizedMode.trim();
        if(DFConstant.FLAG_ON.equalsIgnoreCase(hsmAutherizedMode) || DFConstant.FLAG_TRUE.equalsIgnoreCase(hsmAutherizedMode) || DFConstant.FLAG_YES.equalsIgnoreCase(hsmAutherizedMode))
        {
            IsAuthModeOn = true;            
            
        }
                   
        return IsAuthModeOn;
    }
    
    public static RsaKeyType getRsaKeyType(String rsaKeyTypeId) throws DFUtilitiesException
    {
        RsaKeyType rsaKeyType=null;
        
        if(rsaKeyTypeId==null || rsaKeyTypeId.trim().length()<1)
        {
            throw new DFUtilitiesException("RsaKeyType cannot be null or empty"  ,-1240);
        }
        rsaKeyTypeId=rsaKeyTypeId.trim();                
                
        if ((DFConstant.KEY_RSA_CRT.equalsIgnoreCase(rsaKeyTypeId)) || (DFConstant.KEY_CRT.equalsIgnoreCase(rsaKeyTypeId)))
        {
            rsaKeyType = RsaKeyType.CRT;
        }
        else if ((DFConstant.KEY_RSA_MODEXP.equalsIgnoreCase(rsaKeyTypeId)) || (DFConstant.KEY_MODEXP.equalsIgnoreCase(rsaKeyTypeId)))           
        {
            rsaKeyType = RsaKeyType.MODULUS_EXPONENT;
        }
        
        else
        {
            throw new DFUtilitiesException( "Invalid rsaKeyType=" + rsaKeyTypeId + ". Allowed values are 'CRT' and 'RSA_MODEXP'",-1240);
        }            
        return rsaKeyType;
    }
    
    public static PadMode getPadMode(String padModeType,int lengthBytes) throws DFUtilitiesException
    {
        PadMode padMode;
        if(padModeType==null || padModeType.trim().length()<1)
        {
            padModeType=DFConstant.PAD_00;
        }
        
        if(DFConstant.PAD_80.equalsIgnoreCase(padModeType.trim()))
        {
            switch (lengthBytes)
            {
            case 0:
                padMode = PadMode.L0_80;
                break;
            case 1:
                padMode = PadMode.L1_80;
                break;
            case 2:
                padMode = PadMode.L2_80;
                break;
            default:
                throw new DFUtilitiesException("Invalid lengthBytes: " + lengthBytes,-1212);
            }
        } 
        else                    
        {
            switch (lengthBytes)
            {
            case 0:
                padMode = PadMode.L0_00;
                break;
            case 1:
                padMode = PadMode.L1_00;
                break;
            case 2:
                padMode = PadMode.L2_00;
                break;
            default:
                throw new DFUtilitiesException("Invalid lengthBytes: " + lengthBytes,-1212);
            }
        }
        return padMode;    
    }
    
    public static byte[] getValidPublicKeyExponent(byte[] publicKeyExponent) throws DFUtilitiesException
    {
        byte[] pkExp;
        
        if(publicKeyExponent==null)
        {
            throw new DFUtilitiesException("PublicKeyExponent cannot be null"  ,-1213);
        }

        if (ByteArrayUtilities.stringify_nospaces(publicKeyExponent).endsWith("3"))
        {
            pkExp = ByteArrayUtilities.byteify_nospaces("03");
        }
        else
        {
            pkExp = ByteArrayUtilities.byteify_nospaces("065537");
        }       
        
        return pkExp;    
    }
    
    public static CardScheme getCardScheme(String cardSchemeId) throws DFUtilitiesException
    {
        CardScheme cardScheme=null;
        
        if(cardSchemeId==null || cardSchemeId.trim().length()<1)
        {
            throw new DFUtilitiesException("CardScheme cannot be null or empty"  ,-1221);
        }
        cardSchemeId=cardSchemeId.trim();
        
        if (DFConstant.SCHEME_VSDC.equalsIgnoreCase(cardSchemeId))
        {
            cardScheme = CardScheme.VSDC;
        }
        else if (DFConstant.SCHEME_MASTER.equalsIgnoreCase(cardSchemeId))            
        {
            cardScheme = CardScheme.MasterCard;
        }
        else if (DFConstant.SCHEME_AMEX.equalsIgnoreCase(cardSchemeId))             
        {
            cardScheme = CardScheme.AmericanExpress;
        }
        else
        {
            throw new DFUtilitiesException("Invalid cardscheme=" + cardSchemeId + ". Allowed values are 'VSDC', 'MasterCard' and 'AmericanExpress'",-1211);
        }
        return cardScheme;
    }
    
    public static CipherMode getCipherMode(String cipherModeId) throws DFUtilitiesException
    {
        CipherMode cipherMode=null;
        
        if(cipherModeId==null || cipherModeId.trim().length()<1)
        {
            throw new DFUtilitiesException("CipherMode cannot be null or empty"  ,-1220);
        }
        cipherModeId=cipherModeId.trim();
                
        if (DFConstant.CIPHER_ECB.equalsIgnoreCase(cipherModeId))
        {
            cipherMode = CipherMode.ECB;
        }
        else if (DFConstant.CIPHER_CBC.equalsIgnoreCase(cipherModeId))            
        {
            cipherMode = CipherMode.CBC;
        }
        else
        {
            throw new DFUtilitiesException( "Invalid cipherMode=" + cipherModeId + ". Allowed values are 'CBC' and 'ECB'",-1210);
        }            
        return cipherMode;
    }
    
    public static boolean CanGenerateRSAKeyPair(String hsmAutherizedMode,String smType) throws DFUtilitiesException
    {
        boolean canGenerate=false;
        
        if(smType==null || smType.trim().length()<1)
        {
            return true;
        }        
        
        smType=smType.trim();
        
        if(!IsHSMOnAutherizedMode(hsmAutherizedMode) && DFConstant.SMTYPE_PS9000.equalsIgnoreCase(smType))
        {
            canGenerate = false;            
        }        
        else
        {
            canGenerate=true;
        }     
        
        return canGenerate;
    }  
    
    
    public static String getValidDataValueList(String dataValueList,String listSeperator) throws DFUtilitiesException
    {            
        if( dataValueList==null || dataValueList.trim().length()==0)
        {            
            throw new DFUtilitiesException("Data Value List is null or empty");
        } 
        
        if( listSeperator==null || listSeperator.trim().length()==0)
        {
             listSeperator=DFConstant.DEFAULT_DATA_LIST_SEPERATOR;
             logger.warn("List Seperator is not defined and setting to default :"+DFConstant.DEFAULT_DATA_LIST_SEPERATOR);
        }       
        
        String dataValueList2beValidated=dataValueList.trim();
        int firstIndex=dataValueList2beValidated.indexOf(listSeperator);
        int lastIndex=dataValueList2beValidated.lastIndexOf(listSeperator);
        
        String validDataValueList=dataValueList2beValidated;
        
        if(logger.isDebugEnabled())
        {
            logger.debug("First Index>"+firstIndex);
            logger.debug("Last Index >"+lastIndex);
            logger.debug("List length>"+validDataValueList.length());
        }
        boolean isAddPrefix=false;
        boolean isAddSuffix=false;
                
        if(firstIndex != 0)
        {           
           isAddPrefix=true;
        }        
        if(validDataValueList.length()>0 && lastIndex<(validDataValueList.length()-1))
        {           
           isAddSuffix=true;
        }
        
        if(isAddPrefix && isAddSuffix )
        {
            validDataValueList =listSeperator.concat(validDataValueList).concat(listSeperator);
        }
        else if(isAddPrefix && !isAddSuffix )
        {
            validDataValueList =listSeperator.concat(validDataValueList);
        } 
        else if(!isAddPrefix && isAddSuffix )
        {
            validDataValueList =validDataValueList.concat(listSeperator);
        }
                   
        return validDataValueList;
    }
    
    public static String convertValue2HexString(int index,String inputDataType,Object inputDataValue) throws DFUtilitiesException 
    {
        String hexStringDataValue=null;
        
        if(inputDataValue==null)
        {
            throw new DFUtilitiesException("Input data iValue_"+(index+1)+" cannot be null");
        }
        

        if(DFConstant.DATA_TYPE_BYTEARRAY.equalsIgnoreCase(inputDataType))
        {                    
            String byteInString="";

            if(inputDataValue instanceof byte[])
                byteInString=ByteArrayUtilities.stringify_nospaces((byte[])inputDataValue);
            else
                byteInString=String.valueOf(inputDataValue);                    

            // Check data is valid. Must be even number of digits.
            if(byteInString.length() %2 != 0)
            {                       
                throw new DFUtilitiesException("Input byte data iValue_"+(index+1)+" must consist of an even number of digits");
            }                    
            hexStringDataValue=byteInString;
        }
        else if(DFConstant.DATA_TYPE_STRING.equalsIgnoreCase(inputDataType))
        {
            hexStringDataValue=ByteArrayUtilities.convertStringToHex(String.valueOf(inputDataValue));
        } 
        else if(DFConstant.DATA_TYPE_NUMBER.equalsIgnoreCase(inputDataType) || DFConstant.DATA_TYPE_NUMERIC.equalsIgnoreCase(inputDataType) || DFConstant.DATA_TYPE_INT.equalsIgnoreCase(inputDataType))
        {
            if(ByteArrayUtilities.isNumber(String.valueOf(inputDataValue)))
            {
                hexStringDataValue=Integer.toHexString(Integer.parseInt(String.valueOf(inputDataValue)));
                
                if(hexStringDataValue.length() %2 != 0)
                {                       
                    hexStringDataValue="0"+hexStringDataValue;
                }                 
            }
            else
            {
               throw new DFUtilitiesException("Input data iValue_"+(index+1)+" must be numeric value");
            }            
        }
        else if(DFConstant.DATA_TYPE_HEX.equalsIgnoreCase(inputDataType))
        {
            String hexString=String.valueOf(inputDataValue);
            
            if(hexString.length() %2 != 0)
            {                       
                throw new DFUtilitiesException("Input hex data iValue_"+(index+1)+" must consist of an even number of digits");
            } 
            hexStringDataValue=hexString;
            
        }
        else
        {
            logger.debug("iValue_"+(index+1)+" has undefined data type and the value will be passed through");
            hexStringDataValue=String.valueOf(inputDataValue);
        }
        return hexStringDataValue;
    }

    public static Object getOutputDataValue(int index,String outputDataType,String inputDataValue,String outputDataTag) throws DFUtilitiesException 
    {
        Object objDataValue=null;
        
        String myTagLength="";   
        
        if(inputDataValue!=null && inputDataValue.length()/2>0)
        {   
            if(outputDataTag!=null && outputDataTag.trim().length()>0 )
            {
                String myLength=ByteArrayUtilities.getDERLength(inputDataValue.length()/2);
                // Check tag is valid. Must be even number of digits.
                if(outputDataTag.trim().length()%2 != 0)
                {
                    throw new DFUtilitiesException("Tag "+index+" data must consist of an even number of digits");
                }
                myTagLength=outputDataTag.trim().concat(myLength);
            }
        }

       objDataValue=convertHexString2Value(myTagLength,outputDataType,inputDataValue);

        return objDataValue;
    }
    
        
    public static Object convertHexString2Value(String myTagLength,String outputDataType,String inputDataValue) throws DFUtilitiesException
    {
        Object objDataValue=null;
        
        if(myTagLength==null)
        {
            myTagLength="";
        }
        else
        {
            myTagLength=myTagLength.trim();
        }
        
        if(inputDataValue==null)
        {
            inputDataValue="";
        }
        else
        {
            inputDataValue=inputDataValue.trim();
        }
        
        
        if(DFConstant.DATA_TYPE_BYTEARRAY.equalsIgnoreCase(outputDataType))
        {     
            validateHexString(inputDataValue);         
            objDataValue=ByteArrayUtilities.byteify(myTagLength.concat(inputDataValue));
        }
        else if(DFConstant.DATA_TYPE_STRING.equalsIgnoreCase(outputDataType))
        {
            validateHexString(inputDataValue);
            objDataValue=ByteArrayUtilities.convertHexToString(inputDataValue);
        }
        else if(DFConstant.DATA_TYPE_NUMBER.equalsIgnoreCase(outputDataType) || DFConstant.DATA_TYPE_NUMERIC.equalsIgnoreCase(outputDataType) || DFConstant.DATA_TYPE_INT.equalsIgnoreCase(outputDataType))
        {
            validateHexString(inputDataValue);
            objDataValue=ByteArrayUtilities.convertHexStringToInt(inputDataValue);
        }
        else if(DFConstant.DATA_TYPE_HEX.equalsIgnoreCase(outputDataType))
        {
            validateHexString(inputDataValue);
            objDataValue=myTagLength.concat(inputDataValue);
        }
        else
        {
            objDataValue=myTagLength.concat(inputDataValue);
        }

        return objDataValue;    
    }
    
    private static boolean validateHexString(final String inputDataValue) throws DFUtilitiesException
    {
        if(inputDataValue.trim().length()%2 != 0)
        {
            throw new DFUtilitiesException("Input data must consist of an even number of digits : "+inputDataValue.trim());
        }       
        
        return true;
    }

    public static String[] getSplittedValue(String valueList,int expectedValueCount,boolean is2Validate) throws DFUtilitiesException
    {    
        if(is2Validate)
        {
            if( valueList==null || valueList.trim().length()==0)
            {
                 throw new DFUtilitiesException("Data type list is null or empty");
            } 
        }

        String valueList2beSplitted=valueList.trim();
        int firstIndex=valueList2beSplitted.indexOf(DFConstant.DEFAULT_DATA_LIST_SEPERATOR);
        int lastIndex=valueList2beSplitted.lastIndexOf(DFConstant.DEFAULT_DATA_LIST_SEPERATOR);

        String[] splittedValues;
        if(firstIndex==0)
        {
           splittedValues =valueList2beSplitted.substring(1).split(DFConstant.DEFAULT_DATA_LIST_SEPERATOR);
        }
        else
        {
            splittedValues =valueList2beSplitted.split(DFConstant.DEFAULT_DATA_LIST_SEPERATOR);
        }            
        return splittedValues;
    }
    /*
    public static SecurityData extractSecurityData(SecurityDataSet dataSet, String name) throws DFUtilitiesException 
    {
      if(dataSet==null)
      {
          throw new DFUtilitiesException("Security Data Set is null ");
      } 
      
      if(name==null || name.trim().length()<1)
      {
          throw new DFUtilitiesException("Security Data parameter name is null or empty");
      } 
        
      // retrieve the security data
      SecurityData secData = dataSet.getSecurityData(name);
      
      if(secData==null)
      {
          throw new DFUtilitiesException("Security Data cannot be retrived for parameter "+name);
      }
      // check if the security data is a BlobSecurityData
      
      return secData;
      
    }
    */
    /**
     * extract a BlobSecurityData security data specified by the name parameter from the security data set
     */
    /*
    public static String extractSecurityDataAsString(SecurityDataSet dataSet, String name) throws DFUtilitiesException 
    {
      if(dataSet==null)
      {
          throw new DFUtilitiesException("Security Data Set is null ");
      } 
      
      if(name==null || name.trim().length()<1)
      {
          throw new DFUtilitiesException("Security Data parameter name is null or empty");
      } 
        
      // retrieve the security data
      SecurityData secData = dataSet.getSecurityData(name);
      
      if(secData==null)
      {
          throw new DFUtilitiesException("Security Data cannot be retrived for parameter "+name);
      }
      // check if the security data is a BlobSecurityData
      if(secData instanceof BlobSecurityData)
      {
          return ((BlobSecurityData)secData).getStringifyedValue();
      }
      else if(secData instanceof KeySecurityData)
      {          
          return ByteArrayUtilities.stringify_nospaces(((KeySecurityData)secData).getKey().getKeyValue());
      }
      else if(secData instanceof DataSet)
      {
          throw new DFUtilitiesException("Security Data cannot be extracted from 'DataSet' objects");
      }
      else
      {
        throw new DFUtilitiesException("Security data called " + name + " supposed to be a binary and is a " + dataSet.getClass().getName() + " instead");
      }
      // return it
      
    }
    */
    public static boolean IsMuxSwicthMatchesData(String muxSwitch,String muxData,String muxOperator, boolean isInputSwitch) throws DFUtilitiesException
    {
        
        
        if(muxData==null || (isInputSwitch && muxData.trim().length()<1))
        {
            return false;
        }  
        
        if(muxSwitch==null || (isInputSwitch && muxSwitch.trim().length()<1))
        {
            return false;
        } 
        
        if(muxData.equalsIgnoreCase(muxSwitch))
        {
            return true;  
        } 
        else
        {
            return false;
        }
        
    }
    
    private static String ZEROS_16BYTES="00000000000000000000000000000000";
    
    public static String getPaddedZerosAsPrefix(String hexValue, int noOfByte) throws DFUtilitiesException
    {        
        if(hexValue==null)
        {
            throw new DFUtilitiesException("Input Value cannot be null");
        }        
        if(hexValue.length() %2 != 0)
        {                       
            throw new DFUtilitiesException("Input Value must consist of an even number of digits");
        }   
                
        String padded = ZEROS_16BYTES + hexValue;
        return padded.substring(padded.length() - noOfByte*2, padded.length());
    }
    
    
    public static String doOperation(int index,String dataOpeartion,String inputDataValue) throws DFUtilitiesException 
    {
        String dataOpeartedValue=null;
        
        if(inputDataValue==null)
        {
            throw new DFUtilitiesException("Input data iValue_"+(index+1)+" cannot be null");
        }       

        if(DFConstant.DATA_OPERATION_GETLENGTH.equalsIgnoreCase(dataOpeartion) || DFConstant.DATA_OPERATION_LENGTH.equalsIgnoreCase(dataOpeartion))
        {      
            dataOpeartedValue=convertValue2HexString(1,DFConstant.DATA_TYPE_NUMERIC,String.valueOf(inputDataValue.length()/2));
        }   
        else
        {
            //logger.debug("iValue_"+(index+1)+" has undefined data type and the value will be passed through");
            dataOpeartedValue=inputDataValue;
        }
        return dataOpeartedValue;
    }
    
    public static String doFormation(int index,String dataOpeartion,String inputDataValue) throws DFUtilitiesException 
    {
        String dataOpeartedValue=null;
        
        if(inputDataValue==null)
        {
            throw new DFUtilitiesException("Input data iValue_"+(index+1)+" cannot be null");
        }       

        if(DFConstant.DATA_FORM_LITTLEENDIAN.equalsIgnoreCase(dataOpeartion))
        {      
            dataOpeartedValue=swapEndian(inputDataValue);
        }   
        else
        {
            //logger.debug("iValue_"+(index+1)+" has undefined data type and the value will be passed through");
            dataOpeartedValue=inputDataValue;
        }
        return dataOpeartedValue;
    }
    
    public static String swapEndian(final byte[] big)
    {
        return ByteArrayUtilities.stringify_nospaces(swapEndianAsByte(big));
    }

    public static String swapEndian(final String big)
    {
        return swapEndian(ByteArrayUtilities.byteify_nospaces(big));            
    }
    
    public static byte[] swapEndianAsByte(final byte[] big)
    {
        final byte[] little = new byte[big.length];
        for (int i = 0; i < little.length; i++)
        {
                little[(big.length-1)-i] = big[i];
        }
        return little;
    }

    public static byte[] swapEndianAsByte(final String big)
    {
        return swapEndianAsByte(ByteArrayUtilities.byteify_nospaces(big));            
    }
    
}
