package com.thaipham.game.WorldMap;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.thaipham.game.PlatGame;
import com.thaipham.game.Sprites.Player1;
import com.thaipham.game.Sprites.TilesObjects.TileObject;


public class WorldContactListener implements ContactListener {
    /**
     * Calls method whenever Player collide with another object bit
     * @param contact the contact of fixtures
     */
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();
        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
            switch (cDef){
                case PlatGame.PLAYER_BIT | PlatGame.SPIKES_BIT:
                case PlatGame.PLAYER_BIT | PlatGame.FLAG_BIT:
                    if(fixA.getFilterData().categoryBits == PlatGame.PLAYER_BIT)
                        ((TileObject) fixB.getUserData()).onHit((Player1) fixA.getUserData());
                    else
                        ((TileObject) fixA.getUserData()).onHit((Player1) fixB.getUserData());
                    break;

            }
        }


    @Override
        public void endContact(Contact contact) {


    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
