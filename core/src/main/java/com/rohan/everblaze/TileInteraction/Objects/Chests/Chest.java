package main.java.com.rohan.everblaze.TileInteraction.Objects.Chests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import main.java.com.rohan.everblaze.Levels.World;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Item;
import main.java.com.rohan.everblaze.TileInteraction.Objects.ItemStack;

import java.util.ArrayList;

public class Chest {

    public TextureRegion closed;
    public TextureRegion open;
    public TextureRegion currentFrame;

    public Vector2 position;
    public int chestState = 0;

    public SpriteBatch chestBatch;

    private Item dropItem;

    private ArrayList<ItemStack> chestInventory;
    private ArrayList<Sprite> slots = new ArrayList<Sprite>();
    private Sprite inventorySprite;

    public int id;

    public Chest(int ID, int x, int y, Item item) {

        position = new Vector2(x, y);

        dropItem = item;
        this.id = ID;

        closed = new TextureRegion(new Texture(Gdx.files.internal("Objects/Chest/chest_f0.png")));
        open = new TextureRegion(new Texture(Gdx.files.internal("Objects/Chest/chest_f2.png")));
    }

    public Chest(float x, float y) {

        position = new Vector2(x, y);
        this.chestInventory = new ArrayList<ItemStack>();
        id = World.storageID;
        World.storageID += 1;
        chestBatch = new SpriteBatch();

        closed = new TextureRegion(new Texture(Gdx.files.internal("Objects/Chest/chest_f0.png")));
        open = new TextureRegion(new Texture(Gdx.files.internal("Objects/Chest/chest_f2.png")));
    }

    public Chest() {
    }

    public Item getDropItem() {
        return dropItem;
    }

    public Rectangle getRect() {
        return new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public void render(SpriteBatch batch) {

        if(chestState == 0) {
            currentFrame = closed;
        } else if(chestState == 1) {
            currentFrame = open;
        }

        batch.draw(currentFrame, position.x, position.y);
    }
}
