package programming.exercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccessControlCoordinatorTest
{
    private final int threadCount=10;
    private final String bookNameExist="book";  
    private final String bookNameNotExist="xyz";   
    private final String bookNameNull=null;
            
    AccessControlCoordinator acco;
    
    public AccessControlCoordinatorTest()
    {        
    }
    
    @BeforeClass
    public static void setUpClass()
    {    
        System.out.println("setUpClass");
    }
    
    @AfterClass
    public static void tearDownClass()
    {        
         System.out.println("tearDownClass");
    }
    
    @Before
    public void setUp()
    {        
        System.out.println("setUp");
        acco=new AccessControlCoordinator(new AccessControlDecisionMakerImpl(),new AccessControlDecisionMakerImpl());
    }
    
    @After
    public void tearDown()
    {    
        System.out.println("tearDown");
        acco=null;
    }
    
    @Test
    public final void whenPerformAccessCheckForExistingBookThenGrandAccess() throws AccessControlCoordinatorException, InterruptedException, ExecutionException
    {   
        boolean result=acco.performAccessCheckForBook(bookNameExist);
        Assert.assertTrue(result);        
    }
    
    @Test
    public final void whenPerformAccessCheckForNonExistingBookThenDenyAccess() throws AccessControlCoordinatorException, InterruptedException, ExecutionException
    {        
        //AccessControlDecisionMaker acdm1=new AccessControlDecisionMakerImpl();
        //AccessControlDecisionMaker acdm2=new AccessControlDecisionMakerImpl();
            
        //AccessControlCoordinator acco=new AccessControlCoordinator(acdm1,acdm2);
                
        boolean result=acco.performAccessCheckForBook(bookNameNotExist); 
        Assert.assertFalse(result);       
        
    }
    
    @Test(expected = AccessControlCoordinatorException.class)
    public final void whenPerformAccessCheckForNullThenThrowsException() throws AccessControlCoordinatorException
    {        
        //AccessControlDecisionMaker acdm1=new AccessControlDecisionMakerImpl();
        //AccessControlDecisionMaker acdm2=new AccessControlDecisionMakerImpl();
            
        //AccessControlCoordinator acco=new AccessControlCoordinator(acdm1,acdm2);
        
        acco.performAccessCheckForBook(bookNameNull);       
    }
    
    @Test
    public final void whenPerformMultiAccessCheckForExistingBookThenGrandAccess() throws AccessControlCoordinatorException, InterruptedException, ExecutionException
    {        
        //AccessControlDecisionMaker acdm1=new AccessControlDecisionMakerImpl();
        //AccessControlDecisionMaker acdm2=new AccessControlDecisionMakerImpl();
            
        //AccessControlCoordinator acco=new AccessControlCoordinator(acdm1,acdm2);
                
        //////////////
        List<Boolean> expectedList = new ArrayList<Boolean>(threadCount);
        for (long i = 1; i <= threadCount; i++)
        {
            expectedList.add(true);
        }        
        List<Boolean> resultList=doThread(acco,bookNameExist);
        System.out.println("expectedList:"+expectedList);
        System.out.println("resultList:"+resultList);
        Assert.assertEquals(expectedList, resultList);
        /////////////
    }
    
    @Test
    public final void whenPerformMultiAccessCheckForNonExistingBookThenDenyAccess() throws AccessControlCoordinatorException, InterruptedException, ExecutionException
    {        
        //AccessControlDecisionMaker acdm1=new AccessControlDecisionMakerImpl();
        //AccessControlDecisionMaker acdm2=new AccessControlDecisionMakerImpl();
            
        //AccessControlCoordinator acco=new AccessControlCoordinator(acdm1,acdm2);
                
        //////////////
        List<Boolean> expectedList = new ArrayList<Boolean>(threadCount);
        for (long i = 1; i <= threadCount; i++)
        {
            expectedList.add(false);
        }        
        List<Boolean> resultList=doThread(acco,bookNameNotExist);
        System.out.println("expectedList:"+expectedList);
        System.out.println("resultList:"+resultList);
        Assert.assertEquals(expectedList, resultList);
        /////////////
        
    }
    
    
    
    
    private List<Boolean> doThread(final AccessControlCoordinator acco, final String book) throws InterruptedException, ExecutionException
    {
        Callable<Boolean> task = new Callable<Boolean>()
        {
            @Override
            public Boolean call() throws AccessControlCoordinatorException
            {
                return acco.performAccessCheckForBook(book);                
            }
        };
        
        List<Callable<Boolean>> tasks = Collections.nCopies(threadCount, task);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Future<Boolean>> futures = executorService.invokeAll(tasks);
        List<Boolean> resultList = new ArrayList<Boolean>(futures.size());
        // Check for exceptions
        for (Future<Boolean> future : futures)
        {
            // Throws an exception if an exception was thrown by the task.
            resultList.add(future.get());
        }
        // Validate the IDs
        Assert.assertEquals(threadCount, futures.size());
        Collections.sort(resultList);
        return resultList;        
        
    }
}
