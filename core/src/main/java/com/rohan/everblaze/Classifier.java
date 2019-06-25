package main.java.com.rohan.everblaze;

import main.java.com.rohan.everblaze.Entities.Good.Adventurer;
import main.java.com.rohan.everblaze.Entities.Good.Blacksmith;
import main.java.com.rohan.everblaze.Entities.Good.FoodVendor;

import java.util.ArrayList;

public class Classifier {

    public static String Weapon = "Weapon";
    public static String Tool = "Tool";
    public static String Food = "Food";
    public static String Utility = "Utility";
    public static String Chest = "TreasureChest";

    public static String Green_Slime = "Green_Slime";
    public static String Orange_Slime = "Orange_Slime";
    public static String Purple_Slime = "Purple_Slime";

    public static String Goblin = "Goblin";
    public static String Skeleton = "Skeleton";

    public static String Blacksmith = "Blacksmith";
    public static String Villager = "Villager";
    public static String Adventurer = "Adventurer";
    public static String FoodVendor = "FoodVendor";

    public static ArrayList<String> marketplaceNPCs = new ArrayList<String>() {{
        add(Blacksmith);
        add(Adventurer);
        add(FoodVendor);
    }};
}
