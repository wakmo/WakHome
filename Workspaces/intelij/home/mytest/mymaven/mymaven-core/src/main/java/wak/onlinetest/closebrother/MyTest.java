/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.onlinetest.closebrother;

/**
 *
 * @author wakkir
 * 
 * 
 *  7.3 Determine the effect upon object references and primitive values when they are passed into methods
    that perform assignments or other modifying operations on the parameters.
    Important Concepts
    • The primitive variables are passed to a method by their values, while the object variables are
    passed by references. This should be obvious because a primitive variable holds the value itself,
    while an object variable only stores a reference to the object, and not the object itself.
    • When a primitive variable is passed as an argument in a method call, only the copy of the original
    variable is passed. Therefore, any change to the passed variable in the called method will not affect
    the variable in the calling method.
    • When you pass a reference variable to a method, you pass a copy of it and not the original reference
    variable. Because the copy of the reference variable points to the same object to which the original
    variable points, the called method can change the object properties by using the passed reference.
    Exam Tip
    • If the passed reference is changed in the called method (for example, set to null or reassigned to
    another object), it has no effect on the original variable in the calling method, because it is after all
    just a copy of the original variable.
 * 
 */
public class MyTest 
{
    public void do1(int myint)
    {
        myint++;
    }
    
    public void do2(MyObj mo)
    {
        mo.setId(2);
        mo.setName("Hello");
    }
    
    public void do3(MyObj mo)
    {
        MyObj mo1=mo;
        mo1.setId(4);
    }
    
    public void do4(MyObj mo)
    {
        mo=null;
    }

    public static void main(String[] args) 
    {
        MyTest test=new MyTest();
        int myint=1;
        MyObj mo=new MyObj(1,"one");
        System.out.println("1 : "+myint);
        test.do1(myint);
        System.out.println("2 : "+myint);
        System.out.println("3 : "+mo);
        test.do2(mo);       
        System.out.println("1 : "+mo);
        test.do3(mo);
        System.out.println("5 : "+mo);
        test.do4(mo);
        System.out.println("6 : "+mo);
        
        

    }

}

class MyObj {

    private int id;
    private String name;

    public MyObj(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyObj{" + "id=" + id + ", name=" + name + '}';
    }

}
