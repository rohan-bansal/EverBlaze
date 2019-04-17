package main.java.com.rohan.everblaze.Effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Sound_Effects {

    private Music music;

    public Sound_Effects(String track, boolean looping) {
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/" + track + ".mp3"));
        if(looping) music.setLooping(true);
    }

    public void play() {
        music.play();
    }

    public void pause() {
        music.pause();
    }

    public void stop() {
        music.stop();
    }
}
