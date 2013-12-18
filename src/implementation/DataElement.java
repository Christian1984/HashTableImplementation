package implementation;

import interfaces.*;

public class DataElement implements IDataElement
{
    private String key;
    private int value;
    private boolean isDeleted = false;
    
    //constructors
    public DataElement(String key, int value)
    {
        this.key = key;
        this.value = value;
    }
    
    public DataElement(IDataElement that) //copy constructor
    {
        if (that != null)
        {
            this.key = that.getKey();
            this.value = that.getValue();
            this.isDeleted = that.isDeleted();
        }
        else
        {
            this.key = "default";
            this.value = 0;
        }
    }
    
    public DataElement()
    {
        this("default", 0);
    }
    
    //setters
    public void setKey(String key)
    {
        this.key = key;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    
    //getters
    public String getKey()
    {
        return key;
    }

    public int getValue()
    {
        return value;
    }
    
    public boolean isDeleted()
    {
        return isDeleted;
    }
    
    //methods
    public int hashCode()
    {
        return key.hashCode() & 0x7FFFFFFF; //Make sure that hashCode is positive!
    }
    
    public void delete()
    {
        //flags the element as deleted
        key = "null";
        value = 0;
        isDeleted = true;
    }

    public String toString()
    {
        return "key = " + key + "; value = " + value;
    }
}
