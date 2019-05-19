package main.java.com.rohan.everblaze.Entities.Evil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import main.java.com.rohan.everblaze.Entities.Classifier;
import main.java.com.rohan.everblaze.Entities.MovementScript;

public class Slime extends Enemy {

    Texture idleSheet;
    Texture attackWalkSheet;

    public Slime(String name, String type, int x, int y, MovementScript script) {
        super(name, type, x, y, script);
        super.health = 2;
        super.hearts = 2;
        super.speed = 0.2f;
        super.damage = 1;

        if(type.equals(Classifier.Green_Slime)) {
            attackWalkSheet = new Texture(Gdx.files.internal("Entities/Slime/green_slime_attack.png"));
            idleSheet = new Texture(Gdx.files.internal("Entities/Slime/green_slime_idle.png"));
        } else if(type.equals(Classifier.Orange_Slime)) {
            attackWalkSheet = new Texture(Gdx.files.internal("Entities/Slime/orange_slime_attack.png"));
            idleSheet = new Texture(Gdx.files.internal("Entities/Slime/orange_slime_idle.png"));
        } else if(type.equals(Classifier.Purple_Slime)) {
            attackWalkSheet = new Texture(Gdx.files.internal("Entities/Slime/purple_slime_attack.png"));
            idleSheet = new Texture(Gdx.files.internal("Entities/Slime/purple_slime_idle.png"));
        }

        constructAnimation();
    }

    private void constructAnimation() {


        TextureRegion[][] attackTMP = TextureRegion.split(attackWalkSheet,
                attackWalkSheet.getWidth() / 5,
                attackWalkSheet.getHeight() / 1);

        TextureRegion[] attackFrames = new TextureRegion[5 * 1];
        int index2 = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 5; j++) {
                attackFrames[index2++] = attackTMP[i][j];
            }
        }

        super.walkAnim = new Animation<TextureRegion>(0.25f, attackFrames);
        super.attackAnim = new Animation<TextureRegion>(0.25f, attackFrames);

        TextureRegion[][] idleTMP = TextureRegion.split(idleSheet,
                idleSheet.getWidth() / 3,
                idleSheet.getHeight() / 1);

        TextureRegion[] idleFrames = new TextureRegion[3 * 1];
        int index3 = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                idleFrames[index3++] = idleTMP[i][j];
            }
        }

        super.idleAnim = new Animation<TextureRegion>(0.25f, idleFrames);

    }

    public void render(SpriteBatch batch) {
        super.render(batch);
    }

    @Override
    public void attack() {
    }
}
