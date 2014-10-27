/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.onlinetest.closebrother;

import java.util.Iterator;

/**
 *
 * @author wakkir
 */
public class CloseBrother
{
    /*
     private static int count;
     
     static
     {
        System.out.println("In block 1");
        count =10;
     }
    
     private int[] data;
     
     {
        System.out.println("In block 2");
        data =new int[count];
        for(int i=0; i<count;i++)
        {
            data[i]=i;
        }
     }
     */

    void test1()
    {
        System.out.println("------------- test1 -----------------");
        int a = 0;
        if (a >= 0);
        if (a == 0);
        System.out.print("1");
        //else System.out.println("2");
        System.out.print("3");

    }

    void test2()
    {
        System.out.println("------------- test2 -----------------");

    }

    void test3()
    {
        System.out.println("------------- test3 -----------------");

    }

    void test4()
    {
        System.out.println("------------- test4  -----------------");

    }

    void test5()
    {
        System.out.println("------------- test5  -----------------");
    }

    void test6()
    {
        System.out.println("------------- test6 -----------------");

    }

    void test7()
    {
        System.out.println("------------- test7 -----------------");
    }

    void test8()
    {
        System.out.println("------------- test8 -----------------");

    }

    void test9()
    {
        System.out.println("------------- test9 -----------------");
    }

    void test10()
    {
        System.out.println("------------- test10 -----------------");

    }

    void test11()
    {
        System.out.println("------------- test11 -----------------");

    }

    void test12()
    {
        System.out.println("------------- test12 -----------------");
    }

    void test13()
    {
        System.out.println("------------- test12 -----------------");
    }

    void test14()
    {
        System.out.println("------------- test12 -----------------");
    }

    void test15()
    {
        System.out.println("------------- test15 -----------------");

    }

    public static void main(String[] args)
    {

        //CloseBrother x = new CloseBrother();
        //x.test1();
        //x.test2();
        //x.test3();
        //x.test4();
        //x.test5();
        //x.test6();
        //x.test7();
        //x.test8();
        //x.test9();
        //x.test10();
        //x.test11();
        //x.test12();
        //x.test13();
        //x.test14();
        //x.test15();
        /*
         System.out.println("Count ="+count);
         System.out.println("Before 1");
         CloseBrother x1=new CloseBrother();
         System.out.println("Before 2");
         CloseBrother x2=new CloseBrother();
         */

        SuperMuli m=new SubMuli();
        System.out.println(m.muliplyExtra(1, 2));
        /*
        SuperMuli m = new SubMuli();
        m.printTypeName();
        System.out.println("," + m.index);
        */ 

        //byte arr[]=new byte[]{2,3,4,5};
        //for (final int i:getCharArray(arr))        
        //System.out.print(i+" ");

        //int a[5]=new int[]{2,3,4,5,6};

        //int [][]a5=new int[5]{2,3,4,5,6};
        //int[][] a3=new int[5][];
        //int a4[][][] a3={{{1,2}},{{3,4}},{5,6}};
        //int[,] a2=new int[5,5];
       /*int j=0;
         int a[]={2,4};
         do for(int i:a)
         System.out.print(i+" ");
         while(j++ <1);
         */

        System.out.println("\nEnded...");


    }

    static char[] getCharArray(byte[] arr)
    {
        char[] carr = new char[4];
        int i = 0;
        for (byte c : arr)
        {
            carr[i] = (char) c++;
            i++;
        }
        return carr;
    }
}

class SuperMuli
{

    public int muliplyExtra(int a, int b)
    {
        return this.muliplyNumber(a, b);
    }

    public int muliplyNumber(int a, int b)
    {
        return a * b * 3;
    }
    int index = 1;

    public void printTypeName()
    {
        System.out.print("Supper");
    }
}

class SubMuli extends SuperMuli
{

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
    int index = 2;

    public void printTypeName()
    {

        System.out.print("Sub");
    }
}