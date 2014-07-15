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
    private final int threadCount=100000;
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
        
    }
    
    @AfterClass
    public static void tearDownClass()
    {        
        
    }
    
    @Before
    public void setUp()
    {        
    }
    
    @After
    public void tearDown()
    {           
        
    }
       
    
    @Test
    public final void whenPerformAccessCheckForFirstExistingBookThenGrandAccess() throws AccessControlCoordinatorException, InterruptedException, ExecutionException
    {   
        acco=new AccessControlCoordinator(new AccessControlDecisionMakerImpl(bookNameExist),new AccessControlDecisionMakerImpl(bookNameNotExist));
        boolean result=acco.performAccessCheckForBook(bookNameExist);
        Assert.assertFalse(result);        
    }
    
    @Test
    public final void whenPerformAccessCheckForSecondExistingBookThenGrandAccess() throws AccessControlCoordinatorException, InterruptedException, ExecutionException
    {   
        acco=new AccessControlCoordinator(new AccessControlDecisionMakerImpl(bookNameNotExist),new AccessControlDecisionMakerImpl(bookNameExist));
        boolean result=acco.performAccessCheckForBook(bookNameExist);
        Assert.assertFalse(result);        
    }
    
    @Test
    public final void whenPerformAccessCheckForBothExistingBookThenGrandAccess() throws AccessControlCoordinatorException, InterruptedException, ExecutionException
    {   
        acco=new AccessControlCoordinator(new AccessControlDecisionMakerImpl(bookNameExist),new AccessControlDecisionMakerImpl(bookNameExist));
        boolean result=acco.performAccessCheckForBook(bookNameExist);
        Assert.assertTrue(result);        
    }
    
    @Test
    public final void whenPerformAccessCheckForNoneExistingBookThenDenyAccess() throws AccessControlCoordinatorException, InterruptedException, ExecutionException
    {   
        acco=new AccessControlCoordinator(new AccessControlDecisionMakerImpl(bookNameNotExist),new AccessControlDecisionMakerImpl(bookNameNotExist));
        boolean result=acco.performAccessCheckForBook(bookNameExist);
        Assert.assertFalse(result);        
    }
    
    @Test(expected = AccessControlCoordinatorException.class)
    public final void whenPerformAccessCheckForNullThenThrowsException() throws AccessControlCoordinatorException
    {        
        acco=new AccessControlCoordinator(new AccessControlDecisionMakerImpl(bookNameExist),new AccessControlDecisionMakerImpl(bookNameExist));
        acco.performAccessCheckForBook(bookNameNull);       
    }
    //////////////////////////////
    
    @Test
    public final void whenPerformMultiAccessCheckForFirstExistingBookThenGrandAccess() throws AccessControlCoordinatorException, InterruptedException, ExecutionException
    {   
        acco=new AccessControlCoordinator(new AccessControlDecisionMakerImpl(bookNameExist),new AccessControlDecisionMakerImpl(bookNameNotExist));
        
        List<Boolean> expectedList = new ArrayList<Boolean>(threadCount);
        for (long i = 1; i <= threadCount; i++)
        {
            expectedList.add(false);
        }        
        List<Boolean> resultList=doThread(acco,bookNameExist);
        System.out.println("expectedList:"+expectedList);
        System.out.println("resultList:"+resultList);
        Assert.assertEquals(expectedList, resultList);    
    }
    
    @Test
    public final void whenPerformMultiAccessCheckForSecondExistingBookThenGrandAccess() throws AccessControlCoordinatorException, InterruptedException, ExecutionException
    {   
        acco=new AccessControlCoordinator(new AccessControlDecisionMakerImpl(bookNameNotExist),new AccessControlDecisionMakerImpl(bookNameExist));
        List<Boolean> expectedList = new ArrayList<Boolean>(threadCount);
        for (long i = 1; i <= threadCount; i++)
        {
            expectedList.add(false);
        }        
        List<Boolean> resultList=doThread(acco,bookNameExist);
        System.out.println("expectedList:"+expectedList);
        System.out.println("resultList:"+resultList);
        Assert.assertEquals(expectedList, resultList);        
    }
    
    @Test
    public final void whenPerformMultiAccessCheckForBothExistingBookThenGrandAccess() throws AccessControlCoordinatorException, InterruptedException, ExecutionException
    {   
        acco=new AccessControlCoordinator(new AccessControlDecisionMakerImpl(bookNameExist),new AccessControlDecisionMakerImpl(bookNameExist));
        List<Boolean> expectedList = new ArrayList<Boolean>(threadCount);
        for (long i = 1; i <= threadCount; i++)
        {
            expectedList.add(true);
        }        
        List<Boolean> resultList=doThread(acco,bookNameExist);
        System.out.println("expectedList:"+expectedList);
        System.out.println("resultList:"+resultList);
        Assert.assertEquals(expectedList, resultList);           
    }
    
    @Test
    public final void whenPerformMultiAccessCheckForNoneExistingBookThenDenyAccess() throws AccessControlCoordinatorException, InterruptedException, ExecutionException
    {   
        acco=new AccessControlCoordinator(new AccessControlDecisionMakerImpl(bookNameNotExist),new AccessControlDecisionMakerImpl(bookNameNotExist));
        List<Boolean> expectedList = new ArrayList<Boolean>(threadCount);
        for (long i = 1; i <= threadCount; i++)
        {
            expectedList.add(false);
        }        
        List<Boolean> resultList=doThread(acco,bookNameExist);
        System.out.println("expectedList:"+expectedList);
        System.out.println("resultList:"+resultList);
        Assert.assertEquals(expectedList, resultList);            
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
        Assert.assertEquals(threadCount, futures.size());
        Collections.sort(resultList);
        return resultList;        
        
    }
}
