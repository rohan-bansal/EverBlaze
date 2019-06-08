package main.java.com.rohan.everblaze.FileUtils;

import java.util.ArrayList;

public class Quest {

    private String questName;
    private String NPC;
    private String description;
    private ArrayList<String> reward;
    private String questType;

    public String getQuestType() {
        return questType;
    }

    public void setQuestType(String questType) {
        this.questType = questType;
    }

    public String getQuestName() {
        return questName;
    }

    public void setQuestName(String questName) {
        this.questName = questName;
    }

    public String getNPC() {
        return NPC;
    }

    public void setNPC(String NPC) {
        this.NPC = NPC;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getReward() {
        return reward;
    }

    public void setReward(ArrayList<String> reward) {
        this.reward = reward;
    }
}
