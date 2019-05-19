package main.java.com.rohan.everblaze.Entities;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class MovementScript {


    private int intervalTime;
    private int stopTime;

    private ArrayList<String> sequence = new ArrayList<String>();

    public MovementScript(String type) {

        if(type.contains("clockwise") && !type.contains("counter")) {
            String[] params = type.split("_")[1].split("x");
            clockwise(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
        } else if(type.contains("counter") && type.contains("clockwise")) {
            String[] params = type.split("_")[1].split("x");
            counterClockwise(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
        } else if(type.contains("upDown")) {
            String[] params = type.split("_")[1].split("x");
            upDown(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
        } else if(type.contains("leftRight")) {
            String[] params = type.split("_")[1].split("x");
            leftRight(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
        }
    }

    private void clockwise(int inteT, int stopT) {

        sequence.clear();
        sequence.add("Up");
        sequence.add("Stop");
        sequence.add("Right");
        sequence.add("Stop");
        sequence.add("Down");
        sequence.add("Stop");
        sequence.add("Left");
        sequence.add("Stop");

        intervalTime = inteT;
        stopTime = stopT;
    }

    private void counterClockwise(int inteT, int stopT) {

        sequence.clear();
        sequence.add("Down");
        sequence.add("Stop");
        sequence.add("Right");
        sequence.add("Stop");
        sequence.add("Up");
        sequence.add("Stop");
        sequence.add("Left");
        sequence.add("Stop");


        intervalTime = inteT;
        stopTime = stopT;
    }

    private void upDown(int inteT, int stopT) {

        sequence.clear();
        sequence.add("Up");
        sequence.add("Stop");
        sequence.add("Down");
        sequence.add("Stop");

        intervalTime = inteT;
        stopTime = stopT;
    }

    private void leftRight(int inteT, int stopT) {

        sequence.clear();
        sequence.add("Right");
        sequence.add("Stop");
        sequence.add("Left");
        sequence.add("Stop");


        intervalTime = inteT;
        stopTime = stopT;
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
