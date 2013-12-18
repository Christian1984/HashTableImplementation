package testcode;

import implementation.collisionAvoidance.*;
import interfaces.ICollisionAvoidance;

public class TestCollisionAvoidanceStrategies
{
    public static void main(String[] args)
    {
        ICollisionAvoidance caStrategy;
        int steps = 20;
        
        caStrategy = new CollisionAvoidanceLinear();
        printStrategy(caStrategy, steps);
        
        caStrategy = new CollisionAvoidanceLinearAlternating();
        printStrategy(caStrategy, steps);
        
        caStrategy = new CollisionAvoidanceSquare();
        printStrategy(caStrategy, steps);
        
        caStrategy = new CollisionAvoidanceSquareAlternating();
        printStrategy(caStrategy, steps);
    }
    
    private static void printStrategy(ICollisionAvoidance caStrategy, int steps)
    {
        caStrategy.reset();
        
        System.out.println("\n----- START -----\n");
        
        for (int i = 0; i < steps; i++)
        {
            System.out.println(caStrategy.next());
        }        

        System.out.println("\n----- END -----\n");
    }
}
