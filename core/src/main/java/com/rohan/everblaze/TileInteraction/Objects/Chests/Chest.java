package main.java.com.rohan.everblaze.TileInteraction.Objects.Chests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import main.java.com.rohan.everblaze.Levels.World;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Item;
import main.java.com.rohan.everblaze.TileInteraction.Objects.ItemStack;

import java.util.ArrayList;

public class Chest implements Json.Serializable {

    public TextureRegion closed;
    public TextureRegion open;
    public TextureRegion currentFrame;

    public Vector2 position;
    public int chestState = 0;

    public SpriteBatch chestBatch;

    private Item dropItem;

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
        id = World.storageID;
        World.storageID += 1;
        chestBatch = new SpriteBatch();

        closed = new TextureRegion(new Texture(Gdx.files.internal("Objects/Chest/chest_f0.png")));
        open = new TextureRegion(new Texture(Gdx.files.internal("Objects/Chest/chest_f2.png")));
    }

    public Chest() {
        position = new Vector2();
        id = World.storageID;
        World.storageID += 1;
        chestBatch = new SpriteBatch();

        closed = new TextureRegion(new Texture(Gdx.files.internal("Objects/Chest/chest_f0.png")));
        open = new TextureRegion(new Texture(Gdx.files.internal("Objects/Chest/chest_f2.png")));
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

    @Override
    public void write(Json json) {
        json.writeValue("x", position.x);
        json.writeValue("y", position.y);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        position.x = jsonData.getInt("x");
        position.y = jsonData.getInt("y");
        Gdx.app.log("Chest", id + "");
    }
}
