package com.rohan.everblaze;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.rohan.everblaze.Levels.TitleScreen;

public class Main extends Game {

    @Override
    public void create() {
        Gdx.app.log("Main", "Loading Libraries: LibGDX, Gradle");
        this.setScreen(new TitleScreen(this, false));

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }
}