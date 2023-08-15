package com.pathfinder;

import com.badlogic.gdx.physics.box2d.*;

public class Wall
{
    private World world;
    private static final float thickness = 2;
    private static final float density = 2.0f;
    private static final float friction = 2.0f;
    private static final float restitution = 2.0f;

    public Wall(float x, float y, float length, boolean isVertical)
    {
        this.world = WorldManager.getWorld();
        init(x, y, length, isVertical);
    }

    private void init(float x, float y, float length, boolean isVertical)
    {
        float width = isVertical ? thickness : length;
        float height = isVertical ? length : thickness;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        Body body = world.createBody(bodyDef);

        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.shape = rectangle;
        Fixture fixture = body.createFixture(fixtureDef);

        rectangle.dispose();
    }
}
