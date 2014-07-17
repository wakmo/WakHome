/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.onlinetest.nixdof;

/**
 *
 * @author wakkir.muzammil
 */
public class Cat extends Animal implements IAnimal
{
    @Override
    public int getAge(int age)
    {
        return age;
    }

    @Override
    public void doAge() //throws NumberFormatException
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /*
    @Override // Valid
    protected int getLocalCode(String value,boolean isValidated) throws NumberFormatException
    {
        return 5;
    }*/
    /*
    @Override  //Invalid
    private int getLocalCode(String value,boolean isValidated)
    {
        return 5;
    }
    */
    /*
    @Override //Valid
    public int getLocalCode(String value,boolean isValidated)
    {
        return 5;
    }
    */
    
}
