package com.thaipham.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.thaipham.game.PlatGame;
import com.thaipham.game.Sprites.Player1;
import com.thaipham.game.WorldMap.WorldContactListener;
import com.thaipham.game.WorldMap.WorldCreator;

import static com.badlogic.gdx.Gdx.input;

public class PlayScreen implements Screen  {
    private PlatGame game;
    private TextureAtlas atlas;

    // playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;

    //map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;


    //box2d variables
    private World world;
    private WorldCreator creator;


    //representation of our fixture and body
    private Box2DDebugRenderer b2dr;

    private Music music;

    //Sprites
    private Player1 player;

    // maxjumps variables
    private int jumps = 0;
    private int maxJumps = 1;

    /**
     * Create cam,viewport,player and world into our game. We scale pixel per meter to prevent the box2d from falling slow
     *
     * @param game Brings in our game class.
     */
    public PlayScreen(PlatGame game) {

        atlas = new TextureAtlas("player_and_enemies.pack");
        this.game = game;
        //set up box2d camera
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(PlatGame.V_WIDTH / PlatGame.PPM, PlatGame.V_HEIGHT / PlatGame.PPM, gamecam);
        //Load our map and setup our map renderer
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / PlatGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        // set gravity by 10 in y
        world = new World(new Vector2(0, -10), true);
        //allows for debug lines of our box2d world.
        b2dr = new Box2DDebugRenderer();

        //create world objects in game
        creator = new WorldCreator(this);
        //create player in game
        player = new Player1(this);

        world.setContactListener(new WorldContactListener());
        music = PlatGame.manager.get("audio/music/Epoq-Lepidoptera.ogg", Music.class);
        music.setLooping(true);
        music.play();

    }


    /**
     * Loads our images  from textureAtlas  created by TexturePacker
     * @return our texture Atlas images
     */
    public TextureAtlas getAtlas() {
        return atlas;
    }


    public void show() {

    }

    /**
     *Allows the player to only jump once
     */
    public boolean jumping(float dt) {
        if (jumps == maxJumps)
            return true;
        if ( input.justTouched())
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        jumps++;
        return true;
    }


    /**
     *tracks our player with camera and updates
     * @param dt the delta time that takes in 1 step in the physics simulation(60 times per second)
     */
    private void update(float dt) {
        //Get the map properties from our tiled map.
        MapProperties mapProperties = map.getProperties();
        //grab the map width
        int mapWidth = mapProperties.get("width", Integer.class);

        jumping(dt);
        world.step(1 / 60f, 6, 2);
        player.update(dt);

        //keep map within boundaries
        if (player.b2body.getPosition().x >= (0.01f + (gamePort.getWorldWidth() / 2)) && player.b2body.getPosition().x <= mapWidth - gamePort.getWorldWidth() / 2) {
            gamecam.position.x = player.b2body.getPosition().x;
        }

        //update when camera moves
        gamecam.update();
        //only render what the game can see
        renderer.setView(gamecam);
    }

    /**
     * open the box and draws our world into the game
     */
    @Override
    public void render(float delta) {

        if (player.b2body.getLinearVelocity().y == 0)
            jumps = 0;

        update(delta);
        //Clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //render our game map
        renderer.render();


        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        // call method and set new screen
        if (gameOver()) {
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
        // call method and set new screen
        if (gameClear()) {
            game.setScreen(new GameClearScreen(game));
            dispose();
        }


    }

    /**
     *Call method if the player's current state is DEAD
     */
    public boolean gameOver() {
        if (player.currentState == Player1.State.DEAD) {
            return true;
        }
        return false;
    }

    /**
     *Calls method if the player's current state is CLEAR
     */
    public boolean gameClear() {
        if (player.currentState == Player1.State.CLEAR) {
            return true;

        }
        return false;
    }

    /**
     * viewport gets adjusted to the screen size when it changes
     * @param width the width of the screen
     * @param height the height of the screen
     */
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }


    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
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
        map.dispose();
        world.dispose();
        renderer.dispose();
        b2dr.dispose();

    }
}
