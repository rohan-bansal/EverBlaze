package main.java.com.rohan.everblaze.TileInteraction.Objects.Chests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import main.java.com.rohan.everblaze.Classifier;
import main.java.com.rohan.everblaze.Levels.InventoryOverlay;
import main.java.com.rohan.everblaze.Levels.World;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Item;
import main.java.com.rohan.everblaze.TileInteraction.Objects.ItemStack;

import java.util.ArrayList;

public class StorageChest extends Chest implements Json.Serializable {

    public TextureRegion currentFrame;
    private Sprite highlighted = new Sprite(new Texture(Gdx.files.internal("UI/invSlot2.jpg")));

    private SpriteBatch chestBatch;

    private ArrayList<ItemStack> chestInventory;
    private ArrayList<Sprite> slots = new ArrayList<Sprite>();
    private Sprite inventorySprite;
    BitmapFont nameDrawer = new BitmapFont();
    BitmapFont itemCounter = new BitmapFont();


    public StorageChest(float x, float y) {
        super(x, y);


        this.chestInventory = new ArrayList<ItemStack>();
        chestBatch = new SpriteBatch();

        chestInventory.add(new ItemStack(new Item("Pie", "itemSprites/tile022.png", Classifier.Food, 1, "Restores 10 hearts."), 1));

        inventorySprite = new Sprite(new Texture(Gdx.files.internal("UI/HUD/Inventory/chestInventory.png")));
        inventorySprite.setCenter(320, 470);

        refreshInventory();
    }

    private void refreshInventory() {
        int xPos = 180;
        int yPos = 540;
        int incr = 1;
        while(incr < 21) {
            Sprite temp = new Sprite(new Texture(Gdx.files.internal("UI/invSlot.jpg")));
            temp.setCenter(xPos, yPos);
            slots.add(temp);

            try {
                ItemStack item = chestInventory.get(incr - 1);
                item.stackedItem.setSprite();
                item.stackedItem.sprite.setCenter(xPos, yPos);
            } catch(IndexOutOfBoundsException e) {
            }

            xPos += 70;
            if(xPos > 510) {
                xPos = 180;
                yPos -= 70;
            }
            incr++;
        }
    }

    public StorageChest() {
    }

    private void refreshChest(float x, float y) {
        super.position.x = x;
        super.position.y = y;
        this.chestInventory = new ArrayList<ItemStack>();
        chestBatch = new SpriteBatch();

        inventorySprite = new Sprite(new Texture(Gdx.files.internal("UI/HUD/Inventory/chestInventory.png")));
        inventorySprite.setCenter(320, 470);

        refreshInventory();
    }

    public Rectangle getRect() {
        return super.getRect();
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    public void displayInventory(InventoryOverlay overlay) {
        chestBatch.begin();
        inventorySprite.draw(chestBatch);
        overlay.drawInventory(chestBatch, highlighted, nameDrawer, chestInventory);
        for(Sprite slot : slots) {
            if(slot.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                highlighted.setPosition(slot.getX(), slot.getY());
                highlighted.draw(chestBatch);
                if(Gdx.input.justTouched()) {
                    try {
                        ItemStack item = chestInventory.get(slots.indexOf(slot));
                        chestInventory.remove(chestInventory.get(slots.indexOf(slot)));
                        World.detector.player.inventory_.addItem(item.stackedItem, item.count);
                    } catch (IndexOutOfBoundsException e) {
                    }
                }
            } else {
                slot.draw(chestBatch);
            }
        }
        for(ItemStack item : chestInventory) {
            item.stackedItem.render(chestBatch);
            if(item.stackedItem.sprite.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                GlyphLayout layout = new GlyphLayout();
                layout.setText(nameDrawer, item.stackedItem.name);
                nameDrawer.draw(chestBatch, item.stackedItem.name, (item.stackedItem.sprite.getX() +
                        item.stackedItem.sprite.getWidth() / 2) - layout.width / 2, item.stackedItem.sprite.getY() + 60);
            }
            if(item.count > 1) {
                itemCounter.draw(chestBatch, item.count + "", item.stackedItem.sprite.getX() + 25, item.stackedItem.sprite.getY() + 8);
            }

        }
        chestBatch.end();
        refreshInventory();
    }

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("chestInventory", chestInventory.toArray());
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        refreshChest(super.position.x, super.position.y);
        JsonValue inv = jsonData.get("chestInventory");

        for(JsonValue value : inv) {
            JsonValue item_ = value.get("stackedItem");
            chestInventory.add(new ItemStack(new Item(item_.get("name").asString(), item_.get("spritePath").asString(), item_.get("type").asString(), item_.get("durability").asInt(), item_.get("description").asString()), value.get("count").asInt()));
        }
        refreshInventory();
    }
}
