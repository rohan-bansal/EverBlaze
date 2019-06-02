package main.java.com.rohan.everblaze.Entities;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class NPC {

    private MovementScript script;
    private String name;
    private Vector2 position;
    private String horizDirection;
    private ArrayList<String> sequence;
    private int currentSequenceItem;
    private String type;

    public NPC(String name, String type, int x, int y, MovementScript script) {
        this.script = script;
        this.name = name;
        this.type = type;
        this.position = new Vector2(x, y);

        this.horizDirection = "right";

        this.sequence = script.getSequence();
        currentSequenceItem = 0;
    }
}
