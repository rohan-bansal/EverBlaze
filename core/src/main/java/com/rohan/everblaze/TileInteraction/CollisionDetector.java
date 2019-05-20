package main.java.com.rohan.everblaze.TileInteraction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import main.java.com.rohan.everblaze.Entities.Evil.Enemy;
import main.java.com.rohan.everblaze.Entities.Item;
import main.java.com.rohan.everblaze.Entities.Player;
import main.java.com.rohan.everblaze.Levels.World;

import java.util.Arrays;

public class CollisionDetector {

    private Player player;
    private TiledMap map;
    private MapObjects objects;

    public int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;

    public static final String[] obstacles = {"Pillar", "Wall", "Statue", "Gate_Wall", "Tree", "Swamp_Wall", "Tower", "Railing"};
    public static final String[] room_entrances = {"Cave_Entrance_1", "Cave_Entrance_2", "Blocked_Cave_Entrance_1", "Blocked_Cave_Entrance_2"};

    public CollisionDetector(Player player, TiledMap map) {
        this.player = player;
        this.map = map;
        this.objects = map.getLayers().get("Collisions").getObjects();

        MapProperties properties = map.getProperties();
        tileWidth = properties.get("tilewidth", Integer.class);
        tileHeight = properties.get("tileheight", Integer.class);
        mapWidthInTiles = properties.get("width", Integer.class);
        mapHeightInTiles = properties.get("height", Integer.class);
        mapWidthInPixels = mapWidthInTiles * tileWidth;
        mapHeightInPixels = mapHeightInTiles * tileHeight;
    }

    public MapObjects getObjects () {
        return objects;
    }

    public String collisionAt(int x, int y) {
        Rectangle futurePlayer = player.getMovedRect(x, y);

        for(RectangleMapObject obj : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = obj.getRectangle();

            if (Intersector.overlaps(rectangle, futurePlayer)) {
                if(Arrays.asList(obstacles).contains(obj.getName())) {
                    return "obstacle";
                }
            }
        }
        return "none";
    }

    public String EnemycollisionAt(Enemy enemy, int x, int y) {
        Rectangle futureEntity = new Rectangle(x, y, enemy.currentFrame.getRegionWidth(), enemy.currentFrame.getRegionHeight());

        for(RectangleMapObject obj : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = obj.getRectangle();

            if (Intersector.overlaps(rectangle, futureEntity)) {
                if(Arrays.asList(obstacles).contains(obj.getName())) {
                    return "obstacle";
                }
            }
        }
        return "none";
    }

    public boolean EnemycollisionWith(Item item, Enemy enemy) {
        Rectangle item_ = new Rectangle(item.sprite.getX(), item.sprite.getY(), item.sprite.getTexture().getWidth(), item.sprite.getTexture().getHeight());
        Rectangle enemy_ = new Rectangle(enemy.position.x, enemy.position.y, enemy.currentFrame.getRegionWidth(), enemy.currentFrame.getRegionHeight());
        if (Intersector.overlaps(item_, enemy_)) {
            //Gdx.app.log("", "Player Rect: " + item_.getX() + " " + item_.getY() + " " + item_.getWidth() + " " + item_.getHeight() + " | Enemy Rect: " + enemy_.getX() + " " + enemy_.getY() +
            //        " " + enemy_.getWidth() + " " + enemy_.getHeight());
            return true;
        }
        return false;
    }

    public Item itemCollision(boolean debug) {
        for(Item item : World.onFloor) {
            if (Intersector.overlaps(player.getRectangle(), item.sprite.getBoundingRectangle())) {
                if(debug) {
                    Gdx.app.log("Collision Detector", "Item Collision : " + item.name);
                }
                return item;
            }
        }
        return null;
    }

    public String hasCollided() {

        for(RectangleMapObject obj : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = obj.getRectangle();

            if (Intersector.overlaps(rectangle, player.getRectangle())) {
                if(Arrays.asList(room_entrances).contains(obj.getName())) {
                    return "room_entrance";
                }
            }
        }
        return "none";
    }
}