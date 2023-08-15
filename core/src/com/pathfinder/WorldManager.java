package com.pathfinder;

import com.badlogic.gdx.physics.box2d.World;

public class WorldManager
{
    private static World WORLD;

    public static World getWorld()
    {
        return WORLD;
    }

    public static void setWorld(World world)
    {
        WORLD = world;
    }
}
