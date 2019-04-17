package main.java.com.rohan.everblaze.TileInteraction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import main.java.com.rohan.everblaze.Entities.Item;
import main.java.com.rohan.everblaze.Entities.Player;
import main.java.com.rohan.everblaze.FileUtils.GameManager;

import java.util.ArrayList;

public class Inventory {

    private Player player;
    public Array<Item> inventory;
    public int slotSelected = 1;
    public ArrayList<Sprite> slots;
    public int availableSlot;
    private SpriteBatch slotBatch;

    private Sprite highlighted;

    public Inventory(Player player) {
        this.player = player;
        inventory = new Array<Item>();
        slots = new ArrayList<Sprite>();
        slotBatch = new SpriteBatch();

        highlighted = new Sprite(new Texture(Gdx.files.internal("UI/invSlot2.jpg")));
        loadHotBar();

    }

    public void loadHotBar() {

        Sprite temp;
        int xPos = 300;
        int incr = 1;
        while(incr < 11) {
            temp = new Sprite(new Texture(Gdx.files.internal("UI/invSlot.jpg")));
            temp.setCenter(xPos, 25);
            slots.add(temp);
            xPos += 50;
            incr++;
        }
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void render() {
        detectHotBarSlotSelected();

        slotBatch.begin();
        for(Sprite sprite : slots) {
            if(slots.indexOf(sprite) == slotSelected - 1) {
                highlighted.setCenter(sprite.getX() + 25, sprite.getY() + 25);
                highlighted.draw(slotBatch);
            } else {
                sprite.draw(slotBatch);
            }
        }
        slotBatch.end();
    }

    public void detectHotBarSlotSelected() {
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            slotSelected = 1;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            slotSelected = 2;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            slotSelected = 3;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
            slotSelected = 4;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
            slotSelected = 5;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
            slotSelected = 6;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_7)) {
            slotSelected = 7;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_8)) {
            slotSelected = 8;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_9)) {
            slotSelected = 9;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_0)) {
            slotSelected = 10;
        }
    }

    public void printInventory() {
        for(Item item : inventory) {
            Gdx.app.log("Inventory", item.name);
        }
    }

    public void loadInventory(GameManager manager) {
        inventory = manager.data.getInventory();
        for(Item item : inventory) {
            item.setSprite();
            Gdx.app.log("Inventory", item.name);
        }
        Gdx.app.log("Inventory", "Inventory Loaded, Sprite Textures Set");
        if(inventory.size != 0) {
            availableSlot = inventory.size + 1;
        } else {
            availableSlot = 1;
        }
        Gdx.app.log("Inventory", "Highest Available Slot: " + availableSlot);
    }
}
