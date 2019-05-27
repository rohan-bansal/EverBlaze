package main.java.com.rohan.everblaze.Entities.Evil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import main.java.com.rohan.everblaze.Entities.Item;
import main.java.com.rohan.everblaze.Entities.MovementScript;
import main.java.com.rohan.everblaze.Levels.World;

import java.util.ArrayList;

public class Enemy {

    public int hearts;
    public int health;
    public float speed;
    public int damage;
    private String type;
    public boolean hit = false;

    private boolean breakMove = false;

    private boolean hasDied;
    private boolean reachedLastDieFrame = false;
    private boolean reachedLastAttackFrame = false;
    private boolean playerDamaged = false;
    private boolean attackStarted = false;

    private String name;
    private MovementScript script;

    public Animation<TextureRegion> walkAnim;
    public Animation<TextureRegion> attackAnim;
    public Animation<TextureRegion> idleAnim;
    public Animation<TextureRegion> hitAnim;
    public Animation<TextureRegion> dieAnim;

    private int animState = 0;
    public TextureRegion currentFrame;
    private String horizDirection;

    public Vector2 position;
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

        if(hasDied) {
            die();
        }

        switch(animState) {
            case 0:
                currentFrame = idleAnim.getKeyFrame(stateTime, true);
                break;
            case 1:
                currentFrame = walkAnim.getKeyFrame(stateTime, true);
                break;
            case 2:
                currentFrame = attackAnim.getKeyFrame(stateTime, true);
                if(currentFrame == attackAnim.getKeyFrames()[attackAnim.getKeyFrames().length - 1]) {
                    reachedLastAttackFrame = true;
                }
                //if(!attackAnim.isAnimationFinished(stateTime)) {
                //    currentFrame = attackAnim.getKeyFrame(stateTime);
                //} else {
                //    reachedLastAttackFrame = true;
                //}
                break;
            case 3:
                if(dieAnim != null) {
                    if(!dieAnim.isAnimationFinished(stateTime)) {
                        currentFrame = dieAnim.getKeyFrame(stateTime);
                    } else {
                        reachedLastDieFrame = true;
                    }
                } else {
                    reachedLastDieFrame = true;
                }
                break;
        }

        boolean flip = (horizDirection.equals("left"));
        batch.draw(currentFrame, flip ? position.x + currentFrame.getRegionWidth() : position.x, position.y, flip ? -currentFrame.getRegionWidth() : currentFrame.getRegionWidth(), currentFrame.getRegionHeight());

        if(animState != 3) {
            if(!breakMove) {
                move();
            } else {
                attack();
            }
        }

        if(World.detector.enemySeesPlayer(this, 170)) {
            breakMove = true;
        } else {
            breakMove = false;
        }
    }

    public Rectangle getRect() {
        return new Rectangle(position.x, position.y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public boolean takeDamage(int damage, String direction) {
        health -= damage;

        if(direction.equals("left")) {
            position.x -= 5;
        } else {
            position.x += 5;
        }

        if(health <= 0) {
            stateTime = 0f;
            hasDied = true;
            return true;
        }
        return false;
    }

    private void die() {
        animState = 3;

        if(reachedLastDieFrame) {
            World.enemiesToRemove.add(this);
        }
    }

    public void move() {
        elapsedTime += Gdx.graphics.getDeltaTime();

        if(hasDied) {
            return;
        }

        String current = sequence.get(currentSequenceItem);

        if(current.equals("Up") ||
                current.equals("Down") ||
                current.equals("Left") ||
                current.equals("Right")) {
            if(elapsedTime <= script.getIntervalTime()) {
                animState = 1;
                if(current.equals("Up")) {
                    if(!World.detector.EnemycollisionAt(this, Math.round(position.x), Math.round(position.y + 5)).equals("obstacle")) {
                        position.x += speed;
                    }
                } else if(current.equals("Down")) {
                    if(!World.detector.EnemycollisionAt(this, Math.round(position.x), Math.round(position.y - 5)).equals("obstacle")) {
                        position.y -= speed;
                    }
                } else if(current.equals("Left")) {
                    if(!World.detector.EnemycollisionAt(this, Math.round(position.x - 5), Math.round(position.y)).equals("obstacle")) {
                        horizDirection = "left";
                        position.x -= speed;
                    }
                } else if(current.equals("Right")) {
                    if(!World.detector.EnemycollisionAt(this, Math.round(position.x + 5), Math.round(position.y)).equals("obstacle")) {
                        horizDirection = "right";
                        position.x += speed;
                    }

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
            if(elapsedTime <= script.getStopTime()) {
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

        playerDamaged = false;

        int diffX = Math.round(World.detector.player.position.x - position.x);
        int diffY = Math.round(World.detector.player.position.y - position.y);

        float angle = (float) Math.atan2(diffY, diffX);

        if(reachedLastAttackFrame) {
            if(World.detector.EnemycollisionWith(new Item(), this, true)) {
                if(!playerDamaged) {
                    World.detector.player.health -= damage;
                    Gdx.app.log(name, "Damaged player for " + damage + " hearts");
                    playerDamaged = true;
                }

            }
            animState = 1;
            attackStarted = false;
            reachedLastAttackFrame = false;
        }

        if(World.detector.enemySeesPlayer(this, 7)) {
            animState = 2;
            if(World.detector.player.position.x < position.x) {
                horizDirection = "left";
            } else {
                horizDirection = "right";
            }
            if(!attackStarted) {
                stateTime = 0f;
                attackStarted = true;
            }
        }

        if(animState != 2) {
            if(!World.detector.EnemycollisionAt(this, Math.round(position.x + Math.round(Math.cos(angle))), Math.round(position.y + Math.round(Math.sin(angle)))).equals("obstacle")) {
                animState = 1;

                position.x += speed * Math.cos(angle);
                position.y += speed * Math.sin(angle);

                if(Math.cos(angle) < 0) {
                    horizDirection = "left";
                } else {
                    horizDirection = "right";
                }
            }
        }




        //Gdx.app.log(name, "" + angle);
    }

    public String getName() {
        return name;
    }
}
