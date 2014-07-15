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
    private final String bookNameInStore;
    
    public AccessControlDecisionMakerImpl(String bookName)
    {
        this.bookNameInStore=bookName;
    }

    @Override
    public boolean performAccessCheck(String book)
    {
        return bookNameInStore.equals(book);
    }
    
}
