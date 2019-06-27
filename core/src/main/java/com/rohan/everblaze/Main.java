package main.java.com.rohan.everblaze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import main.java.com.rohan.everblaze.Levels.TitleScreen;

public class Main extends Game {

    private long startTime;
    private SpriteBatch batch;
    private Sprite logo;

    private int titleScreenSet = 0;

    @Override
    public void create() {
        Gdx.app.log("Main", "Loading Libraries: LibGDX, Gradle");

        batch = new SpriteBatch();

        logo = new Sprite(new Texture(Gdx.files.internal("UI/logo2.png")));
        logo.setCenter(500, 400);

        Pixmap pm = new Pixmap(Gdx.files.internal("Entities/pointer.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        pm.dispose();

        startTime = TimeUtils.millis();
    }

    @Override
    public void render() {
        super.render();
        if(TimeUtils.millis() - startTime <= 1000) {
            batch.begin();
            Gdx.gl.glClearColor(0f, 0f, 0f, 1);
            logo.draw(batch);
            batch.end();
        } else {
            if(titleScreenSet == 0) {
                this.setScreen(new TitleScreen(this, false));
                titleScreenSet = 1;
            }
        }
    }

    @Override
    public void dispose() {
    }
}