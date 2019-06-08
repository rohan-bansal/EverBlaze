package main.java.com.rohan.everblaze.Entities.Evil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import main.java.com.rohan.everblaze.Entities.MovementScript;

public class Goblin extends Enemy {

    Texture walkSheet;
    Texture idleSheet;
    Texture attackSheet;

    public Goblin(String name, String type, int x, int y, MovementScript script) {
        super(name, type, x, y, script);
        super.health = 5;
        super.hearts = 5;
        super.speed = 0.4f;
        super.damage = 2;

        walkSheet = new Texture(Gdx.files.internal("Entities/Goblin/goblin_walk.png"));
        attackSheet = new Texture(Gdx.files.internal("Entities/Goblin/goblin_attack.png"));
        idleSheet = new Texture(Gdx.files.internal("Entities/Goblin/goblin_idle.png"));

        constructAnimation();
    }

    private void constructAnimation() {

        TextureRegion[][] walkTMP = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / 7,
                walkSheet.getHeight() / 1);

        TextureRegion[] walkFrames = new TextureRegion[7* 1];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 7; j++) {
                walkFrames[index++] = walkTMP[i][j];
            }
        }

        super.walkAnim = new Animation<TextureRegion>(0.1f, walkFrames);

        TextureRegion[][] attackTMP = TextureRegion.split(attackSheet,
                attackSheet.getWidth() / 10,
                attackSheet.getHeight() / 1);

        TextureRegion[] attackFrames = new TextureRegion[10 * 1];
        int index2 = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 10; j++) {
                attackFrames[index2++] = attackTMP[i][j];
            }
        }

        super.attackAnim = new Animation<TextureRegion>(0.1f, attackFrames);
        super.damageFrame = attackAnim.getKeyFrames()[9];

        TextureRegion[][] idleTMP = TextureRegion.split(idleSheet,
                idleSheet.getWidth() / 10,
                idleSheet.getHeight() / 1);

        TextureRegion[] idleFrames = new TextureRegion[10 * 1];
        int index3 = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 10; j++) {
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
        super.attack();
    }
}
