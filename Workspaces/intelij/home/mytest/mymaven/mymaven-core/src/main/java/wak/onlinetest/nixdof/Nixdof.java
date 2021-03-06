/*
 * http://stackoverflow.com/questions/113511/hash-code-implementation
 * 
 */
package wak.onlinetest.nixdof;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author wakkir.muzammil
 */
public class Nixdof
{    
    private int sum(int lastNumber)
    {
        int returnValue=1;
        if(lastNumber >2)
        {
            returnValue=lastNumber+sum(lastNumber-1)+sum(lastNumber-2);
        }
        return returnValue;
    }
    void test2()
    {
        System.out.println("------------- test2 -----------------");
        System.out.println("sum(4) : " + sum(4));
    }
    
    void test3()
    {
        System.out.println("------------- test3 -----------------");
        
        boolean isFinished1=true;
        if(isFinished1=true)
        {
            System.out.println("finished1");
        }        
        if(isFinished1=false)
        {
            System.out.println("not finished1");
        }
        boolean isFinished2=false;
        if(isFinished2=true)
        {
            System.out.println("finished2");
        }        
        if(isFinished2=false)
        {
            System.out.println("not finished2");
        }
    
    }
    
    void test4()
    {
        System.out.println("------------- test4 :: Exceptions -----------------");
        //Exceptions
    }
    void test5()
    {
        //immutable class
    }
    void test6()
    {
        System.out.println("------------- test6 -----------------");
        double d=-27.2345;
        System.out.println(Math.ceil(d));
        System.out.println(Math.round(d));
        System.out.println(Math.abs(d));
        System.out.println(Math.floor(d));
    
    }
    void test7()
    {
        System.out.println("------------- test7 :: overridding -----------------");
        //overridding
    }
    public enum Element
    {
        HELIUM("He","Gas"),
        MAGNESIUM("Mg","Solid");
        
        private String chemichalSymbol;
        private String nature;

        private Element(String chemichalSymbol, String nature)
        {
            this.chemichalSymbol = chemichalSymbol;
            this.nature = nature;
        }

        public String getChemichalSymbol()
        {
            return chemichalSymbol;
        }

        public String getNature()
        {
            return nature;
        }
        
        
    }
    void test8()
    {
        System.out.println("------------- test8 -----------------");
        
        System.out.printf("%s,%s,%s",Element.HELIUM,Element.HELIUM.getChemichalSymbol(),Element.HELIUM.getNature());
        System.out.println("");
        //System.out.printf("%s,%s,%s",Element[0],Element[0].chemichalSymbol(),Element[0].nature());
        System.out.println(Element.HELIUM);
        //System.out.printf("%s,%s,%s",Element[1],Element[1].chemichalSymbol(),Element[1].nature());
        System.out.printf("%s,%s,%s",Element.HELIUM.toString(),Element.HELIUM.getChemichalSymbol(),Element.HELIUM.getNature());
        System.out.println("");
        
        
    }
    void test9()
    {
        System.out.println("------------- test9 :: file handling & closing -----------------");
        //file handling & closing
    }
    void test10()
    {
        System.out.println("------------- test10 -----------------");
        
        System.out.println("abc"=="abc");
        System.out.println("abc".equals("abc"));
        System.out.println(new String("abc")=="abc");
        System.out.println(new String("abc")==new String("abc"));
        System.out.println(new String()==new String());
    }
    
    void test11()
    {
        System.out.println("------------- test11 :: abstract and interface -----------------");
        //abstract and interface
    }
    
    void test12()
    {
        System.out.println("------------- test12 -----------------");
        
        int i=2; int j=0;
        j= (i++ + i++)*--i;
        System.out.println("j : "+j);
    }
    
    void test15()
    {
        System.out.println("------------- test15 -----------------");
        //Not thread safe
        Set<String> set=new LinkedHashSet<String>();//return sequencial '3-1-2-' set with unique data
        //Set<String> set=new HashSet<String>();//return unordered set with unique data
        //Set<String> set=new TreeSet<String>(); //return ordered '1-2-3-' set with unique data
        
        //Thread safe
        //Set<String> set=Collections.synchronizedSet(new LinkedHashSet<String>());
        /*  * @see Collection
            * @see List
            * @see SortedSet
            * @see HashSet
            * @see TreeSet
            * @see AbstractSet
            * @see Collections#singleton(java.lang.Object)
            * @see Collections#EMPTY_SET
        */
        
        
        set.add("3");        
        set.add("1");
        set.add("3");
        set.add("2");
        set.add("3");
        set.add("1");
        
        for(Iterator<String> it=set.iterator();it.hasNext();)
        {
            String str=(String)it.next();
            System.out.print(str+"-");
        }
    
    }
    
     
    public static void main(String[] args)
    {

       Nixdof x=new Nixdof();
       //x.test2();
       x.test2();
       x.test3();
       x.test4();
       x.test5();
       x.test6();
       x.test7();
       x.test8();
       x.test9();
       x.test10();
       x.test11();
       x.test12();
       x.test15();
       
       
       System.out.println("\nEnded...");
        

    }
    
}
