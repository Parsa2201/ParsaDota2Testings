package com.pathfinder;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter
{
	ShapeRenderer shapeBatch;
	SpriteBatch batch;
	SpriteBatch fboBatch;
	Texture badlogic;
	FrameBuffer frameBuffer;
	Ball ball;
	public OrthographicCamera fboCamera;

	int screenWidth;
	int screenHeight;
	float aspectRatio;

	int fboWidth=100;
	int fboHeight=100;
	int BINDING_DEPTH_FRAMEBUFFER=3;
	int BINDING_DEFAULT=0;

	void createFrameBuffer()
	{
		if (frameBuffer !=null)
		{
			frameBuffer.dispose();
		}
		frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, fboWidth, fboHeight, false);
		frameBuffer.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		frameBuffer.getColorBufferTexture().bind(BINDING_DEPTH_FRAMEBUFFER);
		Gdx.graphics.getGL20().glActiveTexture(GL20.GL_TEXTURE0 + BINDING_DEFAULT); //make sure this is set to 0 for batch draws
		fboCamera=new OrthographicCamera();
		fboCamera.setToOrtho(false, fboWidth, fboHeight);
		fboCamera.update();
	}

	@Override
	public void create()
	{
		batch = new SpriteBatch();
		fboBatch = new SpriteBatch();
		shapeBatch=new ShapeRenderer();
		ball = new Ball();
		badlogic = new Texture("badlogic.jpg");

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		aspectRatio = (float) screenWidth / screenHeight;

		fboWidth *= aspectRatio;
	}

	@Override
	public void render()
	{
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();

//I had some initialisation problems with the fbo when trying to create a framebuffer. Need to be absolutely certain
		//size is correct and opengl context is set up before creating the framebuffer (as far as I remember). This if statement and the
		//createFrameBuffer function works for me and will allow you to change the size of fboWidth/fboHeight on the fly (though will cause stutters if you do)
		if ((frameBuffer ==null) || (frameBuffer.getWidth()!=fboWidth) || (frameBuffer.getHeight()!=fboHeight))
		{
			createFrameBuffer();
		}

		//start drawing to a small framebuffer, that we will then scale up to create a pixelated effect
		frameBuffer.begin();


		ScreenUtils.clear(0, 0, 0, 0.5f);
		shapeBatch.begin(ShapeRenderer.ShapeType.Line);

		shapeBatch.setProjectionMatrix(fboCamera.combined);
		Gdx.gl.glDisable(GL20.GL_BLEND);
		ball.draw(shapeBatch);

		shapeBatch.end();

		fboBatch.begin();
		fboBatch.draw(badlogic, 0, 0);
		fboBatch.end();


		frameBuffer.end();


		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		batch.draw(frameBuffer.getColorBufferTexture(), 20, 20, width, height, 0, 0, 1, 1);
		ball.drawInformation(batch);
		batch.end();
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		shapeBatch.dispose();
		frameBuffer.dispose();
	}
}
