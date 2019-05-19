package main.java.com.rohan.everblaze.Entities.Evil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import main.java.com.rohan.everblaze.Entities.Classifier;
import main.java.com.rohan.everblaze.Entities.MovementScript;

public class Skeleton extends Enemy {

    Texture walkSheet;
    Texture idleSheet;
    Texture attackSheet;

    public Skeleton(String name, String type, int x, int y, MovementScript script) {
        super(name, type, x, y, script);
        super.health = 4;
        super.hearts = 4;
        super.speed = 0.3f;
        super.damage = 3;

        walkSheet = new Texture(Gdx.files.internal("Entities/Skeleton/skeleton_walk.png"));
        attackSheet = new Texture(Gdx.files.internal("Entities/Skeleton/skeleton_attack.png"));
        idleSheet = new Texture(Gdx.files.internal("Entities/Skeleton/skeleton_idle.png"));

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

    }

    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    @Override
    public void attack() {
    }
}
