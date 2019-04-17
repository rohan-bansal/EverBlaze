package com.rohan.everblaze.Levels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rohan.everblaze.FileUtils.GameManager;

public class Options implements Screen {

    private Game game;
    private SpriteBatch screen;

    private Sprite settings, back, musicOn, musicOff, FXOn, FXOff, InvertOn, InvertOff, VSOn, VSOff;
    private boolean fromGame;
    private GameManager manager;

    public Options(Game game, boolean fromGame, GameManager ... manager) {

        this.game = game;
        screen = new SpriteBatch();

        this.fromGame = fromGame;
        if(fromGame) this.manager = manager[0];

        settings = new Sprite(new Texture(Gdx.files.internal("UI/settings.png")));
        back = new Sprite(new Texture(Gdx.files.internal("UI/back.png")));

        musicOn = new Sprite(new Texture(Gdx.files.internal("UI/slider-on.png")));
        musicOff = new Sprite(new Texture(Gdx.files.internal("UI/slider-off.png")));
        FXOn = new Sprite(new Texture(Gdx.files.internal("UI/slider-on.png")));
        FXOff = new Sprite(new Texture(Gdx.files.internal("UI/slider-off.png")));
        InvertOn = new Sprite(new Texture(Gdx.files.internal("UI/slider-on.png")));
        InvertOff = new Sprite(new Texture(Gdx.files.internal("UI/slider-off.png")));
        VSOn = new Sprite(new Texture(Gdx.files.internal("UI/slider-on.png")));
        VSOff = new Sprite(new Texture(Gdx.files.internal("UI/slider-off.png")));

        musicOn.setCenter(300, 700);
        musicOff.setCenter(300, 700);
        FXOn.setCenter(450, 560);
        FXOff.setCenter(450, 560);
        InvertOn.setCenter(500, 420);
        InvertOff.setCenter(500, 420);
        VSOn.setCenter(300, 290);
        VSOff.setCenter(300, 290);


        settings.setPosition(20, 250);
        back.setCenter(100, 50);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(37/255f, 32/255f, 31/255f, 1);
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        screen.begin();
        settings.draw(screen);
        back.draw(screen);

        if(fromGame) {
            if(manager.data.isMusicOn()) {
                musicOn.draw(screen);
            } else {
                musicOff.draw(screen);
            }
            if(manager.data.isEffectsOn()) {
                FXOn.draw(screen);
            } else {
                FXOff.draw(screen);
            }if(manager.data.isInvertControlsOn()) {
                InvertOn.draw(screen);
            } else {
                InvertOff.draw(screen);
            }if(manager.data.isVsyncOn()) {
                VSOn.draw(screen);
            } else {
                VSOff.draw(screen);
            }
        }

        screen.end();

        buttonPressed();
    }

    private void buttonPressed() {
        if(Gdx.input.isTouched()) {
            if(back.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                if(fromGame) {
                    Gdx.app.log("Options", "Returning to World Screen");
                    manager.saveData();
                    game.setScreen(new World(game, true));
                } else {
                    Gdx.app.log("Options", "Returning to Title Screen");
                    game.setScreen(new TitleScreen(game, false));
                }

            } else if(musicOn.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY()) && manager.data.isMusicOn()) {
                manager.data.setMusicOn(false);
            } else if(musicOff.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY()) && !manager.data.isMusicOn()) {
                manager.data.setMusicOn(true);
            } else if(FXOn.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY()) && manager.data.isEffectsOn()) {
                manager.data.setEffectsOn(false);
            } else if(FXOff.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY()) && !manager.data.isEffectsOn()) {
                manager.data.setEffectsOn(true);
            } else if(InvertOn.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY()) && manager.data.isInvertControlsOn()) {
                manager.data.setInvertControlsOn(false);
            } else if(InvertOff.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY()) && !manager.data.isInvertControlsOn()) {
                manager.data.setInvertControlsOn(true);
            } else if(VSOn.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY()) && manager.data.isVsyncOn()) {
                manager.data.setVsyncOn(false);
            } else if(VSOff.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY()) && !manager.data.isVsyncOn()) {
                manager.data.setVsyncOn(true);
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
