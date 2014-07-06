/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.test.technologyconversations.tddbestpractices;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author wakkir.muzammil
 */
public class TDD
{
    private static final Logger logger = LoggerFactory.getLogger(TDD.class.getName());

    
    public TDD()
    {
        logger.info("Calling TDDTest ...");
    }
    
    @BeforeClass
    public static void setUpClass()
    {
        logger.info("Calling setUpClass ...");
    }
    
    @AfterClass
    public static void tearDownClass()
    {
        logger.info("Calling tearDownClass ...");
    }
    
    @Before
    public void setUp()
    {
        logger.info("Calling setUp ...");
    }
    
    @After
    public void tearDown()
    {
        logger.info("Calling tearDown ...");
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
