package main.java.com.rohan.everblaze.TileInteraction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import main.java.com.rohan.everblaze.Effects.ScreenText;
import main.java.com.rohan.everblaze.Entities.Player;
import main.java.com.rohan.everblaze.Levels.World;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Signpost;

public class HUD {

    private Texture heart, threeQheart, halfHeart, QuarterHeart, empty, coin;
    private Sprite pause;

    private ScreenText coins = new ScreenText();

    private SpriteBatch hud;

    private Player player;

    public HUD(Player player) {

        this.player = player;

        hud = new SpriteBatch();
        heart = new Texture(Gdx.files.internal("UI/HUD/Hearts/heart.png"));
        threeQheart = new Texture(Gdx.files.internal("UI/HUD/Hearts/3-4-heart.png"));
        halfHeart = new Texture(Gdx.files.internal("UI/HUD/Hearts/half-heart.png"));
        QuarterHeart = new Texture(Gdx.files.internal("UI/HUD/Hearts/quarter-heart.png"));
        empty = new Texture(Gdx.files.internal("UI/HUD/Hearts/background.png"));
        coin = new Texture(Gdx.files.internal("UI/HUD/Coins/coin.png"));

        pause = new Sprite(new Texture(Gdx.files.internal("UI/pause.png")));
        pause.setPosition(950, 770);
        pause.setSize(25, 25);

        coins.setPosition(new Vector2(25, 753));
        coins.setColor(Color.YELLOW);
        coins.setSize(1f);
    }

    public void drawSign(Signpost activeSign) {
        hud.begin();
        activeSign.drawText(hud);
        hud.end();
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
        hud.draw(coin, 5, 740);

        coins.setText(player.coins + "");
        coins.renderOnlyIf(hud);

        pause.draw(hud);
        hud.end();
    }

    public boolean pausePressed() {

        if(Gdx.input.isTouched()) {
            if (pause.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                World.levelMusic.pause();
                return true;
            }
        }
        return false;
    }
}
