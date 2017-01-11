package com.thaipham.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.thaipham.game.PlatGame;


/**
 * This class clear Screen and draw
 */
public class GameClearScreen implements Screen {
    private Viewport viewport;
    private Stage stage;

    private Game game;

    public GameClearScreen(Game game){
        this.game = game;
        viewport = new FitViewport(PlatGame.V_WIDTH, PlatGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((PlatGame) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);
        Label gameOverLabel = new Label("GAME CLEAR", font);
        table.add(gameOverLabel).expandX();

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    /**
     * Start new PlayScreen if it's touched
     */
    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            game.setScreen(new StartMenu(game));
            dispose();
        }
        Gdx.gl.glClearColor(1, 0, 0, 1);
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
    }
}
