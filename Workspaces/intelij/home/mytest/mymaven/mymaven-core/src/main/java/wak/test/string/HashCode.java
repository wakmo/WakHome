/*
 * http://stackoverflow.com/questions/113511/hash-code-implementation
 * 
 */
package wak.test.string;

/**
 *
 * @author wakkir.muzammil
 */
public class HashCode
{
     
    public static void main(String[] args)
    {
        String aa=new String("HELLO1");
        String bb=new String("HELLO1");
    
        System.out.println("AA:"+aa+",aa.hashCode:"+aa.hashCode());
        System.out.println("BB:"+bb+",bb.hashCode:"+bb.hashCode());
        
        System.out.println(aa.equals(bb));
        System.out.println(bb.equals(aa));
        
        //-------------------------------
        
        ObjA a=new ObjA(1,"HELLO1");
        ObjB b=new ObjB(1,"HELLO1");
    
        System.out.println("A:"+a+",a.hashCode:"+a.hashCode());
        System.out.println("B:"+b+",b.hashCode:"+b.hashCode());
        
        System.out.println(a.equals(b));
        System.out.println(b.equals(a));
        
        //-------------------------------
        
        ObjA a1=new ObjA(1,"HELLO1");
        ObjB b1=new ObjB(1,"HELLO1");
    
        System.out.println("A1:"+a1+",a1.hashCode:"+a1.hashCode());
        System.out.println("B1:"+b1+",b1.hashCode:"+b1.hashCode());
        
        System.out.println(a1.equals(a));
        System.out.println(a.equals(a1));
    
    }
    
}
