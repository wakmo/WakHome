/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wak.onlinetest.closebrother;

/**
 *
 * @author wakkir
 */
public class CloseBrother1 
{    
   
    void test1()
    {
        System.out.println("------------- test1 -----------------");
        
    }
    void test6()
    {
        System.out.println("------------- test6 -----------------");
        double d=25.45555;
        System.out.println(Math.floor(d));
        System.out.println(Math.ceil(d));
        System.out.println(Math.abs(d));
        System.out.println(Math.round(d));
        
        
    
    }
     
    public static void main(String[] args)
    {

       CloseBrother1 x=new CloseBrother1();
       //x.test6();
      // boolean b =((false?false:true)?false:true)?false:true;
       //System.out.println(method(b));
       //System.out.println("\nCloseBrother1 Ended...");
       
       //A a =new A();
       //a.m1();
       //a.m2();
       //a.m3();
       //a.m4();
       /*
       String str1="hello";
       String str2=new String("hello");
       
       if(str1.hashCode()==str2.hashCode())
       {
       System.out.println("trueA");
       };
       
       if(str1.toString().equals(str2.toString()))
       {
       System.out.println("trueB");
       };
       
       if(str1==str2)
       {
       System.out.println("trueD");
       };
       
       if(str1.toString()==str2.toString())
       {
       System.out.println("truE");
       };
       * */
       //x.method();
       /*
       int i=0, j=0;
       cnt: 
       for(i=0;i<10;i++)
        {
            for(j=0;j<5;j++)
            {
                continue cnt;
            }
        }
       System.out.println("i="+i+", j="+j);
        */
       call(call(~1)+call(6^14)+call(1<<2));

    }
    
    static int call(int i)
    {
    System.out.print(i+" ");
    return i;
    }
    
    public void method()
    {
    int x=3;
    boolean b1=true;
    boolean b2=false;
    if((b1|b2)||(x++>4))
        System.out.println("x1 "+x++);
    if((!b1 & b2)&&(x++<4))
        System.out.println("x2 "+x);
    }
    
   static String method(boolean b)
   {
    return b?"True":"False";
   }
    
}


class A 
{
    
    public void m1()
    {
        System.out.println("A.m1, ");
    }
    protected void m2()
    {
        System.out.println("A.m2, ");
    }
    
    private void m3()
    {
        System.out.println("A.m3, ");
    }
    
    void m4()
    {
        System.out.println("A.m4, ");
    }
    
    private void xx()
    {
        System.out.println("A.m4, ");
    }
}


interface X
{
 void m1();
}

class D implements X
{

    @Override
    public void m1()
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
//private void m1(){};
//protected void m1(){};
//static void m1(){};
}

