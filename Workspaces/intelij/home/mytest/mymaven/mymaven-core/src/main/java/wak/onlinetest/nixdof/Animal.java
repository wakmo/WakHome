/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.onlinetest.nixdof;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wakkir.muzammil
 */
public class Animal
{
   public int getAge(int age) throws RemoteException, IOException, Exception
   {
       if(age<0)
       {
        throw new RemoteException("RemoteException");
       }       
       if(age>100)
       {
        throw new IOException("IOException");
       }
       
       if(age==50)
       {
        throw new Exception("Exception");
       }
       
       return age+10;
   }
   
   
   public void calculateAge() throws RemoteException, IOException, Exception
   {
     getAge(3);
   }
   
   public void calculateAge2()
   {
        try
        {
            getAge(3);
        }
        catch (RemoteException ex)
        {
            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            Logger.getLogger(Animal.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   protected int getLocalCode(String value, boolean isValidated) 
   {
       return 4;
   }
}


