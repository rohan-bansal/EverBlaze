package main.java.com.rohan.everblaze.FileUtils;

import com.badlogic.gdx.math.Vector2;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Chest;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Item;
import main.java.com.rohan.everblaze.TileInteraction.Objects.ItemStack;

import java.util.ArrayList;

public class GameData {

    private boolean musicOn;
    private boolean VsyncOn;
    private boolean invertControlsOn;
    private boolean effectsOn;
    private Vector2 playerPosition;
    private ArrayList<ItemStack> inventory;
    private ArrayList<Item> onFloor;
    private ArrayList<Integer> openedChests;
    private int slotSelected;
    private int health;
    private int hearts;
    private int coins;

    public ArrayList<Integer> getOpenedChests() {
        return openedChests;
    }

    public void setOpenedChests(ArrayList<Integer> openedChests) {
        this.openedChests = openedChests;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    private ArrayList<String> enemiesDead;

    public ArrayList<String> getEnemiesDead() {
        return enemiesDead;
    }

    public void setEnemiesDead(ArrayList<String> enemiesDead) {
        this.enemiesDead = enemiesDead;
    }

    public ArrayList<Item> getOnFloor() {
        return onFloor;
    }

    public void setOnFloor(ArrayList<Item> onFloor) {
        this.onFloor = onFloor;
    }

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

    public ArrayList<ItemStack> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<ItemStack> inventory) {
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