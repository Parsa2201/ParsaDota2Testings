package com.pathfinder;

import com.badlogic.gdx.physics.box2d.*;

public class Ball
{
    private World world;
    private Body body;
    private CircleShape circleShape;
    public static final float density = 0.5f;
    public static final float friction = 0.4f;
    public static final float restitution = 0.6f;

    public Ball(float x, float y, float r)
    {
        this.world = WorldManager.getWorld();
        init(x, y, r);
    }

    private void init(float x, float y, float r)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        // Create our body in the world using our body definition
        body = world.createBody(bodyDef);
        body.setLinearVelocity(0, 50);

        // Create a circle shape and set its radius to 6
        circleShape = new CircleShape();
        circleShape.setRadius(r);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        //circle.dispose();
    }
}
