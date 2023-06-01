package com.pathfinder;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball
{
    public void draw(ShapeRenderer shapeRenderer)
    {
        shapeRenderer.begin();
        shapeRenderer.circle(0, 0, 1);
        shapeRenderer.end();
    }
}
