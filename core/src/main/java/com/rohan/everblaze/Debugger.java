package main.java.com.rohan.everblaze;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import main.java.com.rohan.everblaze.Entities.Evil.Enemy;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Item;
import main.java.com.rohan.everblaze.Entities.Player;
import main.java.com.rohan.everblaze.Levels.World;

import java.util.ArrayList;

public class Debugger {

    Player player;
    ShapeRenderer debugRenderer;

    public Debugger(Player player) {
        this.player = player;
        debugRenderer = new ShapeRenderer();
    }

    public void printDebug() {
        if(Gdx.input.isKeyPressed(Input.Keys.U)) {
            Gdx.app.log("DEBUGGER", player.position.x + " : " + player.position.y);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.I)) {
            player.inventory_.printInventory();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.O)) {
            for(Item item : World.onFloor) {
                Gdx.app.log("onFloor Logger", item.name);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.P)) {
            World.detector.itemCollision(true);
        }
    }

    public void renderDebugBoxes(ArrayList<Enemy> enemies, ArrayList<Item> onFloor) {
        debugRenderer.setProjectionMatrix(World.cam.camera.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(Color.GREEN);
        debugRenderer.rect(player.position.x, player.position.y, player.WIDTH, player.HEIGHT);
        debugRenderer.setColor(Color.YELLOW);
        if(player.swordClone != null) {
            debugRenderer.rect(player.swordClone.sprite.getBoundingRectangle().x, player.swordClone.sprite.getBoundingRectangle().y, player.swordClone.sprite.getBoundingRectangle().width, player.swordClone.sprite.getBoundingRectangle().height);
        } else if(player.spearClone != null) {
            debugRenderer.rect(player.spearClone.sprite.getBoundingRectangle().x, player.spearClone.sprite.getBoundingRectangle().y, player.spearClone.sprite.getBoundingRectangle().width, player.spearClone.sprite.getBoundingRectangle().height);
        }


        debugRenderer.end();

        for(Enemy enemy : enemies) {
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            debugRenderer.setColor(Color.RED);
            debugRenderer.rect(enemy.position.x, enemy.position.y, enemy.currentFrame.getRegionWidth(), enemy.currentFrame.getRegionHeight());
            debugRenderer.setColor(Color.DARK_GRAY);
            debugRenderer.rect(enemy.getRect().x - 1, enemy.getRect().y - 1, enemy.getRect().width + 2, enemy.getRect().height + 2);
            debugRenderer.end();
        }
        for(Item item : onFloor) {
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            debugRenderer.setColor(Color.YELLOW);
            debugRenderer.rect(item.sprite.getX(), item.sprite.getY(), item.sprite.getWidth(), item.sprite.getHeight());
            debugRenderer.end();
        }

        for(RectangleMapObject obj : World.detector.getObjects().getByType(RectangleMapObject.class)) {
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);
            Rectangle rectangle = obj.getRectangle();
            debugRenderer.setColor(Color.BLUE);
            debugRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            debugRenderer.end();
        }

    }
}
