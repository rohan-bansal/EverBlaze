package com.rohan.everblaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.rohan.everblaze.Entities.Item;
import com.rohan.everblaze.Entities.Player;
import com.rohan.everblaze.Levels.World;

public class Debugger {

    Player player;

    public Debugger(Player player) {
        this.player = player;
    }

    public void printDebug() {
        if(Gdx.input.isKeyPressed(Input.Keys.C)) {
            Gdx.app.log("DEBUGGER", player.position.x + " : " + player.position.y);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.I)) {
            player.inventory_.printInventory();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.O)) {
            for(Item item : World.onFloor) {
                Gdx.app.log("onFloor Logger", item.name);
            }
        }
    }
}
