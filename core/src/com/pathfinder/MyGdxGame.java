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

	CityGraph cityGraph;
	GraphPath<City> cityPath;
	Agent agent;

	@Override
	public void create()
	{
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();

		cityGraph = new CityGraph();


//		City startCity = new City(300, 250, "S");
//		City bCity = new City(300, 350, "B");
//		City aCity = new City(200, 350, "A");
//		City cCity = new City(400, 350, "C");
//		City dCity = new City(200, 250, "D");
//		City fCity = new City(100, 250, "F");
//		City eCity = new City(400, 250, "E");
//		City hCity = new City(300, 150, "H");
//		City gCity = new City(200, 150, "G");
//		City iCity = new City(200, 50, "I");
//		City jCity = new City(300, 50, "J");
//		City kCity = new City(400, 50, "K");
//		City goalCity = new City(400, 150, "Z");
//
//		cityGraph.addCity(startCity);
//		cityGraph.addCity(bCity);
//		cityGraph.addCity(aCity);
//		cityGraph.addCity(cCity);
//		cityGraph.addCity(dCity);
//		cityGraph.addCity(fCity);
//		cityGraph.addCity(eCity);
//		cityGraph.addCity(hCity);
//		cityGraph.addCity(gCity);
//		cityGraph.addCity(iCity);
//		cityGraph.addCity(jCity);
//		cityGraph.addCity(kCity);
//		cityGraph.addCity(goalCity);
//
//		cityGraph.connectCities(startCity, bCity);
//		cityGraph.connectCities(bCity, aCity);
//		cityGraph.connectCities(bCity, cCity);
//		cityGraph.connectCities(startCity, dCity);
//		cityGraph.connectCities(dCity, fCity);
//		cityGraph.connectCities(startCity, hCity);
//		cityGraph.connectCities(hCity, gCity);
//		cityGraph.connectCities(gCity, iCity);
//		cityGraph.connectCities(iCity, jCity);
//		cityGraph.connectCities(jCity, kCity);
//		cityGraph.connectCities(kCity, goalCity);
//		cityGraph.connectCities(startCity, eCity);
//		cityGraph.connectCities(eCity, goalCity);
//
//		cityPath = cityGraph.findPath(startCity, goalCity);
//
//		agent = new Agent(cityGraph, cityGraph.cities.get(0));
//		agent.setGoal(cityGraph.cities.get(cityGraph.cities.size - 1));

		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 10; j++)
				cityGraph.addCity(new City(i * 50 + 100, j * 50 + 100, i + "," + j));

		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 9; j++)
				cityGraph.connectCities(cityGraph.cities.get(i * 10 + j), cityGraph.cities.get(i * 10 + j + 1));

		for(int i = 0; i < 9; i++)
			for(int j = 0; j < 10; j++)
				cityGraph.connectCities(cityGraph.cities.get(i * 10 + j), cityGraph.cities.get((i + 1) * 10 + j));

		cityPath = cityGraph.findPath(cityGraph.cities.get(0), cityGraph.cities.get(99));

		agent = new Agent(cityGraph, cityGraph.cities.get(0));
		agent.setGoal(cityGraph.cities.get(99));
	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(.5f, .5f, .5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		for (Street street : cityGraph.streets) {
			street.render(shapeRenderer);
		}

		// Draw all cities blue
		for (City city : cityGraph.cities) {
			city.render(shapeRenderer, batch, font, false);
		}

		// Draw cities in path green
		for (City city : cityPath) {
			city.render(shapeRenderer, batch, font, true);
		}

		agent.step();
		agent.render(shapeRenderer, batch);
	}

	@Override
	public void dispose()
	{
		shapeRenderer.dispose();
		batch.dispose();
		font.dispose();
	}
}
