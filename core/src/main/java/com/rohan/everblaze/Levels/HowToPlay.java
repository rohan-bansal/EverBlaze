package main.java.com.rohan.everblaze.Levels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import main.java.com.rohan.everblaze.FileUtils.GameManager;

public class HowToPlay implements Screen {

    private Game game;
    private SpriteBatch screen;

    private Sprite back;
    private BitmapFont title = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);
    private BitmapFont plot = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);
    private BitmapFont plot2 = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);

    private BitmapFont move = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);
    private BitmapFont QDesc = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);
    private BitmapFont EDesc = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);
    private BitmapFont FDesc = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);

    private ParticleEffect pe1, pe2;

    public HowToPlay(Game game) {

        this.game = game;
        screen = new SpriteBatch();

        back = new Sprite(new Texture(Gdx.files.internal("UI/back.png")));
        back.setCenter(100, 50);

        pe1 = new ParticleEffect();
        pe2 = new ParticleEffect();

        pe1.load(Gdx.files.internal("UI/HUD/everblade_fire3"),Gdx.files.internal(""));
        pe2.load(Gdx.files.internal("UI/HUD/everblade_fire3"),Gdx.files.internal(""));

        pe1.getEmitters().first().setPosition(Gdx.graphics.getWidth()/8, 500);
        pe1.start();

        pe2.getEmitters().first().setPosition((Gdx.graphics.getWidth() / 8) * 7, 500);
        pe2.start();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(37/255f, 32/255f, 31/255f, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        pe1.update(Gdx.graphics.getDeltaTime());
        pe2.update(Gdx.graphics.getDeltaTime());

        screen.begin();

        pe1.draw(screen);
        pe2.draw(screen);

        back.draw(screen);

        GlyphLayout layout = new GlyphLayout();
        layout.setText(title, "How To Play");
        title.setColor(Color.FIREBRICK);
        title.getData().setScale(3);
        title.draw(screen, "How To Play", (Gdx.graphics.getWidth() / 2) - layout.width / 2, 800);

        layout.setText(plot, "An archmage that goes by the name Earthshatterer has brought terror to the land!");
        plot.setColor(Color.CORAL);
        plot.getData().setScale(0.8f);
        plot.draw(screen, "An archmage that goes by the name Earthshatterer has brought terror to the land!", (Gdx.graphics.getWidth() / 2) - layout.width / 2, 630);

        layout.setText(plot2, "Find an ancient weapon known as the EverBlade and use it to defeat the Earhshatterer!");
        plot2.setColor(Color.CORAL);
        plot2.getData().setScale(0.8f);
        plot2.draw(screen, "Find an ancient weapon known as the EverBlade and use it to defeat the Earhshatterer!", (Gdx.graphics.getWidth() / 2) - layout.width / 2, 600);

        layout.setText(move, "Move the player with 'W A S D'");
        move.setColor(Color.TAN);
        move.getData().setScale(0.7f);
        move.draw(screen, "Move the player with 'W A S D'", (Gdx.graphics.getWidth() / 2) - layout.width / 2, 500);

        layout.setText(QDesc, "Pick up items by running into them. Drop items with 'Q'");
        QDesc.setColor(Color.TAN);
        QDesc.getData().setScale(0.7f);
        QDesc.draw(screen, "Pick up items by running into them. Drop items with 'Q'", (Gdx.graphics.getWidth() / 2) - layout.width / 2, 470);

        layout.setText(EDesc, "Open your inventory with 'E' to view item descriptions, quests, health, and coins.");
        EDesc.setColor(Color.TAN);
        EDesc.getData().setScale(0.7f);
        EDesc.draw(screen, "Open your inventory with 'E' to view item descriptions, quests, health, and coins.", (Gdx.graphics.getWidth() / 2) - layout.width / 2, 440);

        layout.setText(FDesc, "Press 'F' to use items (e.g. eat food, attack, open chests, view signs, talk to NPCs).");
        FDesc.setColor(Color.TAN);
        FDesc.getData().setScale(0.7f);
        FDesc.draw(screen, "Press 'F' to use items (e.g. eat food, attack, open chests, view signs, talk to NPCs).", (Gdx.graphics.getWidth() / 2) - layout.width / 2, 410);

        screen.end();

        if (pe1.isComplete()) pe1.reset();
        if (pe2.isComplete()) pe2.reset();

        buttonPressed();
    }

    private void buttonPressed() {
        if(Gdx.input.isTouched()) {
            if(back.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                Gdx.app.log("How To Play", "Returning to Title Screen");
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

    }
}
