package main.java.com.rohan.everblaze.TileInteraction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.java.com.rohan.everblaze.Entities.Player;

public class HUD {

    private Texture heart, threeQheart, halfHeart, QuarterHeart, empty;
    private Sprite pause;

    private SpriteBatch hud;

    private Player player;

    public HUD(Player player) {

        this.player = player;

        hud = new SpriteBatch();
        heart = new Texture(Gdx.files.internal("Hearts/heart.png"));
        threeQheart = new Texture(Gdx.files.internal("Hearts/3-4-heart.png"));
        halfHeart = new Texture(Gdx.files.internal("Hearts/half-heart.png"));
        QuarterHeart = new Texture(Gdx.files.internal("Hearts/quarter-heart.png"));
        empty = new Texture(Gdx.files.internal("Hearts/background.png"));

        pause = new Sprite(new Texture(Gdx.files.internal("UI/pause.png")));
        pause.setPosition(950, 770);
        pause.setSize(25, 25);
    }

    public void render() {
        hud.begin();
        for(int x = 0; x < player.hearts; x++) {
            if(player.health == player.hearts) {
                hud.draw(heart, 20 * x + 5, 770);
            } else {
                if(x > player.health - 1) {
                    hud.draw(empty, 20 * x + 5, 770);
                } else {
                    hud.draw(heart, 20 * x + 5, 770);
                }
            }
        }
        pause.draw(hud);
        hud.end();
    }

    public boolean pausePressed() {

        if(Gdx.input.isTouched()) {
            if (pause.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                return true;
            }
        }
        return false;
    }
}
