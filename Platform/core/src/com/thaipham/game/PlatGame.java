package com.thaipham.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.thaipham.game.Screens.PlayScreen;
import com.thaipham.game.Screens.StartMenu;

/**
 * @Author Thai Pham
 */

public class PlatGame extends Game {
    // Virtual width and height
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;
    public static AssetManager manager;
    //Collision bits
    public static final short GROUND_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short OBJECT_BIT = 4;
    public static final short SPIKES_BIT = 8;
    public static final short FLAG_BIT = 16;
    //container which holds all our img and texture
    public SpriteBatch batch;


    @Override
    public void create () {
        batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("audio/music/Epoq-Lepidoptera.ogg", Music.class);
        manager.finishLoading();
        setScreen(new StartMenu(this));
    }

    @Override
    public void render () {
        super.render();
        manager.update();
    }

}

