package main.java.com.rohan.everblaze.Entities;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class MovementScript {

    private int pixelsUp;
    private int pixelsDown;
    private int pixelsRight;
    private int pixelsLeft;

    private int intervalTime;

    private ArrayList<String> sequence = new ArrayList<String>();

    public MovementScript(String type) {

        if(type.equals("clockwise_2x2")) {
            clockwise2by2();
        } else if(type.equals("counterClockwise_2x2")) {
            counterClockwise2by2();
        } else if(type.equals("upDown3")) {
            upDown3();
        } else if(type.equals("leftRight3")) {
            leftRight3();
        }
    }

    private void clockwise2by2() {
        pixelsUp = 32;
        pixelsDown = 32;
        pixelsRight = 32;
        pixelsLeft = 32;

        sequence.clear();
        sequence.add("Up");
        sequence.add("Right");
        sequence.add("Down");
        sequence.add("Left");

        intervalTime = 2;
    }

    private void counterClockwise2by2() {
        pixelsUp = 32;
        pixelsDown = 32;
        pixelsRight = 32;
        pixelsLeft = 32;

        sequence.clear();
        sequence.add("Down");
        sequence.add("Rightme");
        sequence.add("Up");
        sequence.add("Left");

        intervalTime = 2;
    }

    private void upDown3() {
        pixelsUp = 48;
        pixelsDown = 48;

        sequence.clear();
        sequence.add("Up");
        sequence.add("Down");

        intervalTime = 1;
    }

    private void leftRight3() {
        pixelsRight = 48;
        pixelsLeft = 48;

        sequence.clear();
        sequence.add("Right");
        sequence.add("Left");

        intervalTime = 1;
    }

    public int getPixelsUp() {
        return pixelsUp;
    }

    public int getPixelsDown() {
        return pixelsDown;
    }

    public int getPixelsRight() {
        return pixelsRight;
    }

    public int getPixelsLeft() {
        return pixelsLeft;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public ArrayList<String> getSequence() {
        return sequence;
    }
}
