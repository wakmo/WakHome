/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author wakkir.muzammil
 */
public class WakSort
{
    public WakObj findMaxValueWak(List<WakObj> anyOldOrderValues)
    {
        //Collections.reverse(anyOldOrderValues);
        //Collections.reverseOrder(new ReverseComparatorWak());
        Collections.sort(anyOldOrderValues, new ReverseComparatorWak());
        for(int i=0;i<anyOldOrderValues.size();i++)
        {
            System.out.printf("%s,%s", anyOldOrderValues.get(i),"");
        }
        return anyOldOrderValues.get(0);
    }
    
    
    public List<WakObj> findTopNValues(List<WakObj> anyOldOrderValues, int n)
    {  
        Collections.sort(anyOldOrderValues, new ReverseComparatorWak());
        
        return anyOldOrderValues.subList(0, n);
                
    }
    
    public static void main(String[] args) 
    {
       WakSort x=new WakSort();
                   
       List wakList=new ArrayList();
       wakList.add(new WakObj(120,"120"));
       wakList.add(new WakObj(5,"5"));
       wakList.add(new WakObj(0,"0"));
       wakList.add(new WakObj(999,"999"));
       wakList.add(new WakObj(77,"77"));
       wakList.add(new WakObj(-999,"-999"));
       wakList.add(new WakObj(55,"55"));
       wakList.add(new WakObj(34,"34"));
       wakList.add(new WakObj(-2,"-2"));
       wakList.add(new WakObj(5,"5"));
       wakList.add(new WakObj(65,"65"));
       wakList.add(new WakObj(2,"2"));
       wakList.add(new WakObj(-1,"-1"));
       WakObj zz= x.findMaxValueWak(wakList);
       
       System.out.printf("\n%s\n",zz);   
       
       List<WakObj> ll= x.findTopNValues(wakList,5);
       Iterator<WakObj> i=ll.iterator();
       while(i.hasNext())
       {
           System.out.printf("%s,",i.next());   
       }
       System.out.println();
       
    }
}


//#######################################################

class ReverseComparatorWak<T> implements Comparator<WakObj>
{

    @Override
    public int compare(WakObj c1, WakObj c2)
    {        
        return c2.compareTo(c1);           
    }
    
}

final class WakObj implements Comparable<WakObj>
{
   private final int id;
   private final String name;
   
   WakObj(int id,String name)
   {
       this.id=id;
       this.name=name;
   }

    public int compareTo(WakObj wakObj)
    {
        int thisVal = this.id;
	int wakObjVal = wakObj.id;
	return (thisVal<wakObjVal ? -1 : (thisVal==wakObjVal ? 0 : 1));
    }
    
    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 97 * hash + this.id;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
   
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final WakObj other = (WakObj) obj;
        if (this.id != other.id)
        {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
        {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString()
    {
        //return "WakObj{" + "id=" + id + ", name=" + name + '}';
        return id+"";
    }

    

}
