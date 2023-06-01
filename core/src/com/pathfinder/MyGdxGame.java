package com.pathfinder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.swing.*;

public class MyGdxGame extends ApplicationAdapter
{
	ShapeRenderer shapeRenderer;
	SpriteBatch batch;
	BitmapFont font;
	Texture screen;
	Ball ball;

	@Override
	public void create()
	{
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();

		ball = new Ball();
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(.5f, .5f, .5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		ball.draw(shapeRenderer);
	}

	@Override
	public void dispose()
	{
		shapeRenderer.dispose();
		batch.dispose();
		font.dispose();
	}
}
