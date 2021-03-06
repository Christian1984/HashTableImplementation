package implementation.collisionAvoidance;

import interfaces.ICollisionAvoidance;

public class CollisionAvoidanceLinearAlternating implements ICollisionAvoidance
{
    private int counter = 0;

    public void reset()
    {
        counter = 0;
        
    }

    public int next()
    {
        int modifier = (int) Math.ceil((double) counter / 2);
        
        if (counter % 2 == 0)
        {
            modifier *= -1;
        }
        
        counter++;
        
        return modifier;
    }

    public int getCounter()
    {
        return counter;
    }
}
