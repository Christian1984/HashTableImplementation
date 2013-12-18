package testcode;

import implementation.*;
import implementation.collisionAvoidance.*;
import interfaces.IHashTable;

public class TestcodeRandom
{
   /**
    * Test class allows to test the HashTable implementation with randomly created elements.
    * @param args[0] number of elements to be inserted into the HashTable
    * @param args[1] upper limit for the randomizer
    * @param args[3] selected collision avoidance strategy [0-3]
    */
    
    public static void main(String[] args)
    {
        //read command line parameters
        int elements = (args.length > 0 && args[0] != null) ? tryParseInt(args[0]) : 0;
        int random_limit = (args.length > 1 && args[1] != null) ? tryParseInt(args[1]) : 0;
        int caStrategySelection = (args.length > 2 && args[2] != null) ? tryParseInt(args[2]) : 0;
        
        //store as finals
        final int ELEMENTS = elements != 0 ? elements : 40; 
        final int RANDOM_LIMIT = random_limit != 0 ? random_limit : 10000;
        
        //declare HashTable as final
        final IHashTable htable;
        
        //select collision avoidance strategy
        switch (caStrategySelection)
        {                
            case 1:
                htable = new HashTable(new CollisionAvoidanceLinearAlternating());
                break;
                
            case 2:
                htable = new HashTable(new CollisionAvoidanceSquare());
                break;
                
            case 3:
                htable = new HashTable(new CollisionAvoidanceSquareAlternating());
                break;

            case 0:
            default:
                htable = new HashTable(new CollisionAvoidanceLinear());
                break;
        }
        
        htable.print();
        
        //Add elements
        System.out.println("\n============= ADDING " + ELEMENTS + " RANDOM ELEMENTS =============\n");
        
        for (int i = 0; i < ELEMENTS; i++)
        {
            String key = Integer.toString((int) (Math.random() * RANDOM_LIMIT));
            int value = (int) (Math.random() * RANDOM_LIMIT);
            
            htable.insert(new DataElement(key, value), true);
        }        

        htable.print();

        System.out.println("\n============= END OF ADDING ELEMENTS =============\n");
        
        //Delete Elements
        System.out.println("\n============= DELETING " + ELEMENTS + " RANDOM ELEMENTS =============\n");

        for (int i = 0; i < ELEMENTS; i++)
        {
            String key = "000";
            
            while(true)
            {
                key = Integer.toString((int) (Math.random() * RANDOM_LIMIT));                 
                if (htable.member(key) || htable.getLoadFactor() <= 1e-10) break;
            } 
            
            if (Math.floor(Math.random() * 4) != 0) htable.delete(key); //delete ~75% of all entries
        } 
        
        htable.print();
    }
    
    private static int tryParseInt(String s)
    {
        try
        {
            int res = Integer.parseInt(s);
            return res;
        }
        catch(NumberFormatException e)
        {
            return 0;
        }
    }

}
