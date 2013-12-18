package interfaces;

public interface IHashTable
{    
    //basic dictionary operations
    public boolean insert(IDataElement element, boolean overwrite); //throws HashTableOverflowException;
    public boolean delete(String key);
    public boolean member(String key);
    
    //hashtable operations
    public int getSize();
    public double getLoadFactor();
    
    public IDataElement getDataElementByKey(String key);
    public IDataElement getDataElementByIndex(int index);
    public int getValue(String key);
    
    //print
    public void print();
}
