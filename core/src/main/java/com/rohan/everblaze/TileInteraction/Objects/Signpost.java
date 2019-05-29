package main.java.com.rohan.everblaze.TileInteraction.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Signpost {

    public Sprite sprite;
    private String text;

    public Signpost(int x, int y, String text) {
        sprite = new Sprite(new Texture(Gdx.files.internal("itemSprites/signpost.png")));
        sprite.setPosition(x, y);
        this.text = text;
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public String getText() {
        return text;
    }
}
