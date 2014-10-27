/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.javaoo;

/**
 *
 * @author wakkir.muzammil
 */
public class MyInheritance
{

    public static void main(String[] args)
    {

        SuperMulti m1 = new SubMulti1();
        System.out.println(m1.muliplyExtra(1, 2));
        
        
        SuperMulti m2 = new SubMulti2();
        System.out.println(m2.muliplyExtra(1, 2));
        
        
        SuperMulti m = new SubMulti2();
        m.printTypeName();
        System.out.println("," + m.index);
        System.out.println("," + m.getIndex());   
         
    }
}

class SuperMulti
{
    int index = 0;
    
    public int muliplyExtra(int a, int b)
    {
        return this.muliplyNumber(a, b);
    }

    public int muliplyNumber(int a, int b)
    {
        return a * b * 3;
    }
    
    public void printTypeName()
    {
        System.out.println("Supper");
    }
    
    public int getIndex()
    {
        return index;
    }
}

class SubMulti1 extends SuperMulti
{
    int index = 1;

    @Override
    public int muliplyExtra(int a, int b)
    {
        return super.muliplyExtra(a * 5, b);
    }

    @Override
    public int muliplyNumber(int a, int b)
    {
        return a * b * 7;
    }
      
    public void printTypeName()
    {

        System.out.println("Sub1");
    }
    
    public int getIndex()
    {
        return index;
    }
}


class SubMulti2 extends SuperMulti
{
    int index = 2;

    @Override
    public int muliplyExtra(int a, int b)
    {
        return super.muliplyExtra(a * 5, b);
    }

    @Override
    public int muliplyNumber(int a, int b)
    {
        return a * b * 2;
    }
      
    public void printTypeName()
    {

        System.out.println("Sub2");
    }
    
    public int getIndex()
    {
        return index;
    }
}
