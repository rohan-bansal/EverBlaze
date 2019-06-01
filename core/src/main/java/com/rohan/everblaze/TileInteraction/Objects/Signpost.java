package main.java.com.rohan.everblaze.TileInteraction.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import main.java.com.rohan.everblaze.Effects.ScreenText;

public class Signpost {

    public Sprite sprite, signBack;

    private String text;

    public ScreenText signManager = new ScreenText();


    public Signpost(int x, int y, String text) {
        sprite = new Sprite(new Texture(Gdx.files.internal("itemSprites/signpost.png")));
        sprite.setPosition(x, y);
        this.text = text;

        signBack = new Sprite(new Texture(Gdx.files.internal("UI/signbackground.png")));
        signBack.setCenter(500, 400);

        signManager.setColor(Color.BLACK);
        signManager.setSize(3f);
        signManager.setText(getText());
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public String getText() {
        return text;
    }

    public void drawText(SpriteBatch hudBatch) {
        signBack.draw(hudBatch);
        GlyphLayout layout = new GlyphLayout();
        layout.setText(signManager.drawer, getText());
        signManager.setPosition(new Vector2((signBack.getX() + signBack.getWidth() / 2) - layout.width / 2, signBack.getY() + 230));
        signManager.renderOnlyIf(hudBatch);
    }
}
