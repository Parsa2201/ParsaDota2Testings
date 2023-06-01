package com.pathfinder;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

import java.util.ArrayList;

public class Information
{
    private FreeTypeFontGenerator generator;
    private ArrayList<Info> information = new ArrayList<>();
    private int lastChangedIndex = 0;

    public Information()
    {
        generator = new FreeTypeFontGenerator(new FileHandle("River Adventurer.ttf"));
    }

    private BitmapFont font(int size)
    {
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = size;
        return generator.generateFont(parameter);
    }

    public void draw(Batch batch)
    {
        float maxWidth = 400;
//        for(Info info : information)
//        {
//            GlyphLayout layout = new GlyphLayout(font(20), info.toString());
//            float width = layout.width;
//            if(width > maxWidth)
//                maxWidth = width;
//        }

        for(Info info : information)
            font(20).draw(batch, info.toString(), Gdx.graphics.getWidth() - maxWidth, Gdx.graphics.getHeight() - 20 * info.index);

        //font(20).draw(batch, "Hello World!", 0, 0);
    }

    public void addInfo(String info)
    {
        information.add(new Info(info, "", information.size()));
    }

    public void changeInfoValue(String value)
    {
        information.get(lastChangedIndex).changeValue(value);
        lastChangedIndex++;

        if(lastChangedIndex >= information.size())
            lastChangedIndex = 0;
    }

    public void changeInfoValue(float value)
    {
        changeInfoValue(String.valueOf(value));
    }
}

class Info
{
    public String argument;
    public String value;
    public int index;

    public Info(String argument, String value, int index)
    {
        this.argument = argument;
        this.value = value;
        this.index = index;
    }

    public void changeValue(String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return argument + " : " + value;
    }
}