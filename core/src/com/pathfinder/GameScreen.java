package com.pathfinder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class GameScreen implements Screen
{
    MyGdxGame game;
    SpriteBatch batch;
    OrthographicCamera camera;
    World world;
    Box2DDebugRenderer debugRenderer;

    GameScreen(SpriteBatch batch, MyGdxGame game)
    {
        this.game = game;
        this.batch = batch;
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        System.out.println(camera.zoom);
        camera.zoom = 0.2f;
        camera.update();
        world = new World(new Vector2(0, -9.8f), false);
        WorldManager.setWorld(world);
        debugRenderer = new Box2DDebugRenderer();
        init();
    }

    private void init()
    {
        Ball ball1 = new Ball(0, 0, 5);
        Ball ball2 = new Ball(2, 4, 10);

        Wall wall1 = new Wall(0, -78, 80, false);
        Wall wall2 = new Wall(82, 0, 80, true);
        Wall wall3 = new Wall(-82, 0, 80, true);
    }

    @Override
    public void show()
    {

    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.render(world, camera.combined);

        doPhysicStep(delta);
    }

    private float accumulator = 0;
    public void doPhysicStep(float deltaTime)
    {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= 1/60f)
        {
            world.step(1/60f, 6, 2);
            accumulator -= 1/60f;
        }
    }

    @Override
    public void resize(int width, int height)
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {

    }
}
