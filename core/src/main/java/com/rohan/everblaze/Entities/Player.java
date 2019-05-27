package main.java.com.rohan.everblaze.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import main.java.com.rohan.everblaze.Effects.Sound_Effects;
import main.java.com.rohan.everblaze.Levels.World;
import main.java.com.rohan.everblaze.TileInteraction.Inventory;

public class Player {

    public Texture currentFrame, running, idle;

    private float speed = 0.9f;
    private boolean runTemp = false;

    public Vector2 position;
    public Inventory inventory_;
    public int WIDTH;
    public int HEIGHT;

    private boolean flip;

    public Item swordClone;
    private Sprite slash_left, slash_right;
    public boolean renderSlash = false;
    private float slashTime = 0;
    public int cooldown = 0;

    private Sound_Effects effect_slash;
    public Sound_Effects effect_eat;

    public int hearts = 10;
    public int health = 10;

    private Rectangle box;
    public String horiDirection = "right";
    public String vertDirection = "up";

    public Player(int x, int y) {

        position = new Vector2();
        position.x = x;
        position.y = y;

        inventory_ = new Inventory(this);
        inventory_.addItem(new Item("Green Apple", "itemSprites/tile002.png", Classifier.Food, "Restores 2 hearts."));
        inventory_.addItem(new Item("Blackberry", "itemSprites/tile000.png", Classifier.Food, "Restores 2 hearts."));

        idle = new Texture(Gdx.files.internal("Character/knight-idle.png"));
        running = new Texture(Gdx.files.internal("Character/knight-walk.png"));

        slash_left = new Sprite(new Texture(Gdx.files.internal("Character/slash_left2.png")));
        slash_right = new Sprite(new Texture(Gdx.files.internal("Character/slash_right2.png")));
        effect_slash = new Sound_Effects("sword_slash2", false);
        effect_eat = new Sound_Effects("eat_food", false);

        currentFrame = idle;

        WIDTH = currentFrame.getWidth();
        HEIGHT = currentFrame.getHeight();

        box = new Rectangle();
        box.x = position.x;
        box.y = position.y;
        box.height = HEIGHT;
        box.width = WIDTH;

    }

    public void update() {

        runTemp = false;
        keyboardMove();

        controllerMove();
        processCollision();
        processWeaponry();

        box.x = position.x;
        box.y = position.y;
    }

    private void processCollision() {
        if(World.detector.hasCollided().equals("room_entrance")) {
            Gdx.app.log("Player", "Room Entrance");
        }

    }

    private void processWeaponry() {
        if(inventory_.inventory.size() != 0) {
            if (inventory_.slotSelected - 1 < inventory_.inventory.size()) {
                Item item = inventory_.inventory.get(inventory_.slotSelected - 1);
                if(item.name.contains("Sword")) {
                    swordClone = new Item(item.name, item.spritePath, item.type, item.description);
                    swordClone.sprite.setSize(16, 16);
                } else {
                    swordClone = null;
                }
            } else {
                swordClone = null;
            }
        }
        if(swordClone != null) {
            if(horiDirection.equals("left")) {
                swordClone.sprite.setCenter(Math.round(position.x + 2), Math.round(position.y + (HEIGHT / 2)));
                swordClone.sprite.rotate(-20);
            } else if(horiDirection.equals("right")) {
                swordClone.sprite.setCenter(Math.round(position.x + WIDTH - 4), Math.round(position.y + (HEIGHT / 2) + 5));
                swordClone.sprite.rotate(20);
            }
        }
    }

    public void attack() {
        effect_slash.play();
        renderSlash = true;
        slashTime = 0f;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {
        slashTime += Gdx.graphics.getDeltaTime();

        flip = (horiDirection.equals("left"));

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(currentFrame, flip ? position.x + WIDTH : position.x, position.y, flip ? -WIDTH : WIDTH, HEIGHT);

        if(swordClone != null) {
            if(!flip) {
                swordClone.sprite.setFlip(true, false);
            }
            if(renderSlash) {
                if(flip) {
                    slash_left.setPosition(position.x - 14, position.y - 2);
                    swordClone.sprite.rotate90(false);
                    swordClone.sprite.setY(swordClone.sprite.getY() - 10);
                    slash_left.draw(batch);
                    swordClone.render(batch);
                    if(cooldown == 0) {
                        cooldown = 1;
                    }
                } else {
                    slash_right.setPosition(position.x + 14, position.y - 2);
                    swordClone.sprite.rotate90(true);
                    swordClone.sprite.setY(swordClone.sprite.getY() - 10);
                    slash_right.draw(batch);
                    swordClone.render(batch);
                    if(cooldown == 0) {
                        cooldown = 1;
                    }
                }
            }
            swordClone.render(batch);
        }
        if(renderSlash && slashTime > 0.2f) {
            cooldown = 0;
            renderSlash = false;
        }

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

// controller
    public void controllerMove() {
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
    }

// keyboard
    public void keyboardMove() {
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            if(!World.detector.collisionAt(Math.round(position.x - 3), Math.round(position.y)).equals("obstacle")) {
                position.x -= speed;
                horiDirection = "left";
                currentFrame = running;
                runTemp = true;
            }

        }

        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            if(!World.detector.collisionAt(Math.round(position.x + 3), Math.round(position.y)).equals("obstacle")) {
                position.x += speed;
                horiDirection = "right";
                currentFrame = running;
                runTemp = true;
            }

        }

        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {
            if(!World.detector.collisionAt(Math.round(position.x), Math.round(position.y - 3)).equals("obstacle")) {
                position.y -= speed;
                vertDirection = "down";
                currentFrame = running;
                runTemp = true;
            }

        }

        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP))
        {
            if(!World.detector.collisionAt(Math.round(position.x), Math.round(position.y + 3)).equals("obstacle")) {
                position.y += speed;
                vertDirection = "up";
                currentFrame = running;
                runTemp = true;
            }

        }
        if(!runTemp) currentFrame = idle;
    }
}
