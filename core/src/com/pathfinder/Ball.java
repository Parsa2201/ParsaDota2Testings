package com.pathfinder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Ball
{
    float x = 10;
    float y = 10;
    float speed = 0.5f;
    float direction = 22.5f;
    float angularSpeed = 2;
    float width = 4;
    float height = 4;
    float expansion = 0.1f;
    float acceleration;

    private Information information;

    int time = 0;

    public Ball()
    {
        information = new Information();
        information.addInfo("x");
        information.addInfo("y");
        information.addInfo("speed");
        information.addInfo("direction");
        information.addInfo("angularSpeed");
        information.addInfo("width");
        information.addInfo("height");
        information.addInfo("acceleration");
        information.addInfo("time");
        information.addInfo("expansion");
    }

    private void move()
    {
        acceleration = MathUtils.sin((float) time / 100) / 100;
        speed += acceleration;
        angularSpeed += acceleration;

        if(time < 150)
        {
            width += expansion;
            height += expansion;
        }
        else
            direction -= angularSpeed;

        x += speed * MathUtils.cosDeg(direction);
        y += speed * MathUtils.sinDeg(direction);

        if(time % 100 < 50)
        {
            width += expansion;
            height -= expansion;
        }
        else
        {
            width -= expansion;
            height += expansion;
        }

        time++;
    }

    public void draw(ShapeRenderer shapeRenderer)
    {
        move();
        if(time % 3 == 0)
        {
            information.changeInfoValue(x);
            information.changeInfoValue(y);
            information.changeInfoValue(speed);
            information.changeInfoValue(direction);
            information.changeInfoValue(angularSpeed);
            information.changeInfoValue(width);
            information.changeInfoValue(height);
            information.changeInfoValue(acceleration);
            information.changeInfoValue(time);
            information.changeInfoValue(expansion);
        }


        shapeRenderer.setColor(1, 0, 0, 1);
        shapeRenderer.ellipse(x, y, width, height);
    }

    public void drawInformation(Batch batch)
    {
        information.draw(batch);
    }
}
