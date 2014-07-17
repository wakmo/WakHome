/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wakkir.mytest.java;

/**
 *
 * @author wakkir
 */
public class StringOps 
{
    public void testWhile()
    {
        int i=0;
        int j=0;
        while(i<10)
        {
            System.out.println("i>"+(i++));
            System.out.println("j>"+(++j));
            //i++;
        }    
    }
    
    public void testDoWhile()
    {
        int i=0;
        do
        {
            System.out.println(i++);
            //i++;
        }
        while(i<10);    
    }
    
    public void testSubstring()
    {
        String str="abcdefghijklmno";
        
        System.out.println(str.substring(1));
        System.out.println(str.indexOf('c'));
            
         
    }
    
    
    
    public static void main(String[] args)
    {
        StringOps ss=new StringOps();
        System.out.println("Starting...");
        //ss.testWhile();
        //ss.testDoWhile();
        ss.testSubstring();
        
        System.out.println("Ended...");
        
        
    }    
}
