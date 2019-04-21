package main.java.com.rohan.everblaze.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import main.java.com.rohan.everblaze.Levels.World;

public class Item implements Json.Serializable {

    public String name;
    public String spritePath;
    public Sprite sprite;
    public String type;
    public String description;
    public String position;

    public Item(String name, String path, String type, String description) {
        this.name = name;
        this.spritePath = path;
        this.type = type;
        this.description = description;
        setSprite();
    }

    public Item() {
    }

    public void render(SpriteBatch batch) {
        this.sprite.draw(batch);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void loadCoords(int x, int y) {
        this.sprite.setCenter(x, y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpritePath() {
        return spritePath;
    }

    public void setSpritePath(String spritePath) {
        this.spritePath = spritePath;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.sprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));
    }

    public void setSprite() {
        this.sprite = new Sprite(new Texture(Gdx.files.internal(spritePath)));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void write(Json json) {
        json.writeValue("name", name);
        json.writeValue("spritePath", spritePath);
        json.writeValue("type", type);
        json.writeValue("description", description);
        if(World.onFloor.contains(this)) {
            json.writeValue("position", new Vector2(sprite.getX(), sprite.getY()));
        }
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        name = jsonData.getString("name");
        spritePath = jsonData.getString("spritePath");
        type = jsonData.getString("type");
        description = jsonData.getString("description");
    }
}
