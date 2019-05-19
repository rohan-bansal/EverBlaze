package main.java.com.rohan.everblaze.Entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Blob extends Enemy {

    public Blob(String name, String type, int x, int y, MovementScript script) {
        super(name, type, x, y, script);
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    @Override
    public void attack() {
    }
}
