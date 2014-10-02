/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.string;


class A
{
    {
        System.out.println("A1");
    }
    
    A()
    {
        System.out.println("A2");
    }

}

public class MultiInherit extends A
{    
    {
        int a=424;
        System.out.println("M1"+a);
    }
    
    MultiInherit()
    {
        System.out.println("M2");
    }
    

    public static void main(String[] args)
    {
        MultiInherit m = new MultiInherit();
        System.out.println();
    }
}
