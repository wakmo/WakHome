/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wak.transaction.programmatic.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.Assert;

/**
 *
 * @author wakkir
 * 
 * Using the PlatformTransactionManager
 * 
 */
public class ServiceWithPlatformTransactionManager 
{
    private final PlatformTransactionManager transactionManager;

    // use constructor-injection to supply the PlatformTransactionManager
    public ServiceWithPlatformTransactionManager(PlatformTransactionManager transactionManager) 
    {
        Assert.notNull(transactionManager, "The transactionManager argument must not be null.");
        this.transactionManager=transactionManager;      
        
    }

    
    public void someServiceMethod() 
    {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        // explicitly setting the transaction name is something that can only be done programmatically
        def.setName("SomeTxName");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus status = transactionManager.getTransaction(def);
        //try 
        {
            // execute your business logic here
        }
        //catch (MyException ex) 
        {
            transactionManager.rollback(status);
            //throw ex;
        }
        transactionManager.commit(status);
    }
    
}
