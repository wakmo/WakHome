/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.Arrays;
import java.util.Collections;


/**
  * Java Program to find Max value and find Top N Values for given list of data.
 * 
  * @author wakkir.muzammil
 */
public class FindTopValuesImpl implements FindTopValues
{
    /**
     * This method will return the max value  
     *
     * @param anyOldOrderValues is the array of integer data value to be sorted to get max value.
     * @return the maximum value of the given data.    
     */    
    @Override
    public int findMaxValue(Integer[] anyOldOrderValues)
    {
        Arrays.sort(anyOldOrderValues, Collections.reverseOrder());        
        return anyOldOrderValues[0];
    }
    
    /**
     * This method will return the top N values of data in descending order  
     *
     * @param anyOldOrderValues is the array of integer data value to be sorted to get max value.
     * @param n is defined the number of top N data to be returned.
     * @return top N number of data as Integer array.    
     */ 
    @Override
    public Integer[] findTopNValues(Integer[] anyOldOrderValues, int n)
    {  
        Arrays.sort(anyOldOrderValues, Collections.reverseOrder());
        return Arrays.copyOfRange(anyOldOrderValues, 0, n);
    }
        
}

