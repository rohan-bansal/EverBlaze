package main.java.com.rohan.everblaze.TileInteraction.Objects;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ItemDurabilityBar {

    public String name;
    private Item item;
    private int durability;
    private ShapeRenderer durRenderer;

    public ItemDurabilityBar(String name, int durability, Item INVitem) {
        this.item = INVitem;
        this.durability = durability;
        this.name = name;
        durRenderer = new ShapeRenderer();
    }

    public void render(SpriteBatch batch) {
        durRenderer.begin(ShapeRenderer.ShapeType.Filled);
        durRenderer.setColor(Color.GREEN);
        durRenderer.rect(item.sprite.getX(), item.sprite.getY(), 20, 5);
        durRenderer.end();
    }

}