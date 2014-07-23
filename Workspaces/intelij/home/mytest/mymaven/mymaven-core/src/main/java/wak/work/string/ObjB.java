/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wak.work.string;

/**
 *
 * @author wakkir.muzammil
 */
public class ObjB
{

    private int id;
    private String name;
    

    public ObjB(int id, String name)
    {
        this.id = id;
        this.name = name;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 67 * hash + this.id;
        hash = 67 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final ObjB other = (ObjB) obj;
        if (this.id != other.id)
        {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
        {
            return false;
        }
        return true;
    }

   
    
    

    @Override
    public String toString()
    {
        return "ObjB{" + "id=" + id + ", name=" + name + '}';
    }
       

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
