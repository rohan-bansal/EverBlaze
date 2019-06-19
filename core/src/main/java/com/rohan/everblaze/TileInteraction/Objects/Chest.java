package main.java.com.rohan.everblaze.TileInteraction.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import main.java.com.rohan.everblaze.Levels.World;

public class Chest {

    public TextureRegion closed;
    public TextureRegion open;
    public TextureRegion currentFrame;

    private Vector2 position;
    public int chestState = 0;
    private float stateTime = 0f;

    private Item dropItem;

    public Chest(int x, int y, Item item) {

        position = new Vector2(x, y);
        Gdx.app.log("Chest", "Position : " + position.x + " : " + position.y);

        dropItem = item;

        closed = new TextureRegion(new Texture(Gdx.files.internal("Entities/Chest/chest_f0.png")));
        open = new TextureRegion(new Texture(Gdx.files.internal("Entities/Chest/chest_f2.png")));
    }

    public Rectangle getRect() {
        return new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        if(chestState == 0) {
            currentFrame = closed;
        } else if(chestState == 1) {
            currentFrame = open;
        }

        batch.draw(currentFrame, position.x, position.y);
    }

    public void displayContents() {
        dropItem.setSprite();
        dropItem.sprite.setSize(16, 16);
        dropItem.sprite.setCenter(position.x + 24, position.y + 24);
        World.onFloor.add(dropItem);
    }
}
