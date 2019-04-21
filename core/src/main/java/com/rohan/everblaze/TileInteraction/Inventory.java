package main.java.com.rohan.everblaze.TileInteraction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import main.java.com.rohan.everblaze.Entities.Item;
import main.java.com.rohan.everblaze.Entities.Player;
import main.java.com.rohan.everblaze.FileUtils.GameManager;
import main.java.com.rohan.everblaze.Levels.World;

import java.util.ArrayList;

public class Inventory {

    private Player player;
    public ArrayList<Item> inventory;
    public int slotSelected = 1;
    public ArrayList<Sprite> slots;
    private SpriteBatch slotBatch;
    private float slotX = 300;

    private BitmapFont nameDrawer = new BitmapFont();

    private Sprite highlighted;

    public Inventory(Player player) {
        this.player = player;
        inventory = new ArrayList<Item>();
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
        Gdx.app.log("Inventory", item.name + " added to inventory");
        item.setSprite();
        item.sprite.setCenter(slotX, 25);
        slotX += 50;
        inventory.add(item);
    }

    public void render() {
        detectKeyPressed();

        slotBatch.begin();
        for(int y = 0; y < slots.size(); y++) {
            if(slots.indexOf(slots.get(y)) == slotSelected - 1) {
                highlighted.setCenter(slots.get(y).getX() + 25, slots.get(y).getY() + 25);
                highlighted.draw(slotBatch);
                try {
                    nameDrawer.draw(slotBatch, inventory.get(y).name, inventory.get(y).sprite.getX() - 16, inventory.get(y).sprite.getY() + 60);
                } catch(Exception e) {
                }
            } else {
                slots.get(y).draw(slotBatch);
            }
        }
        for(int x = 0; x < inventory.size(); x++) {
            inventory.get(x).render(slotBatch);
        }
        slotBatch.end();
    }

    public void detectKeyPressed() {
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
        } else
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            pickUpItem();
        } else
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            dropItem();
        }
    }

    private void pickUpItem() {
        if (World.detector.itemCollision(false) != null) {
            Item item = World.detector.itemCollision(false);
            Gdx.app.log("Inventory", "Picking Up Item : " + item.name);

            addItem(item);
            World.onFloor.remove(item);
        }
    }

    private void dropItem() {
        if(inventory.size() != 0) {
            if(slotSelected <= inventory.size()) {
                Item item_ = inventory.get(slotSelected - 1);
                Gdx.app.log("Inventory", "Dropping Item : " + item_.name);

                item_.setSprite();
                item_.sprite.setCenter(player.position.x, player.position.y);
                item_.sprite.setSize(16, 16);

                World.onFloor.add(item_);
                inventory.remove(item_);

                refreshInventory();
            }
        }
    }

    public void printInventory() {
        for(Item item : inventory) {
            Gdx.app.log("Inventory", item.name);
        }
    }

    private void refreshInventory() {
        slotX = 300;
        for(Item item : inventory) {
            item.setSprite();
            item.sprite.setCenter(slotX, 25);
            slotX += 50;
        }
    }

    public void loadInventory(GameManager manager) {
        inventory = manager.data.getInventory();
        refreshInventory();
        Gdx.app.log("Inventory", "Inventory Loaded, Sprite Textures Set");
    }
}
