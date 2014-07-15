/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programming.exercise;

/**
 *
 * @author wakkir.muzammil
 */
public class AccessControlCoordinatorException extends Exception
{

    static final long serialVersionUID = -3387516993124229943L;
    private String errorCode;
/**
 * 
 */
    public AccessControlCoordinatorException()
    {
        super();
    }

    public AccessControlCoordinatorException(String message)
    {
        super(message);
    }

    public AccessControlCoordinatorException(String message, String errCode)
    {
        super(message);
        errorCode = errCode;
    }

    public String getErrorCode()
    {
        return errorCode;
    }
    
}