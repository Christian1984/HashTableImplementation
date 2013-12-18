package testcode;

import interfaces.*;
import implementation.*;
import implementation.collisionAvoidance.*;

public class Testcode
{
    public static void main(String[] args)
    {
        //IHashTable htable = new HashTable(new CollisionAvoidanceLinear());
        //IHashTable htable = new HashTable(new CollisionAvoidanceLinearAlternating());
        //IHashTable htable = new HashTable(new CollisionAvoidanceSquare());
        IHashTable htable = new HashTable(new CollisionAvoidanceSquareAlternating());
        
        htable.print();
        
        IDataElement el1 = new DataElement("element1", 1);
        System.out.println(el1.toString());
        System.out.println("el1.hashCode() ==> " + el1.hashCode());
        System.out.println();
        
        IDataElement el2 = new DataElement("element2", 2);
        System.out.println(el2.toString());
        System.out.println("el2.hashCode() ==> " + el2.hashCode());
        System.out.println();
        
        IDataElement el3 = new DataElement("element3", 3);
        System.out.println(el3.toString());
        System.out.println("el3.hashCode() ==> " + el3.hashCode());
        System.out.println();
        
        IDataElement el4 = new DataElement("Very different key!!!", 19875);
        System.out.println(el4.toString());
        System.out.println("el4.hashCode() ==> " + el4.hashCode());
        System.out.println();  
        
        

        System.out.println("\n\nInserting el1!");
        htable.insert(el1, false);
        
        System.out.println("\n\nInserting el2!");
        htable.insert(el2, false);
        
        System.out.println("\n\nInserting el3!");
        htable.insert(el3, false);
        
        System.out.println("\n\nInserting el4!");
        htable.insert(el4, false);
        
        htable.print();
        
        IDataElement el5 = new DataElement("element1", 5);
        System.out.println(el5.toString());
        System.out.println("el5.hashCode() ==> " + el5.hashCode());
        System.out.println();
        
        System.out.println("\n\nInserting el5! DO NOT OVERWRITE!!!");
        htable.insert(el5, false);

        htable.print();
        
        System.out.println("\n\nInserting el5! OVERWRITE!!!");
        htable.insert(el5, true);

        htable.print();        

        IDataElement el6 = new DataElement("NewElement6", 66);
        IDataElement el7 = new DataElement("NewElement7", 77);
        IDataElement el8 = new DataElement("NewElement8", 88);
        IDataElement el9 = new DataElement("NewElement9", 99);

        System.out.println("\n\nInserting Elements el6 through el9!");
        htable.insert(el6, false);
        htable.insert(el7, false);
        htable.insert(el8, false);
        htable.insert(el9, false);
        
        htable.print(); 

        System.out.println("\n\nDeleting Elements el1 through el7!");        
        htable.delete(el1.getKey());        
        htable.delete(el2.getKey());        
        htable.delete(el3.getKey());        
        htable.delete(el4.getKey());
        htable.delete(el5.getKey());        
        htable.delete(el6.getKey());        
        htable.delete(el7.getKey());
        
        htable.print(); 
        
        System.out.println(htable.member("NewElement8"));
        System.out.println(htable.member("NewElement"));

        System.out.println("\n\nDeleting Elements el8 through el9!");        
        htable.delete(el8.getKey());        
        htable.delete(el9.getKey()); 
        
        htable.print(); 
    }
}
