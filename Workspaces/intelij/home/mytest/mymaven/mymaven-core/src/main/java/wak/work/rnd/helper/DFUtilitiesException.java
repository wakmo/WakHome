/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.rnd.helper;

/**
 * @author wakkir.muzammil
 */
public class DFUtilitiesException extends Exception
{
    private static final long serialVersionUID = 4233390499647614871L;
    private int errorCode=0; 

    public DFUtilitiesException()
    {
        super();
    }

    public DFUtilitiesException(String message)
    {
        super(message);
    }
    
    
    
    public DFUtilitiesException(String message,int errorCode)
    {
        super(message);
        this.errorCode=errorCode;
    }

    public DFUtilitiesException(String message, Throwable cause)
    {
        super(message, cause);
    }
    
    public DFUtilitiesException(String message, Throwable cause,int errorCode)
    {
        super(message, cause);
        this.errorCode=errorCode;
    }

    public DFUtilitiesException(Throwable cause)
    {
        super(cause);
    }

    public int getErrorCode()
    {
        return errorCode;
    }    
    

}
