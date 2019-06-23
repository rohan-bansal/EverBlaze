package main.java.com.rohan.everblaze.TileInteraction.Objects.Chests;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import main.java.com.rohan.everblaze.Levels.World;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Item;


public class TreasureChest extends Chest {

    public TextureRegion currentFrame;

    private Vector2 position;

    private Item dropItem;

    public int id;

    public TreasureChest(int ID, int x, int y, Item item) {

        super(ID, x ,y, item);

        position = new Vector2(x, y);

        dropItem = item;
        this.id = ID;
    }

    public Item getDropItem() {
        return super.getDropItem();
    }

    public Rectangle getRect() {
        return super.getRect();
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    public void displayContents() {
        dropItem.setSprite();
        dropItem.sprite.setSize(16, 16);
        dropItem.sprite.setCenter(position.x + 24, position.y + 24);
        World.onFloor.add(dropItem);
    }
}
