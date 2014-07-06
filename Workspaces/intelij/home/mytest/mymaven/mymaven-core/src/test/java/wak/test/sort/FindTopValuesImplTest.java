/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.test.sort;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author wakkir.muzammil
 */
public class FindTopValuesImplTest
{    
    Integer[] anyOldOrderValues = {120, 5, 0, 999, 77,-999,55,34,-2,65,2,-1};
    
    Integer[] expectedMaxRangeValues = {999, 120, 77, 65, 55};    
    Integer[] anyOldOrderValuesEmpty = {};
    
    public FindTopValuesImplTest()
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
    public final void whenAnyOldOrderValuesUsedThenGetMaxValue()
    {        
        FindTopValues x=new FindTopValuesImpl();
        int maxvalue=x.findMaxValue(anyOldOrderValues);
        //Assert.assertEquals(999, maxvalue);
        assertThat(maxvalue,equalTo(999));
    }
    
    @Test
    public final void whenAnyOldOrderValuesUsedThenGetTopNValues()
    {        
        FindTopValues x=new FindTopValuesImpl();
        Integer[] maxvalues=x.findTopNValues(anyOldOrderValues, expectedMaxRangeValues.length);
        Assert.assertArrayEquals(expectedMaxRangeValues,maxvalues);
    }
    
    
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public final void whenEmptyValuesIsUsedThenExceptionIsThrown()
    {        
        FindTopValues x=new FindTopValuesImpl();
        int maxvalue=x.findMaxValue(anyOldOrderValuesEmpty);
    }
    
    @Test(expected = NullPointerException.class)
    public final void whenNullValuesIsUsedThenExceptionIsThrown()
    {        
        FindTopValues x=new FindTopValuesImpl();
        int maxvalue=x.findMaxValue(null);   
    }
    
    @Test(expected =IllegalArgumentException.class)
    public final void whenTopNIsNegativeThenExceptionIsThrown()
    {        
        FindTopValues x=new FindTopValuesImpl();
        Integer[] maxvalues=x.findTopNValues(anyOldOrderValues, -1);        
    }
    
    
}
