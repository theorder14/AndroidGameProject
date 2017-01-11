package com.thaipham.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.thaipham.game.PlatGame;

/**
 * Start screen of our game with a button that starts the game when clicked
 */

public class StartMenu implements Screen {
    Skin skin;
    Stage stage;
    SpriteBatch batch;

    Game game;

    public StartMenu(Game game) {
        create();
        this.game=game;

    }

    public void create() {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();

        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGB888);
        pixmap.setColor(Color.RED);
        pixmap.fill();

        BitmapFont bfont = new BitmapFont();
        skin.add("white", new Texture(pixmap));
        skin.add("default", bfont);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.RED);
        textButtonStyle.font = skin.getFont("default");


        final TextButton textButton = new TextButton("PLAY", textButtonStyle);
        textButton.setPosition(550,300);
        stage.addActor(textButton);


        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new PlayScreen((PlatGame) game));
                stage.dispose();
            }

        });

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
       stage.dispose();
       skin.dispose();

    }
}
