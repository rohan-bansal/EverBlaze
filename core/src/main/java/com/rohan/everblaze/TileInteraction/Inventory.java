package main.java.com.rohan.everblaze.TileInteraction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import main.java.com.rohan.everblaze.Classifier;
import main.java.com.rohan.everblaze.Levels.InventoryOverlay;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Item;
import main.java.com.rohan.everblaze.Entities.Player;
import main.java.com.rohan.everblaze.FileUtils.GameManager;
import main.java.com.rohan.everblaze.Levels.World;
import main.java.com.rohan.everblaze.TileInteraction.Objects.ItemDurabilityBar;
import main.java.com.rohan.everblaze.TileInteraction.Objects.ItemStack;

import java.util.ArrayList;


public class Inventory {

    private Player player;
    public ArrayList<ItemStack> inventory;
    public static int slotSelected = 1;
    public Item itemSelected;
    public ArrayList<Sprite> slots;
    private SpriteBatch slotBatch;
    private float slotX = 300;

    public static boolean renderOverlay = false;

    private ItemDurabilityBar bar;
    public InventoryOverlay overlay;

    private BitmapFont nameDrawer = new BitmapFont();
    private BitmapFont itemCounter = new BitmapFont();

    private Sprite highlighted;

    public Inventory(Player player) {
        this.player = player;
        inventory = new ArrayList<ItemStack>();
        slots = new ArrayList<Sprite>();
        slotBatch = new SpriteBatch();
        bar = new ItemDurabilityBar();

        highlighted = new Sprite(new Texture(Gdx.files.internal("UI/invSlot2.jpg")));

        overlay = new InventoryOverlay(inventory, player);

        loadHotBar();
        refreshInventory();

    }

    public void loadHotBar() {

        Sprite temp;
        int xPos = 300;
        int incr = 1;
        while(incr < 11) {
            temp = new Sprite(new Texture(Gdx.files.internal("UI/invSlot.jpg")));
            temp.setCenter(xPos, 25);
            slots.add(temp);
            xPos += 50;
            incr++;
        }
    }

    public void addItem(Item item) {
        Gdx.app.log("Inventory", item.name + " added to inventory");
        item.setSprite();
        item.sprite.setCenter(slotX, 25);

        for(ItemStack item_ : inventory) {
            if(item_.stackedItem.name.equals(item.name) && (item.type.equals(Classifier.Utility) || item.type.equals(Classifier.Food))) {
                item_.addItem();
                return;
            }
        }
        slotX += 50;
        inventory.add(new ItemStack(item, 1));
    }

    public void render() {

        detectKeyPressed();

        slotBatch.begin();
        for(int y = 0; y < slots.size(); y++) {
            if(slots.indexOf(slots.get(y)) == slotSelected - 1) {
                highlighted.setCenter(slots.get(y).getX() + 25, slots.get(y).getY() + 25);
                if(!renderOverlay) highlighted.draw(slotBatch);
                try {
                    GlyphLayout layout = new GlyphLayout();
                    layout.setText(nameDrawer, inventory.get(y).stackedItem.name);
                    if(!renderOverlay) nameDrawer.draw(slotBatch, inventory.get(y).stackedItem.name, (inventory.get(y).stackedItem.sprite.getX() +
                            inventory.get(y).stackedItem.sprite.getWidth() / 2) - layout.width / 2, inventory.get(y).stackedItem.sprite.getY() + 60);
                } catch(Exception e) {
                }
            } else {
                if(!renderOverlay) slots.get(y).draw(slotBatch);
            }
        }

        for(ItemStack item : inventory) {
            if(!renderOverlay) item.stackedItem.render(slotBatch);
            if(item.stackedItem.durability <= 0) {
                World.itemStackToRemove.add(item);
            }
            if(item.count > 1) {
                itemCounter.draw(slotBatch, item.count + "", item.stackedItem.sprite.getX() + 25, item.stackedItem.sprite.getY() + 8);
            }
        }

        if(renderOverlay) {
            overlay.render(slotBatch, inventory, itemCounter);
        }

        slotBatch.end();

        bar.getDurRenderer().begin(ShapeRenderer.ShapeType.Filled);
        for(ItemStack item : inventory) {
            if(!item.stackedItem.type.equals(Classifier.Food) && !item.stackedItem.type.equals(Classifier.Utility)) {
                if(!renderOverlay) bar.render(item.stackedItem);
            }
        }
        bar.getDurRenderer().end();

        if(inventory.size() != 0) {
            if (slotSelected - 1 < inventory.size()) {
                itemSelected = inventory.get(slotSelected - 1).stackedItem;
            } else {
                itemSelected = null;
            }
        } else {
            itemSelected = null;
        }

        if(!renderOverlay) World.drawManager.render(slotBatch, 3);
    }


    public void detectKeyPressed() {
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            slotSelected = 1;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            slotSelected = 2;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            slotSelected = 3;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
            slotSelected = 4;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
            slotSelected = 5;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
            slotSelected = 6;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_7)) {
            slotSelected = 7;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_8)) {
            slotSelected = 8;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_9)) {
            slotSelected = 9;
        } else
        if(Gdx.input.isKeyPressed(Input.Keys.NUM_0)) {
            slotSelected = 10;
        } else
        if(Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            if(renderOverlay) {
                renderOverlay = false;
                refreshInventory();
            } else {
                openOverlay();
            }
        } else
        if(Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            if(!renderOverlay) dropItem();
        } else
        if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            useSelected();
        }

        if(World.autoPickup) {
            for(Item item : World.onFloor) {
                if(player.getRectangle().overlaps(item.sprite.getBoundingRectangle())) {
                    pickUpItem();
                }
            }
        }

    }

    private void openOverlay() {
        renderOverlay = true;
    }

    public void shiftSlotSelected(boolean direction) {
        if(direction) {
            if(slotSelected != 10) {
                slotSelected += 1;
            } else {
                slotSelected = 1;
            }
        } else {
            if(slotSelected != 1) {
                slotSelected -= 1;
            } else {
                slotSelected = 10;
            }
        }
    }

    public void useSelected() {
        if(inventory.size() != 0) {
            if(slotSelected - 1 < inventory.size()) {
                if(World.focus.equals("nothing")) {
                    Item item = inventory.get(slotSelected - 1).stackedItem;
                    //Gdx.app.log("Inventory", item.type);
                    if(item.type.equals("Food")) {
                        if(player.health != player.hearts) {
                            String[] desc = item.description.split(" ");
                            int restore = Integer.parseInt(desc[1]);
                            player.health += restore;
                            if(player.health > player.hearts) {
                                player.health = 10;
                            }
                            player.effect_eat.play();
                            boolean remove = inventory.get(slotSelected - 1).dropItem();
                            if(remove) {
                                inventory.remove(inventory.get(slotSelected - 1));
                            }
                            refreshInventory();
                        } else {
                            World.drawManager.setColor(Color.FIREBRICK);
                            World.drawManager.setPosition(new Vector2(390, 100));
                            World.drawManager.setText("Max Health");
                            World.drawManager.setSize(4);
                            World.drawManager.resetTimer();
                        }
                    } else if(item.type.equals("Weapon")) {
                        if(item.name.toLowerCase().contains("sword") || item.name.toLowerCase().contains("blade") || item.name.toLowerCase().contains("saber") || item.name.toLowerCase().contains("dagger") ||
                                item.name.toLowerCase().contains("knife") || item.name.toLowerCase().contains("rapier") || item.name.toLowerCase().contains("longsword") || item.name.toLowerCase().contains("shortsword")
                                || item.name.toLowerCase().contains("spear") || item.name.toLowerCase().contains("halberd") || item.name.toLowerCase().contains("trident") || item.name.toLowerCase().contains("sceptre")) {
                            player.attack();
                        }
                    }
                }
            }
        }

    }

    public void pickUpItem() {
        if(inventory.size() < 10) {
            if (World.detector.itemCollision(false) != null) {
                Item item = World.detector.itemCollision(false);
                Gdx.app.log("Inventory", "Picking Up Item : " + item.name);

                addItem(item);
                World.onFloorToRemove.add(item);
                //World.onFloor.remove(item);
            }
        }
    }

    public void dropItem() {
        if(inventory.size() != 0) {
            if(slotSelected <= inventory.size()) {
                //Item item_ = inventory.get(slotSelected - 1).stackedItem;
                //Gdx.app.log("Inventory", "Dropping Item : " + item_.name);

                //item_.setSprite();
                //item_.sprite.setCenter(player.position.x + player.WIDTH, player.position.y);
                //item_.sprite.setSize(16, 16);

                //World.onFloor.add(item_);
                boolean tempVar = inventory.get(slotSelected - 1).dropItem();
                if(tempVar) {
                    Item toDrop = new Item(inventory.get(slotSelected - 1).stackedItem);
                    inventory.remove(inventory.get(slotSelected - 1));
                    toDrop.setSprite();
                    toDrop.sprite.setCenter(player.position.x + player.WIDTH, player.position.y);
                    toDrop.sprite.setSize(16, 16);
                    World.onFloor.add(toDrop);
                    Gdx.app.log("Inventory", "Dropping Item : " + toDrop.name);
                } else {
                    Item toDrop = new Item(inventory.get(slotSelected - 1).stackedItem);
                    toDrop.setSprite();
                    toDrop.sprite.setCenter(player.position.x + player.WIDTH, player.position.y);
                    toDrop.sprite.setSize(16, 16);
                    World.onFloor.add(toDrop);
                    Gdx.app.log("Inventory", "Dropping Item : " + toDrop.name);
                }

                refreshInventory();
            }
        }
    }

    public void printInventory() {
        for(ItemStack item : inventory) {
            Gdx.app.log("Inventory", item.stackedItem.name);
        }
    }

    public void refreshInventory() {
        slotX = 300;
        for(ItemStack item : inventory) {
            item.stackedItem.setSprite();
            item.stackedItem.sprite.setCenter(slotX, 25);
            slotX += 50;
        }
    }

    public void loadInventory(GameManager manager) {
        inventory = manager.data.getInventory();
        refreshInventory();
        Gdx.app.log("Inventory", "Inventory Loaded, Sprite Textures Set");
    }
}
