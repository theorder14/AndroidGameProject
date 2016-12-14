package com.thaipham.game.Sprites.TilesObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.thaipham.game.PlatGame;
import com.thaipham.game.Screens.PlayScreen;
import com.thaipham.game.Sprites.Player1;

/**
 * This class calls method when player collide with the flag or exit sign.
 * The player will have a state of CLEAR which will in turn call GameClear method and it will set new screen.
 */

public class Flag extends com.thaipham.game.Sprites.TilesObjects.TileObject {
    public Flag(PlayScreen screen,Rectangle rect){
        super(screen,rect);
        fixture.setUserData(this);
        setCategoryFilter(PlatGame.FLAG_BIT);

    }

    /**
     * call clear method from Class Player1
     */
    @Override
    public void onHit(Player1 player1) {
        player1.clear();
        Gdx.app.log("flag","collision");

    }

}
