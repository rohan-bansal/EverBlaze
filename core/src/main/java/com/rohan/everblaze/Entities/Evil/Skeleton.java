package main.java.com.rohan.everblaze.Entities.Evil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import main.java.com.rohan.everblaze.Classifier;
import main.java.com.rohan.everblaze.Effects.Sound_Effects;
import main.java.com.rohan.everblaze.Entities.MovementScript;
import main.java.com.rohan.everblaze.Levels.World;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Item;

public class Skeleton extends Enemy {

    Texture walkSheet;
    Texture idleSheet;
    Texture attackSheet;
    Texture dieSheet;
    boolean droppedItem = false;

    Sound_Effects moveSound = new Sound_Effects("Entities/Skeleton/skeleton_move.mp3");

    public Skeleton(String name, String type, int x, int y, MovementScript script) {
        super(name, type, x, y, script);
        super.health = 10;
        super.hearts = 10;
        super.speed = 0.3f;
        super.damage = 3;
        super.hardness = 3;

        walkSheet = new Texture(Gdx.files.internal("Entities/Skeleton/skeleton_walk.png"));
        attackSheet = new Texture(Gdx.files.internal("Entities/Skeleton/skeleton_attack.png"));
        idleSheet = new Texture(Gdx.files.internal("Entities/Skeleton/skeleton_idle.png"));
        dieSheet = new Texture(Gdx.files.internal("Entities/Skeleton/skeleton_die.png"));

        constructAnimation();
    }

    private void constructAnimation() {

        TextureRegion[][] walkTMP = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / 13,
                walkSheet.getHeight() / 1);

        TextureRegion[] walkFrames = new TextureRegion[13 * 1];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 13; j++) {
                walkFrames[index++] = walkTMP[i][j];
            }
        }

        super.walkAnim = new Animation<TextureRegion>(0.1f, walkFrames);

        TextureRegion[][] attackTMP = TextureRegion.split(attackSheet,
                attackSheet.getWidth() / 18,
                attackSheet.getHeight() / 1);

        TextureRegion[] attackFrames = new TextureRegion[18 * 1];
        int index2 = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 18; j++) {
                attackFrames[index2++] = attackTMP[i][j];
            }
        }

        super.attackAnim = new Animation<TextureRegion>(0.1f, attackFrames);
        super.damageFrame = attackAnim.getKeyFrames()[7];

        TextureRegion[][] idleTMP = TextureRegion.split(idleSheet,
                idleSheet.getWidth() / 11,
                idleSheet.getHeight() / 1);

        TextureRegion[] idleFrames = new TextureRegion[11 * 1];
        int index3 = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 11; j++) {
                idleFrames[index3++] = idleTMP[i][j];
            }
        }

        super.idleAnim = new Animation<TextureRegion>(0.1f, idleFrames);

        TextureRegion[][] dieTMP = TextureRegion.split(dieSheet,
                dieSheet.getWidth() / 15,
                dieSheet.getHeight() / 1);

        TextureRegion[] dieFrames = new TextureRegion[15 * 1];
        int index4 = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 15; j++) {
                dieFrames[index4++] = dieTMP[i][j];
            }
        }

        super.dieAnim = new Animation<TextureRegion>(0.1f, dieFrames);

    }

    public void render(SpriteBatch batch) {
        super.render(batch);

        if(super.reachedLastDieFrame && !droppedItem) {
            Item dropItem = new Item("Rusty Halberd", "itemSprites/tile421.png", Classifier.Weapon, 3, "Rusty Weapon. Very brittle!", 3);
            dropItem.loadCoords((int) (position.x + super.currentFrame.getRegionWidth() / 2), (int) (position.y + super.currentFrame.getRegionHeight() / 2));
            dropItem.sprite.setSize(16, 16);
            World.onFloor.add(dropItem);
            droppedItem = true;
        }
    }

    @Override
    public void attack() {
        super.attack();
        if(super.playSound) {
            moveSound.play();
            super.playSound = false;
        }
    }
}
