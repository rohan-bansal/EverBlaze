package com.rohan.everblaze.FileUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.rohan.everblaze.Entities.Player;

public class GameManager {

    private FileHandle gameData;
    public GameData data;
    private Json json;
    private Player player;

    public GameManager(Player player) {
        this.player = player;
        this.json = new Json();
    }

    public boolean createNewSave(boolean override) {
        this.data = new GameData();

        boolean exists = Gdx.files.local("EverBlaze_Save").exists();

        if(exists && !override) {
            return true;
        }
        if(override || !Gdx.files.local("EverBlaze_Save").exists()) {
            this.data.setPlayerPosition(player.position);
            this.data.setInventory(player.inventory_.inventory);

            this.data.setMusicOn(true);
            this.data.setVsyncOn(true);
            this.data.setEffectsOn(true);
            this.data.setInvertControlsOn(false);

            this.gameData = Gdx.files.local("EverBlaze_Save");
            Gdx.app.log("Manager", "New Save Created");
            return false;
        }
        return false;
    }

    public void saveData() {
        if (gameData != null) {
            json.setIgnoreDeprecated(true);
            gameData.writeString(json.prettyPrint(data), false);
            Gdx.app.log("Manager", "Data Saved");
        }
    }

    public boolean loadData() {
        this.gameData = Gdx.files.local("EverBlaze_Save");
        try {
            data = json.fromJson(GameData.class, gameData.readString());
            Gdx.app.log("Manager", "Save Loaded");
            return true;
        } catch(Exception e) {
            Gdx.app.log("Manager", "Load Data Failed");
            e.printStackTrace();
            return false;
        }
    }
}
