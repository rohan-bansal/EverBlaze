package main.java.com.rohan.everblaze.TileInteraction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import main.java.com.rohan.everblaze.Classifier;
import main.java.com.rohan.everblaze.Entities.Evil.Enemy;
import main.java.com.rohan.everblaze.Entities.Good.NPC;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Item;
import main.java.com.rohan.everblaze.Entities.Player;
import main.java.com.rohan.everblaze.Levels.World;

import java.util.ArrayList;
import java.util.Arrays;

public class CollisionDetector {

    public Player player;
    private TiledMap map;
    private MapObjects objects;

    public int tileWidth, tileHeight,
            mapWidthInTiles, mapHeightInTiles,
            mapWidthInPixels, mapHeightInPixels;

    public static final String[] obstacles = {"Pillar", "Wall", "Statue", "Gate_Wall", "Tree", "Swamp_Wall", "Tower", "Railing", "Rock", "Tower_1"};
    public static final String[] room_entrances = {"Cave_Entrance_1", "Cave_Entrance_2", "Blocked_Cave_Entrance_1", "Blocked_Cave_Entrance_2"};

    public CollisionDetector(Player player, TiledMap map) {
        this.player = player;
        this.map = map;
        this.objects = map.getLayers().get("Collisions").getObjects();
        //for(MapObject object : objects) {
        //    Gdx.app.log("detector", object.getName());
        //}

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

    public ArrayList<MapObject> getChests() {

        ArrayList<MapObject> chests = new ArrayList<MapObject>();

        for(MapObject object : objects) {
            if(object.getName().toLowerCase().contains("chest")) {
                chests.add(object);
                Gdx.app.log("Detector", "Found chest. Loading...");
            }
        }
        return chests;
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

    public String NPCcollisionAt(NPC npc, int x, int y) {
        Rectangle futureEntity = new Rectangle(x, y, npc.currentFrame.getRegionWidth(), npc.currentFrame.getRegionHeight());

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

    public boolean EnemycollisionWith(Item item, Enemy enemy, boolean... player_) {
        if(player_.length > 0) {
            Rectangle enemy_ = enemy.getRect();
            Rectangle player__ = player.getRectangle();
            if (enemy_.overlaps(player__)) {
                return true;
            }
        } else {
            Rectangle item_ = item.sprite.getBoundingRectangle();
            Rectangle enemy_ = enemy.getRect();
            if (item_.overlaps(enemy_)) {
                return true;
            }
        }
        return false;
    }

    public boolean enemySeesPlayer(Enemy enemy, int distance) {
        Rectangle player_ = player.getRectangle();
        Rectangle enemy_ = enemy.getRect();
        enemy_.height += distance;
        enemy_.width += distance;
        enemy_.setCenter(enemy.position.x - (enemy.currentFrame.getRegionWidth() / 2), enemy.position.y - (enemy.currentFrame.getRegionHeight() / 2));
        if (enemy_.overlaps(player_)) {
            return true;
        }
        return false;
    }

    public Item itemCollision(boolean debug) {
        for(Item item : World.onFloor) {
            if (player.getRectangle().overlaps(item.sprite.getBoundingRectangle())) {
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