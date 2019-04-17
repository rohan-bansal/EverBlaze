package com.rohan.everblaze.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.rohan.everblaze.FileUtils.GameManager;
import com.rohan.everblaze.Levels.World;
import com.rohan.everblaze.TileInteraction.Inventory;

public class Player {

    public Texture currentFrame;
    public Texture running;
    public Texture idle;

    private float speed = 3;
    private boolean runTemp = false;

    public Vector2 position;
    public Inventory inventory_;
    public int WIDTH = 16;
    public int HEIGHT = 20;

    public int hearts = 10;
    public int health = 10;

    private Rectangle box;
    public String direction = "right";

    public Player(int x, int y) {

        position = new Vector2();
        position.x = x;
        position.y = y;

        inventory_ = new Inventory(this);
        inventory_.addItem(new Item("Cherry", "itemSprites/tile002.png", Classifier.Food, "Restores 2 hearts."));

        idle = new Texture(Gdx.files.internal("Character/knight-idle.png"));
        running = new Texture(Gdx.files.internal("Character/knight-walk.png"));
        currentFrame = idle;

        box = new Rectangle();
        box.x = position.x;
        box.y = position.y;
        box.height = HEIGHT;
        box.width = WIDTH;

    }

    public void update() {

        runTemp = false;
        move();

        if(World.movingRight) {
            if(!World.detector.collisionAt(Math.round(position.x + 3), Math.round(position.y)).equals("obstacle")) {
                position.x += speed;
            }
        }
        if(World.movingLeft) {
            if(!World.detector.collisionAt(Math.round(position.x - 3), Math.round(position.y)).equals("obstacle")) {
                position.x -= speed;
            }
        }
        if(World.movingUp) {
            if(!World.detector.collisionAt(Math.round(position.x), Math.round(position.y + 3)).equals("obstacle")) {
                position.y += speed;
            }
        }
        if(World.movingDown) {
            if(!World.detector.collisionAt(Math.round(position.x), Math.round(position.y - 3)).equals("obstacle")) {
                position.y -= speed;
            }
        }

        box.x = position.x;
        box.y = position.y;

    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {

        World.detector.itemCollision();

        boolean flip = (direction.equals("left"));

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(currentFrame, flip ? position.x + WIDTH : position.x, position.y, flip ? -WIDTH : WIDTH, HEIGHT);
        batch.end();

        inventory_.render();
    }

    public Rectangle getRectangle() {
        return box;
    }

    public Rectangle getMovedRect(int x, int y) {
        Rectangle box2 = new Rectangle();
        box2.width = box.width;
        box2.height = box.height;
        box2.x = x;
        box2.y = y;

        return box2;
    }

    public Rectangle getInflatedRect() {
        Rectangle box2 = new Rectangle();
        box2.width = box.width + 4;
        box2.height = box.height + 4;
        box2.setCenter(box.x + 2, box.y + 2);
        return box2;
    }

    public void move() {
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            if(!World.detector.collisionAt(Math.round(position.x - 3), Math.round(position.y)).equals("obstacle")) {
                position.x -= 3;
                direction = "left";
                currentFrame = running;
                runTemp = true;
            }

        }

        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            if(!World.detector.collisionAt(Math.round(position.x + 3), Math.round(position.y)).equals("obstacle")) {
                position.x += 3;
                direction = "right";
                currentFrame = running;
                runTemp = true;
            }

        }

        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            if(!World.detector.collisionAt(Math.round(position.x), Math.round(position.y - 3)).equals("obstacle")) {
                position.y -= 3;
                currentFrame = running;
                runTemp = true;
            }

        }

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            if(!World.detector.collisionAt(Math.round(position.x), Math.round(position.y + 3)).equals("obstacle")) {
                position.y += 3;
                currentFrame = running;
                runTemp = true;
            }

        }
        if(!runTemp) currentFrame = idle;
    }

}
