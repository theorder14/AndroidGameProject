package com.thaipham.game.Sprites.TilesObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.thaipham.game.PlatGame;
import com.thaipham.game.Screens.PlayScreen;
import com.thaipham.game.Sprites.Player1;


/**
 * This class calls method when player collide with the spikes or falls down out of map.
 * The player will have a state of DEAD which will in turn call GameOver method and it will set new screen.
 */
public class Spikes extends com.thaipham.game.Sprites.TilesObjects.TileObject {
    public Spikes(PlayScreen screen, Rectangle rect){
        super(screen, rect);
        fixture.setUserData(this);
        setCategoryFilter(PlatGame.SPIKES_BIT);

    }

    /**
     * call die method from Class Player1.
     */
    @Override
    public void onHit(Player1 player1) {
        player1.die();
        Gdx.app.log("spikes","collide");
    }

}
