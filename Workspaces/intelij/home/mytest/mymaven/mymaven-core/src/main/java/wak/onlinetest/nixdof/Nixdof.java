/*
 * http://stackoverflow.com/questions/113511/hash-code-implementation
 * 
 */
package wak.onlinetest.nixdof;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

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
        System.out.println("sum(4) : " + sum(4));
    }
    
    void test3()
    {
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
        //Exceptions
    }
    void test5()
    {
        //immutable class
    }
    void test6()
    {
        double d=-27.2345;
        System.out.println(Math.ceil(d));
        System.out.println(Math.round(d));
        System.out.println(Math.abs(d));
        System.out.println(Math.floor(d));
    
    }
    void test7()
    {
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
        System.out.printf("%s,%s,%s",Element.HELIUM,Element.HELIUM.getChemichalSymbol(),Element.HELIUM.getNature());
        System.out.println("");
        //System.out.printf("%s,%s,%s",Element[0],Element[0].chemichalSymbol(),Element[0].nature());
        System.out.println(Element.HELIUM);
        //System.out.printf("%s,%s,%s",Element[1],Element[1].chemichalSymbol(),Element[1].nature());
         System.out.printf("%s,%s,%s",Element.HELIUM.toString(),Element.HELIUM.getChemichalSymbol(),Element.HELIUM.getNature());
        
        
        
    }
    void test9()
    {
        //file handling & closing
    }
    void test10()
    {
        System.out.println("abc"=="abc");
        System.out.println("abc".equals("abc"));
        System.out.println(new String("abc")=="abc");
        System.out.println(new String("abc")==new String("abc"));
        System.out.println(new String()==new String());
    }
    
    void test11()
    {
        //abstract and interface
    }
    
    void test12()
    {
        int i=2; int j=0;
        j= (i++ + i++)*--i;
        System.out.println("j : "+j);
    }
    
    void test15()
    {
        
        Set<String> set=new LinkedHashSet<String>();
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
       x.test12();
       System.out.println("\nEnded...");
        

    }
    
}
