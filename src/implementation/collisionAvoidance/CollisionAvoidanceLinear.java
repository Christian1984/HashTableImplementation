package implementation.collisionAvoidance;

import interfaces.ICollisionAvoidance;

public class CollisionAvoidanceLinear implements ICollisionAvoidance
{
    private int counter = 0;

    public void reset()
    {
        counter = 0;        
    }

    public int next()
    {
        return counter++; //return hash-modification, then increment
    }

    public int getCounter()
    {
        return counter;
    }
}
