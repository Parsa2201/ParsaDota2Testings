package com.pathfinder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Ball
{
    float x = 10;
    float y = 10;
    float speed = 1;
    float direction = 45;
    float angularSpeed = 2;
    float radius = 4;
    float expansion = 0.1f;

    int time = 0;

    public Ball()
    {

    }

    private void move()
    {
        if(time < 100)
            radius += expansion;
        else
            direction -= angularSpeed;

        x += speed * MathUtils.cosDeg(direction);
        y += speed * MathUtils.sinDeg(direction);

        time++;
    }

    public void draw(ShapeRenderer shapeRenderer)
    {
        move();

        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.circle(x, y, radius);
    }
}
