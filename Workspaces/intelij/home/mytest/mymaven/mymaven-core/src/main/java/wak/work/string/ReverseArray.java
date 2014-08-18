/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.string;

import java.util.Arrays;

/**
 *
 * @author wakkir.muzammil
 */
public class ReverseArray
{
    private static final String oneWord="This is my first task";
    
    public void reverseChar(char[] carr)
    {
        
        int j=carr.length-1;
        for(int i=0; i<carr.length;i++)
        {
            if(j <= i)
            {
                break;
            }
            char first=carr[i];
            char last=carr[j];
            carr[j--]=first;
            carr[i] = last;            
        }
        System.out.print(String.valueOf(carr));
        
         System.out.println();        
    }       
    
    public void reverseWord(char[] src)
    {        
        int j=0;
        char[] target=new char[src.length];
        for(int i=0; i<src.length;i++)
        {
            if(' '==src[i])
            {                
                System.arraycopy(src, j, target,target.length-i , i-j);
                j=i+1;
            } 
            if(i==(src.length-1))
            {                
                System.arraycopy(src, j, target,target.length-(i+1) , (i+1)-j);                
            }
        }  
        
        
        System.out.print(String.valueOf(target));
        System.out.println();
        
    }  
    
    
    
    public static void main(String[] args)
    {
        ReverseArray xx=new ReverseArray();
        //xx.reverseChar(oneWord.toCharArray());
        xx.reverseWord(oneWord.toCharArray());
    
    }
}
