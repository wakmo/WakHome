/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.sort;

import java.util.Arrays;
import java.util.Comparator;




/**
 *
 * @author wakkir.muzammil
 */
public class FindTopValuesImpl1 implements FindTopValues1
{
        
    @Override
    public int findMaxValue(int[] anyOldOrderValues)
    {
        Arrays.sort(anyOldOrderValues);//, Collections.reverseOrder()
        return anyOldOrderValues[anyOldOrderValues.length-1];
    }

    @Override
    public int[] findTopNValues(int[] anyOldOrderValues, int n)
    {  
        Arrays.sort(anyOldOrderValues);//, Collections.reverseOrder()
        
        return Arrays.copyOfRange(anyOldOrderValues,anyOldOrderValues.length-n,anyOldOrderValues.length);
                
    }
    
    public int findMaxValue(Integer[] anyOldOrderValues)
    {
        Arrays.sort(anyOldOrderValues, new ReverseComparator());
        for(int i=0;i<anyOldOrderValues.length;i++)
        {
            System.out.printf("%s,%s", anyOldOrderValues[i],"");
        }
        return anyOldOrderValues[0];
    }
    
    public int[] findTopNValues1(int[] anyOldOrderValues, int n)
    {  
       Arrays.sort(anyOldOrderValues);
       
       int[] xx=Arrays.copyOfRange(anyOldOrderValues,anyOldOrderValues.length-n,anyOldOrderValues.length);
        
       return xx;
                
    }
    
    
   
    
    public static void main(String[] args) 
    {
       FindTopValuesImpl1 x=new FindTopValuesImpl1();
       Integer[] anyOldOrderValues = {120, 5, 0, 999, 77,-999,55,34,-2,65,2,-1};
       
       //x.findMaxValue(anyOldOrderValues, 5);
       //x.findTopNValuesGuava(anyOldOrderValues, 5);
        
       //Arrays.sort(anyOldOrderValues);
       //System.out.println(Arrays.toString(anyOldOrderValues));
       
       System.out.println(x.findMaxValue(anyOldOrderValues)); 
       
        
       //System.out.println(Arrays.toString(x.findTopNValues(anyOldOrderValues, 5)));
    }


    
}
class ReverseComparator<T> implements Comparator<Comparable<Object>>
{

    @Override
    public int compare(Comparable<Object> c1, Comparable<Object> c2)
    {        
        return c2.compareTo(c1);           
    }
    
}

class ReverseComparatorStr<T> implements Comparator<String>
{

    @Override
    public int compare(String c1, String c2)
    {        
        return c2.compareTo(c1);           
    }
    
}

class ReverseComparatorInt<T> implements Comparator<Integer>
{

    @Override
    public int compare(Integer c1, Integer c2)
    {        
        return c2.compareTo(c1);           
    }
    
}


