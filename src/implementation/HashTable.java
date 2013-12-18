package implementation;

import interfaces.*;
import implementation.collisionAvoidance.*;

public class HashTable implements IHashTable
{
    //attributes
    private ICollisionAvoidance caStrategy;
    private IDataElement[] htable;
    private int numberOfEntries = 0;
    
    //final attributes
    private final static double MAX_LOAD_FACTOR = 0.6f;
    private final static double MIN_LOAD_FACTOR = 0.1f;
    private final static double AVG_LOAD_FACTOR = 0.5f;
    
    private final static int MIN_SIZE = 7; //first prime less than 10
    private final static ICollisionAvoidance DEFAULT_CA_STRATEGY = 
        new CollisionAvoidanceLinear();
    
    
    //constructors
    public HashTable(ICollisionAvoidance caStrategy, int size)
    {
        this.caStrategy = caStrategy instanceof ICollisionAvoidance ? caStrategy : DEFAULT_CA_STRATEGY;        
        htable = size > 0 ? new IDataElement[size] : new IDataElement[MIN_SIZE];
    }
    
    public HashTable(int size)
    {
        this(DEFAULT_CA_STRATEGY, size);
    }
    
    public HashTable(ICollisionAvoidance caStrategy)
    {
        this(caStrategy, MIN_SIZE);
    }
    
    public HashTable()
    {
        this(DEFAULT_CA_STRATEGY, MIN_SIZE);
    }
    

    //getters
    public int getSize()
    {
        return htable.length;
    }

    public double getLoadFactor()
    {       
        //return getNumberOfEntries() / getSize();
        return (double) numberOfEntries / getSize();
    }
    
    
    //dictionary methods
    public boolean insert(IDataElement element, boolean overwrite)
    {
        //make sure that element is not null
        if (element == null)
        {
            System.out.println("ERROR: Element to insert is not a valid object! Aborting insert!!!");
            return false;
        }
        
        //make a copy of element, so that the htable does not reference the original element!
        IDataElement elCopy = new DataElement(element);
        
        System.out.println("INFO: Trying to insert element with key '" + elCopy.getKey() + "'...");
        
        //if key is already in htable, deal with it (depending on overwrite)
        int memberID = getIndexOf(elCopy.getKey());
        
        if (memberID != -1)
        {
            if (overwrite)
            {
                System.out.println("INFO: Key '" + elCopy.getKey() + "' found at index " + memberID + ". Old element overwritten!");
                insertElementAt(elCopy, memberID);                
                return true;
            }
            else
            {
                System.out.println("WARNING: Key '" + elCopy.getKey() + "' found at index " + memberID + ". Keeping old element!");
                return false;
            }
        }
        
        //if program gets here, key is NOT in htable and needs to be inserted.  
        //start scanning!
        caStrategy.reset(); //reset caStrategy
        
        for (int scanCount = 0; scanCount < getSize(); scanCount++)
        {
            int index = calculateIndex(element);
            System.out.println("INFO: Scanning => Element: " + elCopy.getKey() + "; scanCount: " + scanCount + "; Hashcode: " + index);
            
            //insert element only if slot is empty or flagged as deleted
            if (!(htable[index] instanceof IDataElement) || htable[index].isDeleted())
            {
                numberOfEntries++;
                insertElementAt(element, index);
                
                //check if htable needs to be resized
                if (getLoadFactor() > MAX_LOAD_FACTOR)
                {
                    System.out.println("INFO: MAX_LOAD_FACTOR exceeded. Resizing htable and rehashing elements!\n");
                    expandHashtable();
                }
                
                return true;
            }
        }
        
        //if code gets here, scanning was not successful and element could not be inserted.
        //since hashtable resizes automatically, this should never happen
        System.out.println("ERROR: No free slots found to insert element! It took " + caStrategy.getCounter() + " tries to get here.\n");
        return false;
    }

    public boolean delete(String key)
    {
        int index = getIndexOf(key);
        
        //if key was found, flag slot as deleted
        if (index != -1)
        {
            numberOfEntries--;
            
            htable[index].delete();
            System.out.println("INFO: Element with key '" + key + "' successfully deleted at index " + index 
                + ". Current LoadFactor is " + String.format("%.2f", getLoadFactor()));

            //check if htable needs to be resized           
            if (numberOfEntries > 0 && getLoadFactor() < MIN_LOAD_FACTOR)
            {
                System.out.println("INFO: MIN_LOAD_FACTOR underrun. Resizing htable and rehashing elements!\n");
                reduceHashtable();
            }
            
            return true;
        }
        
        //if element was not found return false
        System.out.println("WARNING: Element could not be deleted: Key '" + key + "' not found!");  
        return false;
    }

    public boolean member(String key)
    {
        return (getIndexOf(key) != -1);
    }

    
    //methods    
    public IDataElement getDataElementByKey(String key)
    {       
        return getDataElementByIndex(getIndexOf(key));
    }

    public IDataElement getDataElementByIndex(int index)
    {
        if (index >= 0 && index < getSize())
        {
            return htable[index];
        }
        
        return null;
    }

    public int getValue(String key)
    {
        IDataElement el = getDataElementByKey(key);
        
        if (el instanceof IDataElement)
        {
            return el.getValue();
        }
        else
        {
            return 0;
        }
    }
    
    
    //helper methods
    private int calculateIndex(IDataElement element)
    {
        int index = (element.hashCode() + caStrategy.next()) % getSize();
        
        if (index < 0)
        {
            index = (getSize() - 1) + index;
        }
        
        return index;
    }
    
    private int getIndexOf(String key)
    {
        //initialize caStrategy for new scan
        caStrategy.reset();
        
        //create a new element from key, so that DataElement's hashCode()-method can be used
        IDataElement keyElement = new DataElement(key, -1);
        
        //scan
        for (int scanCount = 0; scanCount < getSize(); scanCount++)
        {
            int index = calculateIndex(keyElement);
            
            if ((htable[index] instanceof IDataElement))
            {
                if (!htable[index].isDeleted() && htable[index].getKey().equals(keyElement.getKey()))
                {
                    return index; //if the DataElement in this slot is not flagged as deleted and the keys are equal, the wanted index is found
                }
            }
            else
            {
                break; //if at any point during the scan the method encounters a slot that is null, abort search. element is not in htable!
            }
        }
        
        return -1;
    }
    
    private void insertElementAt(IDataElement element, int index)
    {
        if (index >= 0 && index < getSize())
        {
            htable[index] = element;
            System.out.println("INFO: Element with key '" + element.getKey() + "' successfully inserted at " + index 
                + ". Insert required " + caStrategy.getCounter() + " tries. Current LoadFactor is " 
                + String.format("%.2f", getLoadFactor()) + "\n");
        }
    }
    
    /*private int getNumberOfEntries()
    {
        int numberOfEntries = 0;
        
        for (int i = 0; i < htable.length; i++)
        {
            if (htable[i] instanceof IDataElement && !htable[i].isDeleted())
            {
                numberOfEntries++;
            }
        }
        
        return numberOfEntries;
    }*/
    
    
    //helper methods to resize hashtable
    private void expandHashtable()
    {
        int newSize = 2 * getSize() + 1; //ensures that newSize is uneven. TODO: a better solution should search for next prime.
        resizeHashtableTo(newSize);        
    }
    
    private void reduceHashtable()
    {
        int newSize = (int) Math.ceil((double) numberOfEntries / AVG_LOAD_FACTOR); //resize hashtable to achieve AVG_LOAD_FACTOR
        
        if (newSize % 2 == 0) 
        {
            newSize += 1; //ensures that newSize is uneven. TODO: again, a better solution should search for next prime.
        }

        resizeHashtableTo(newSize);
    }
    
    private void resizeHashtableTo(int newSize)
    {

        
        if (newSize < MIN_SIZE) //don't do anything if size is less than MIN_SIZE
        {
            newSize = MIN_SIZE;
            //return;
        }
        
        //preserve old number of entries
        int oldNumberOfEntries = numberOfEntries;
        numberOfEntries = 0;        
        
        IDataElement[] oldTable = htable;
        htable = new DataElement[newSize];
        
        for (int i = 0; i < oldTable.length; i++)
        {            
            if (oldTable[i] instanceof IDataElement && !oldTable[i].isDeleted())
            {
                if (!insert(oldTable[i], false))
                {
                    //TODO: write and throw own HashtableExpandError for better error handling.
                    System.out.println("ERROR: An error occured while resizing the hashtable. Recovering old status!\n");
                    htable = oldTable;
                    numberOfEntries = oldNumberOfEntries;
                    return; 
                }
            }
        }
    }
    
    
    //output
    public String toString()
    {
        String s = "";
        
        for (int i = 0; i < htable.length; i++)
        {
            String elementString;
            
            if (htable[i] instanceof IDataElement)
            {
                if (htable[i].isDeleted())
                {
                    elementString = "deleted";
                }
                else
                {
                    elementString = htable[i].toString();
                }
            }
            else
            {
                elementString = "null";                
            }            

            s += (i + ": " + elementString + "\n");
        }
        
        return s;
    }
    
    public void print()
    {
        System.out.println("\nHashtable:");
        System.out.println("----------");
        System.out.println(toString());
        System.out.println("----------");
        System.out.println("Hashtable size: " + getSize()); 
        System.out.println("Number of entries: " + numberOfEntries); //getNumberOfEntries()); 
        System.out.println("Load Factor = " + getLoadFactor());
        System.out.println("----------\n");
    }
}
