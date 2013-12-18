package implementation.collisionAvoidance;

import interfaces.ICollisionAvoidance;

public class CollisionAvoidanceSquare implements ICollisionAvoidance
{
    private int counter = 0;

    public void reset()
    {
        counter = 0;
        
    }

    public int next()
    {
        return (int) Math.pow(counter++, 2); //return hash-modification, then increment
    }

    public int getCounter()
    {
        return counter;
    }
}
