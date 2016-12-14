package com.thaipham.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.thaipham.game.PlatGame;
import com.thaipham.game.Screens.PlayScreen;



public class Player1 extends Sprite {
    public enum State {STANDING, RUNNING, DEAD, CLEAR}

    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private Animation playerRun;

    private float stateTimer;

    private boolean playerIsDead;
    private boolean playerClear;


    /**
     *This is our player1 class.We have different texture for each states
     * Only running and standing states have textures currently
     * @param screen takes in the PlayScreen
     */
    public Player1(PlayScreen screen) {
        // gets our Atlas and find the region by name
        super(screen.getAtlas().findRegion("p1_stand"));
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

        //running textures
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(getTexture(), 51, 158, 72, 97));
        frames.add(new TextureRegion(getTexture(), 125, 158, 72, 97));
        frames.add(new TextureRegion(getTexture(), 199, 158, 72, 97));
        frames.add(new TextureRegion(getTexture(), 273, 158, 72, 97));
        playerRun = new Animation(0.1f, frames);
        frames.clear();


        // texture of player standing
        playerStand = new TextureRegion(getTexture(), 440, 64, 66, 92);
        definePlayer();
        setBounds(0, 0, 16 / PlatGame.PPM, 16 / PlatGame.PPM);
        setRegion(playerStand);

    }

    /**
     * This method sets the position of the b2body and also make the player move automatically at the x axis
     * @param dt is the delta time
     */
    public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        if (b2body.getLinearVelocity().x <= 2)
            b2body.applyLinearImpulse(new Vector2(0.05f, 0), b2body.getWorldCenter(), true);

    }

    /**
     * Behavior of the player. We have texture for run and stand states
     * @return our final adjusted frame
     */
    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case DEAD:
            case CLEAR:
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = playerStand;
                break;
        }
        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        return region;

    }

    /**
     * Get the states of player weather he's dead or collides with a certain object
     * If player is positive or negative in the x axis he is running. He stands if no other states return
     * @return the different states
     */
    public State getState() {
        if (playerIsDead)
            return State.DEAD;
        else if(playerClear)
            return State.CLEAR;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void die() {

        playerIsDead = true;

    }

    public void clear(){
        playerClear = true;
    }

    /**
     * Gives our player dynamic b2body and shape
     */
    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / PlatGame.PPM ,32 / PlatGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(8 / PlatGame.PPM);
        fdef.filter.categoryBits = PlatGame.PLAYER_BIT;
        fdef.filter.maskBits = PlatGame.Ground_BIT |
                PlatGame.OBJECT_BIT|
                PlatGame.FLAG_BIT |
                PlatGame.SPIKES_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
        fdef.isSensor = true;


    }


}
