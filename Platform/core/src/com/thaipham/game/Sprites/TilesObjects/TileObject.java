package com.thaipham.game.Sprites.TilesObjects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.thaipham.game.PlatGame;
import com.thaipham.game.Screens.PlayScreen;
import com.thaipham.game.Sprites.Player1;



public abstract class TileObject {
    public World world;
    public TiledMap map;
    public Rectangle rect;
    public Body body;
    public Fixture fixture;

    /**
     * Fixtures of TilesObjects
     * @param screen the screen from PlayScreen
     * @param rect takes in rectangle
     */
   public TileObject(PlayScreen screen, Rectangle rect){
       this.world = screen.getWorld();
       this.map = screen.getMap();
       this.rect = rect;

       BodyDef bdef = new BodyDef();
       FixtureDef fdef= new FixtureDef();
       PolygonShape shape = new PolygonShape();

       bdef.type = BodyDef.BodyType.StaticBody;
       bdef.position.set((rect.getX()+ rect.getWidth() / 2) / PlatGame.PPM , (rect.getY() + rect.getHeight() / 2)/ PlatGame.PPM);

       body = world.createBody(bdef);

       shape.setAsBox(rect.getWidth() / 2 / PlatGame.PPM , rect.getHeight() / 2 / PlatGame.PPM);
       fdef.shape = shape;
       body.createFixture(fdef);
       fixture = body.createFixture(fdef);
   }


     public abstract void onHit(Player1 player1);

    /**
     * method to differentiate the bits
     * @param filterBit filter of the bits
     */
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }




}




