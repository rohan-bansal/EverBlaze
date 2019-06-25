package main.java.com.rohan.everblaze.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.math.Vector2;
import main.java.com.rohan.everblaze.Entities.Player;

public class ScreenText {

    public BitmapFont drawer;
    private String text;
    private Vector2 position;
    private float elapsed = 0;

    public ScreenText() {

        drawer = new BitmapFont();
        setColor(Color.BLACK);
        setSize(4);
    }

    public ScreenText(String font, String fontfile) {

        drawer = new BitmapFont(Gdx.files.internal(font), Gdx.files.internal(fontfile), false);
        setSize(0.5f);
    }

    public void setColor(Color color) {
        drawer.setColor(color);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void resetTimer() {
        elapsed = 0;
    }

    public void setSize(float size) {
        drawer.getData().setScale(size);
    }

    public void render(SpriteBatch batch, float time) {
        elapsed += Gdx.graphics.getDeltaTime();

        if(text != null) {
            if(elapsed < time) {
                batch.begin();
                drawer.draw(batch, this.text, position.x, position.y);
                batch.end();
            }
        }
    }

    public void renderOnlyIf(SpriteBatch batch) {
        if(text != null) {
            drawer.draw(batch, this.text, position.x, position.y);
        }
    }
}
