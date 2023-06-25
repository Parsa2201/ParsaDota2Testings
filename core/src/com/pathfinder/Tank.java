package com.pathfinder;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tank
{
    public String id;
    public Vector2 position;
    public Vector2 velocity;
    private final Random random;

    private Vector2 lastServerPosition;

    public Tank(String id, Vector2 position)
    {
        random = new Random();
        this.id = id;
        this.position = position;
        velocity = new Vector2(100, 100);
        lastServerPosition = new Vector2(position);
    }

    public Tank(String id)
    {
        random = new Random();
        this.id = id;
        velocity = new Vector2(100, 100);
        position = new Vector2(random.nextInt(500), random.nextInt(500));
        lastServerPosition = new Vector2(position);
    }

    public void update(float delta)
    {
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        velocity = velocity.rotateDeg(-1.0f);
    }

    public void render(Batch batch)
    {
        batch.draw(Assets.tank, position.x, position.y);
    }

    public boolean hasMovedInRelationToTheServer()
    {
        return !position.equals(lastServerPosition);
    }

    public void setLastServerPosition()
    {
        this.lastServerPosition.set(position);
    }
}
