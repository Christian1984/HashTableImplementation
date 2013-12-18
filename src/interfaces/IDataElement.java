package interfaces;

public interface IDataElement
{
    //setters
    public void setKey(String key);
    public void setValue(int value);
    
    //getters
    public String getKey();
    public int getValue();
    public boolean isDeleted();
    
    //methods
    public void delete();
}
