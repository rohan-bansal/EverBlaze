package main.java.com.rohan.everblaze.TileInteraction.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import main.java.com.rohan.everblaze.Levels.World;


public class Item implements Json.Serializable {

    public String name;
    public String spritePath;
    public Sprite sprite;
    public String type;
    public String description;
    public int damage;
    public int durability;
    public int baseDur;


    public Item(Item another) {
        this.name = another.name;
        this.spritePath = another.spritePath;
        this.type = another.type;
        this.description = another.description;
        this.durability = another.durability;
        this.baseDur = another.durability;
        this.damage = another.damage;

        setSprite();
    }

    public Item(String name, String path, String type, int durability, String description, int... weapon) {
        this.name = name;
        this.spritePath = path;
        this.type = type;
        this.description = description;
        this.durability = durability;
        this.baseDur = durability;

        if(weapon.length > 0 && weapon[0] != 0) {
            this.damage = weapon[0];
        } else {
            this.damage = 0;
        }
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
        json.writeValue("durability", durability);
        if(damage != 0) {
            json.writeValue("damage", damage);
        }
        if(World.onFloor.contains(this)) {
            json.writeValue("position", new Float[] {sprite.getX(), sprite.getY()});
        }
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        name = jsonData.getString("name");
        spritePath = jsonData.getString("spritePath");
        type = jsonData.getString("type");
        description = jsonData.getString("description");
        durability = Integer.parseInt(jsonData.getString("durability"));
        if(type.equals("Weapon")) {
            damage = Integer.parseInt(jsonData.getString("damage"));
        }
        setSprite();

        if(jsonData.has("position")) {
            JsonValue position = jsonData.get("position");
            float[] positions_2 = position.asFloatArray();
            Gdx.app.log("Item", positions_2[0] + " " + positions_2[1]);
            sprite.setX(positions_2[0]);
            sprite.setY(positions_2[1]);
        }

    }
}
