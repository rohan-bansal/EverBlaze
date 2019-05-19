package main.java.com.rohan.everblaze.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Enemy {

    private int hearts;
    private int health;
    private int speed;

    private String name;
    private MovementScript script;

    private Sprite sprite;
    private Vector2 position;
    ArrayList<String> sequence = new ArrayList<String>();
    private int currentSequenceItem;

    private float elapsedTime = 0f;

    public Enemy(String name, String type, int x, int y, MovementScript script) {

        this.script = script;
        this.name = name;
        this.position = new Vector2(x, y);

        if(type.equals(Classifier.Slime)) {
            this.hearts = 2;
            this.health = 2;
            this.speed = 2;
            //sprite = new Sprite(new Texture(Gdx.files.internal("Entities/slime.png")));
        } else if(type.equals(Classifier.Goblin)) {
            this.hearts = 5;
            this.health = 5;
            this.speed = 4;
            //sprite = new Sprite(new Texture(Gdx.files.internal("Entities/goblin.png")));
        } else if(type.equals(Classifier.Skeleton)) {
            this.hearts = 4;
            this.health = 4;
            this.speed = 4;
            //sprite = new Sprite(new Texture(Gdx.files.internal("Entities/skeleton.png")));
        }
        this.sequence = script.getSequence();
        currentSequenceItem = 0;
    }

    public void render(SpriteBatch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();

        move();

        //sprite.setPosition(position.x, position.y);
        //sprite.draw(batch);
    }

    public void move() {
        if(elapsedTime >= script.getIntervalTime()) {
            elapsedTime = 0f;
            if(currentSequenceItem + 1 == sequence.size()) {
                currentSequenceItem = 0;
            } else {
                currentSequenceItem += 1;
            }
        }
        Gdx.app.log("EnemyTick", "" + currentSequenceItem);
    }

    public void attack() {
    }
}
