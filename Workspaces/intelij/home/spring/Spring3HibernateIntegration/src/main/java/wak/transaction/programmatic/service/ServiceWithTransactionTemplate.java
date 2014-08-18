/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wak.transaction.programmatic.service;

import java.sql.ResultSet;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

/**
 *
 * @author wakkir
 * 
 * Using the TransactionTemplate
 * 
 */
public class ServiceWithTransactionTemplate 
{
    
    // single TransactionTemplate shared amongst all methods in this instance
    private final TransactionTemplate transactionTemplate;

    // use constructor-injection to supply the PlatformTransactionManager
    public ServiceWithTransactionTemplate(PlatformTransactionManager transactionManager) 
    {
        Assert.notNull(transactionManager, "The transactionManager argument must not be null.");
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        
        // the transaction settings can be set here explicitly if so desired
        this.transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_UNCOMMITTED);
        this.transactionTemplate.setTimeout(30); // 30 seconds
        // and so forth...
    }

    public ResultSet someServiceMethod() 
    {
        return transactionTemplate.execute(new TransactionCallback<ResultSet>() 
        {
            // the code in this method executes in a transactional context
            public ResultSet doInTransaction(TransactionStatus status) 
            {
                //getListOperation1();
                //return resultOfSelectOperation2();
                throw new UnsupportedOperationException("Not supported yet."); 
            }
        });
    }
    
    public void someServiceMethodWithoutResult() 
    {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() 
        {
           // the code in this method executes in a transactional context
            protected void doInTransactionWithoutResult(TransactionStatus status) 
            {
                //try 
                {
                //updateOperation1();
                //updateOperation2();
                } 
                //catch (SomeBusinessExeption ex) 
                {
                    status.setRollbackOnly();
                } 
            }
            
        });
    }
    
}
