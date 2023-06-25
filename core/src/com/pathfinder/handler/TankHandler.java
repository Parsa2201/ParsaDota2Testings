package com.pathfinder.handler;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.pathfinder.Tank;

import java.util.HashMap;
import java.util.Map;

public class TankHandler
{
    private final Map<String, Tank> tanks;
    private int tankCount;
    private boolean isServer;

    public TankHandler()
    {
        tanks = new HashMap<>();
    }

    public void addTank(String id)
    {
        tanks.put(id, new Tank(id));
        tankCount++;
    }

    public void addTank(String id, Vector2 position)
    {
        tanks.put(id, new Tank(id, position));
        tankCount++;
    }

    public void removeTank(String id)
    {
        tanks.remove(id);
        tankCount--;
    }

    public void setPositionById(String id, Vector2 position)
    {
        if(!tanks.containsKey(id))
            return;

        tanks.get(id).position = position;
    }

    public void update(final float delta)
    {
        tanks.values().forEach(tank -> tank.update(delta));
        if(isServer)
            doGameLogic();
    }

    public void render(final Batch batch)
    {
        tanks.values().forEach(tank -> tank.render(batch));
    }

    public void checkIfIsServer()
    {
        isServer = tankCount == 0;
    }

    private void doGameLogic()
    {
        tanks.values().forEach(tank1 -> tanks.values().forEach(tank2 ->
        {
            if(tank1.id.equals(tank2.id))
                return;

            if(tank1.position.dst(tank2.position) < 50)
            {
                tank1.velocity = tank1.velocity.rotateDeg(180);
                tank2.velocity = tank2.velocity.rotateDeg(180);
            }
        }));
    }
}
