package main.java.com.rohan.everblaze.Entities.Evil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import main.java.com.rohan.everblaze.Entities.MovementScript;

import java.util.ArrayList;

public class Enemy {

    public int hearts;
    public int health;
    public float speed;
    public int damage;
    private String type;

    private String name;
    private MovementScript script;

    public Animation<TextureRegion> walkAnim;
    public Animation<TextureRegion> attackAnim;
    public Animation<TextureRegion> idleAnim;

    private int animState = 0;
    private TextureRegion currentFrame;
    private String horizDirection;

    private Sprite sprite;
    private Vector2 position;
    private ArrayList<String> sequence;
    private int currentSequenceItem;

    private float elapsedTime = 0f;
    private float stateTime = 0f;

    public Enemy(String name, String type, int x, int y, MovementScript script) {

        this.script = script;
        this.name = name;
        this.type = type;
        this.position = new Vector2(x, y);

        this.horizDirection = "right";

        this.sequence = script.getSequence();
        currentSequenceItem = 0;

    }

    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        move();

        switch(animState) {
            case 0:
                currentFrame = idleAnim.getKeyFrame(stateTime, true);
                break;
            case 1:
                currentFrame = walkAnim.getKeyFrame(stateTime, true);
                break;
            case 2:
                currentFrame = attackAnim.getKeyFrame(stateTime, true);
                break;
        }

        boolean flip = (horizDirection.equals("left"));
        batch.draw(currentFrame, flip ? position.x + currentFrame.getRegionWidth() : position.x, position.y, flip ? -currentFrame.getRegionWidth() : currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
        //batch.draw(currentFrame, position.x, position.y);
    }

    public void move() {
        elapsedTime += Gdx.graphics.getDeltaTime();

        String current = sequence.get(currentSequenceItem);

        if(current.equals("Up") ||
                current.equals("Down") ||
                current.equals("Left") ||
                current.equals("Right")) {
            if(elapsedTime <= script.getIntervalTime()) {
                animState = 1;
                if(current.equals("Up")) {
                    position.y += speed;
                } else if(current.equals("Down")) {
                    position.y -= speed;
                } else if(current.equals("Left")) {
                    horizDirection = "left";
                    position.x -= speed;
                } else if(current.equals("Right")) {
                    horizDirection = "right";
                    position.x += speed;
                }
            } else {
                elapsedTime = 0f;
                if(currentSequenceItem + 1 == sequence.size()) {
                    currentSequenceItem = 0;
                } else {
                    currentSequenceItem += 1;
                }
            }
        } else if(current.equals("Stop")) {
            if(elapsedTime <= script.getIntervalTime()) {
                animState = 0;
            } else {
                elapsedTime = 0f;
                if(currentSequenceItem + 1 == sequence.size()) {
                    currentSequenceItem = 0;
                } else {
                    currentSequenceItem += 1;
                }
            }
        }

        //Gdx.app.log("Enemy", current + " : " + position.x + " : " + position.y);
        //Gdx.app.log("EnemyTick", "" + currentSequenceItem);
    }

    public void attack() {
    }
}
