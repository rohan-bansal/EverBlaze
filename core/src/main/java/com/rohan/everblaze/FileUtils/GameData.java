package main.java.com.rohan.everblaze.FileUtils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import main.java.com.rohan.everblaze.Entities.Item;

import java.util.ArrayList;

public class GameData {

    private boolean musicOn;
    private boolean VsyncOn;
    private boolean invertControlsOn;
    private boolean effectsOn;
    private Vector2 playerPosition;
    private ArrayList<Item> inventory;
    private int slotSelected;
    private int health;
    private int hearts;

    public int getSlotSelected() {
        return slotSelected;
    }

    public void setSlotSelected(int slotSelected) {
        this.slotSelected = slotSelected;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public Vector2 getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(Vector2 playerPosition) {
        this.playerPosition = playerPosition;
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }

    public boolean isVsyncOn() {
        return VsyncOn;
    }

    public void setVsyncOn(boolean vsyncOn) {
        VsyncOn = vsyncOn;
    }

    public boolean isInvertControlsOn() {
        return invertControlsOn;
    }

    public void setInvertControlsOn(boolean invertControlsOn) {
        this.invertControlsOn = invertControlsOn;
    }

    public boolean isEffectsOn() {
        return effectsOn;
    }

    public void setEffectsOn(boolean effectsOn) {
        this.effectsOn = effectsOn;
    }
}