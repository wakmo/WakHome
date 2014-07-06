/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.test.technologyconversations.tddbestpractices;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author wakkir.muzammil
 */
public class TestApp1
{

    @Test
    public void testPrintHelloWorld()
    {

        Assert.assertEquals(App.getHelloWorld(), "Hello World");

    }
}
