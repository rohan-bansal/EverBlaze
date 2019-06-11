package main.java.com.rohan.everblaze.TileInteraction.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ItemDurabilityBar {

    private ShapeRenderer durRenderer;

    public ItemDurabilityBar() {
        durRenderer = new ShapeRenderer();
    }

    public ShapeRenderer getDurRenderer() {
        return durRenderer;
    }

    public void render(Item item) {
        float durabilityFraction = (float) item.durability / item.baseDur;
        if(durabilityFraction >= 0.35 && durabilityFraction <= 0.6) {
            durRenderer.setColor(Color.YELLOW);
        } else if(durabilityFraction > 0.6) {
            durRenderer.setColor(Color.GREEN);
        } else if(durabilityFraction < 0.35) {
            durRenderer.setColor(Color.RED);
        }
        durRenderer.rect(item.sprite.getX() + 5, item.sprite.getY() - 5, durabilityFraction * 25, 2);
    }

}