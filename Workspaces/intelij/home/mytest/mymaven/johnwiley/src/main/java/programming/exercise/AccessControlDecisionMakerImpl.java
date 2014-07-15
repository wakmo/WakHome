/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package programming.exercise;

/**
 *
 * @author wakkir.muzammil
 */
public class AccessControlDecisionMakerImpl implements AccessControlDecisionMaker
{

    @Override
    public boolean performAccessCheck(String book)
    {
        if("book".equals(book))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
}
