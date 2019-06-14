package main.java.com.rohan.everblaze.Levels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import main.java.com.rohan.everblaze.Effects.Sound_Effects;
import com.rohan.everblaze.TileInteraction.GifDecoder;

public class GameOver implements Screen {

    private Game game;

    private Sprite title, load, quit;
    private SpriteBatch screen;
    private TextureRegion currentFrame;

    private float stateTime = 0f;

    Animation<TextureRegion> animation;

    private Sound_Effects music;

    public GameOver(Game game) {

        Gdx.app.log("Title", "Set game over screen");

        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.NORMAL, Gdx.files.internal("UI/gameOver.gif").read());

        this.game = game;
        screen = new SpriteBatch();

        title = new Sprite(new Texture(Gdx.files.internal("UI/title.png")));

        this.load = new Sprite(new Texture(Gdx.files.internal("UI/load-game.png")));
        this.quit = new Sprite(new Texture(Gdx.files.internal("UI/quit.png")));

        title.setCenter(500, 400);
        load.setCenter(500, 300);
        quit.setCenter(500, 200);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(37/255f, 32/255f, 31/255f, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );


        screen.begin();

        currentFrame = animation.getKeyFrame(stateTime, false);

        screen.draw(currentFrame, 130, 400);
        load.draw(screen);
        quit.draw(screen);

        screen.end();

        buttonPressed();

    }

    private void buttonPressed() {
        if(Gdx.input.isTouched()) {
            if(load.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                game.setScreen(new World(game, true));
            } else if(quit.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                game.setScreen(new TitleScreen(game, false));
            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        screen.dispose();
    }
}
