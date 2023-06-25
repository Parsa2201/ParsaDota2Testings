package com.pathfinder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.pathfinder.handler.TankHandler;
import com.pathfinder.net.SocketClientHandler;

public class GameScreen implements Screen
{
    Batch batch;
    Tank playerTank;
    TankHandler tankHandler;
    SocketClientHandler socketClientHandler;

    public GameScreen(Batch batch)
    {
        this.batch = batch;
        playerTank = new Tank("");
        tankHandler = new TankHandler();
        socketClientHandler = new SocketClientHandler(playerTank, tankHandler);
    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(0.4f, 0.4f, 0.6f, 1);

        playerTank.update(delta);
        tankHandler.update(delta);
        socketClientHandler.update(delta);

        batch.begin();
        playerTank.render(batch);
        tankHandler.render(batch);
        batch.end();
    }

    @Override
    public void show()
    {

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
        dispose();
    }

    @Override
    public void dispose()
    {
        socketClientHandler.dispose();
    }
}
