package programming.exercise;

public class AccessControlCoordinator
{

    AccessControlDecisionMaker accessControlDecisionMaker1;
    AccessControlDecisionMaker accessControlDecisionMaker2;

    public AccessControlCoordinator(final AccessControlDecisionMaker accessControlDecisionMaker1,
                                    final AccessControlDecisionMaker accessControlDecisionMaker2)
    {
        this.accessControlDecisionMaker1=accessControlDecisionMaker1;
        this.accessControlDecisionMaker2=accessControlDecisionMaker2;
    }

    public boolean performAccessCheckForBook(final String book) throws AccessControlCoordinatorException
    {
        if(book==null)
        {
            throw new AccessControlCoordinatorException("Book name cannot be null","-20202");
        }
        
        if (this.accessControlDecisionMaker1.performAccessCheck(book) && 
                this.accessControlDecisionMaker2.performAccessCheck(book))
        {
            return true;
        }
        else
        {
            return false;
        }
                
    }
    
    
}
