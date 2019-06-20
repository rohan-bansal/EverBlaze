package main.java.com.rohan.everblaze.FileUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import main.java.com.rohan.everblaze.Entities.Good.NPC;
import main.java.com.rohan.everblaze.Levels.World;

public class QuestManager {

    private FileHandle data;
    public Quest questData;
    private Json json;
    private NPC npc;

    public QuestManager(NPC npc, FileHandle data) {
        this.questData = new Quest();
        this.json = new Json();
        this.npc = npc;
        this.data = data;

        parseRequirements();
    }

    private void parseRequirements() {
        try {
            questData = json.fromJson(Quest.class, data.readString());
            World.addQuest(questData);
            Gdx.app.log("Quests", "Quest Loaded : " + questData.getQuestName());
        } catch(Exception e) {
            Gdx.app.log("Quests", "Quest Parsing Failed");
            e.printStackTrace();
        }
    }
}
