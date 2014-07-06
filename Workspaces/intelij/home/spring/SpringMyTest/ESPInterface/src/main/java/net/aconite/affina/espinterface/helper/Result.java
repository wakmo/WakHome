/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.aconite.affina.espinterface.helper;

/**
 *
 * @author thushara.pethiyagoda
 */
public class Result
{
    private boolean isSuccessFul;
    private Exception exception;
    private String msg;
    
    /**
     * 
     * @param success
     * @param ex
     * @param additionalMsg 
     */
    private Result(boolean success, Exception ex, String friendlyMessage)
    {
        isSuccessFul = success;
        exception = ex;
        msg = friendlyMessage;
    }
    
    public static Result getInstance(boolean success, Exception ex, String friendlyMessage)
    {
        return new Result(success, ex, friendlyMessage);
    }
    
    public boolean isSuccessFul()
    {
        return isSuccessFul;
    }
    /**
     * Can be null.
     * @return Exception if any related to this Result.
     */
    public Exception getException()
    {
        return exception;
    }
    
    public String getFriendlyMessage()
    {
        return  msg == null ? "" : msg;
    }
         
    public String getExceptionMessage()
    {
        return getException() != null ? getException().getMessage() : "";
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(getFriendlyMessage());
        if(getException() != null)
        {
            sb.append(getException().getMessage());
        }
        return sb.toString();
    }
            
}
