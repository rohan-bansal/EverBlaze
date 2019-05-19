package main.java.com.rohan.everblaze.Entities;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class MovementScript {


    private int intervalTime;
    private int stopTime;

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

        sequence.clear();
        sequence.add("Up");
        sequence.add("Stop");
        sequence.add("Right");
        sequence.add("Stop");
        sequence.add("Down");
        sequence.add("Stop");
        sequence.add("Left");
        sequence.add("Stop");

        intervalTime = 3;
        stopTime = 2;
    }

    private void counterClockwise2by2() {

        sequence.clear();
        sequence.add("Down");
        sequence.add("Stop");
        sequence.add("Right");
        sequence.add("Stop");
        sequence.add("Up");
        sequence.add("Stop");
        sequence.add("Left");
        sequence.add("Stop");


        intervalTime = 3;
        stopTime = 2;
    }

    private void upDown3() {

        sequence.clear();
        sequence.add("Up");
        sequence.add("Stop");
        sequence.add("Down");
        sequence.add("Stop");

        intervalTime = 4;
        stopTime = 2;
    }

    private void leftRight3() {

        sequence.clear();
        sequence.add("Right");
        sequence.add("Stop");
        sequence.add("Left");
        sequence.add("Stop");


        intervalTime = 4;
        stopTime = 2;
    }


    public int getIntervalTime() {
        return intervalTime;
    }

    public ArrayList<String> getSequence() {
        return sequence;
    }

    public int getStopTime() {
        return stopTime;
    }
}
