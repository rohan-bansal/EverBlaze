package main.java.com.rohan.everblaze.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import main.java.com.rohan.everblaze.Classifier;
import main.java.com.rohan.everblaze.Entities.Player;
import main.java.com.rohan.everblaze.FileUtils.Quest;
import main.java.com.rohan.everblaze.TileInteraction.Inventory;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Item;
import main.java.com.rohan.everblaze.TileInteraction.Objects.ItemStack;

import java.util.ArrayList;

public class InventoryOverlay {

    private Player player;

    private Sprite questHighlight = new Sprite(new Texture(Gdx.files.internal("UI/HUD/Inventory/questCardHighlight.png")));
    private Sprite inventory, quests, healthbar, highlighted;
    private BitmapFont itemCounter = new BitmapFont();

    private BitmapFont nameDrawer = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);
    private BitmapFont descriptionDrawer = new BitmapFont();
    private BitmapFont dmgHealthDrawer = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);
    private BitmapFont durabilityDrawer = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);
    private BitmapFont healthDrawer = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);
    private BitmapFont coinDrawer = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);

    private BitmapFont questDescDrawer = new BitmapFont(Gdx.files.internal("Fonts/Retron2.fnt"), Gdx.files.internal("Fonts/Retron2.png"), false);
    private BitmapFont escapeDrawer = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);
    private BitmapFont questNameDrawer = new BitmapFont(Gdx.files.internal("Fonts/turok2.fnt"), Gdx.files.internal("Fonts/turok2.png"), false);
    private BitmapFont questLocationDrawer = new BitmapFont(Gdx.files.internal("Fonts/ari2.fnt"), Gdx.files.internal("Fonts/ari2.png"), false);




    private ArrayList<Sprite> slots = new ArrayList<Sprite>();
    public ArrayList<ItemStack> inventory_;
    private int slotSelected = 0;

    private Quest renderQuestDesc_ = null;


    public InventoryOverlay(ArrayList<ItemStack> inventory_, Player player) {

        this.player = player;

        this.inventory_ = inventory_;
        highlighted = new Sprite(new Texture(Gdx.files.internal("UI/invSlot2.jpg")));

        int xPos = 610;
        int yPos = 540;
        int incr = 1;
        while(incr < 11) {
            Sprite temp = new Sprite(new Texture(Gdx.files.internal("UI/invSlot.jpg")));
            temp.setCenter(xPos, yPos);
            slots.add(temp);
            xPos += 70;
            if(xPos > 940) {
                xPos = 610;
                yPos -= 70;
            }
            incr++;
        }
        inventory = new Sprite(new Texture(Gdx.files.internal("UI/HUD/Inventory/inventory.png")));
        healthbar = new Sprite(new Texture(Gdx.files.internal("UI/HUD/Inventory/healthbar.png")));
        quests = new Sprite(new Texture(Gdx.files.internal("UI/HUD/Inventory/quests.png")));

        inventory.setCenter(750, 400);
        healthbar.setCenter(300, 720);
        quests.setCenter(320, 360);
    }

    public void drawInventory(SpriteBatch batch, Sprite highlight, BitmapFont nameDrawer, ArrayList<ItemStack> chestInv) {
        inventory.draw(batch);

        this.inventory_ = World.detector.player.inventory_.inventory;

        for(int x = 0; x < 10; x++) {
            if(slots.get(x).getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                highlight.setCenter(slots.get(x).getX() + 25, slots.get(x).getY() + 25);
                highlight.draw(batch);
                GlyphLayout layout = new GlyphLayout();
                try {
                    layout.setText(nameDrawer, inventory_.get(x).stackedItem.name);
                    nameDrawer.draw(batch, inventory_.get(x).stackedItem.name, (inventory_.get(x).stackedItem.sprite.getX() +
                            inventory_.get(x).stackedItem.sprite.getWidth() / 2) - layout.width / 2, inventory_.get(x).stackedItem.sprite.getY() + 60);
                    if(Gdx.input.justTouched()) {
                        ItemStack item = inventory_.get(x);
                        chestInv.add(item);
                        World.itemStackToRemove.add(inventory_.get(x));
                        World.detector.player.inventory_.refreshInventory();
                    }
                } catch (IndexOutOfBoundsException e) {
                }

            } else {
                slots.get(x).draw(batch);
            }

            try {
                this.inventory_.get(x).stackedItem.sprite.setCenter(slots.get(x).getX() + (slots.get(x).getWidth() / 2), slots.get(x).getY() + (slots.get(x).getHeight() / 2));
                this.inventory_.get(x).stackedItem.render(batch);
                if(this.inventory_.get(x).count > 1) {
                    itemCounter.draw(batch, this.inventory_.get(x).count + "", this.inventory_.get(x).stackedItem.sprite.getX() + 25, this.inventory_.get(x).stackedItem.sprite.getY() + 8);
                }
            } catch (IndexOutOfBoundsException e) {
            }

        }
    }

    public void renderQuestDesc(Quest quest, SpriteBatch batch) {
        World.focus = "questMenu";

        Sprite questDesc = new Sprite(new Texture(Gdx.files.internal("UI/HUD/Inventory/questDescription.png")));
        questDesc.setCenter(500, 400);
        questDesc.draw(batch);

        escapeDrawer.setColor(Color.SLATE);
        escapeDrawer.getData().setScale(0.5f);
        escapeDrawer.draw(batch, "Press 'Esc' to hide this window", 400, 200);

        GlyphLayout layout = new GlyphLayout();
        layout.setText(questDescDrawer, quest.getDescription());
        questDescDrawer.setColor(Color.GOLD);
        questDescDrawer.getData().setScale(0.5f);
        questDescDrawer.draw(batch, quest.getDescription(), (questDesc.getX() +
                questDesc.getWidth() / 2) - layout.width / 2, 490);

        layout.setText(questNameDrawer, quest.getQuestName());
        questNameDrawer.setColor(Color.SCARLET);
        questNameDrawer.getData().setScale(1.5f);
        questNameDrawer.draw(batch, quest.getQuestName(), (questDesc.getX() +
                questDesc.getWidth() / 2) - layout.width / 2, 600);

        layout.setText(questLocationDrawer, quest.getLocation());
        questLocationDrawer.setColor(Color.GOLD);
        questLocationDrawer.getData().setScale(0.7f);
        questLocationDrawer.draw(batch, quest.getLocation(), (questDesc.getX() +
                questDesc.getWidth() / 2) - layout.width / 2, 535);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            renderQuestDesc_ = null;
        }
    }

    public void render(SpriteBatch batch, ArrayList<ItemStack> inventory_, BitmapFont itemCounter) {

        this.inventory_ = inventory_;

        inventory.draw(batch);
        healthbar.draw(batch);
        quests.draw(batch);


        for(int x = 0; x < 10; x++) {
            if(slots.indexOf(slots.get(x)) == Inventory.slotSelected - 1) {
                highlighted.setCenter(slots.get(x).getX() + 25, slots.get(x).getY() + 25);
                highlighted.draw(batch);
            } else {
                slots.get(x).draw(batch);
            }
            try {
                this.inventory_.get(x).stackedItem.sprite.setCenter(slots.get(x).getX() + (slots.get(x).getWidth() / 2), slots.get(x).getY() + (slots.get(x).getHeight() / 2));
                this.inventory_.get(x).stackedItem.render(batch);
                if(this.inventory_.get(x).count > 1) {
                    itemCounter.draw(batch, this.inventory_.get(x).count + "", this.inventory_.get(x).stackedItem.sprite.getX() + 22, this.inventory_.get(x).stackedItem.sprite.getY() + 8);
                }
            } catch (IndexOutOfBoundsException e) {
            }

        }

        try {
            Item tempItem = this.inventory_.get(Inventory.slotSelected - 1).stackedItem;
            GlyphLayout layout = new GlyphLayout();
            layout.setText(nameDrawer, tempItem.name);
            nameDrawer.setColor(Color.GOLD);
            //nameDrawer.getData().setScale(2);
            nameDrawer.draw(batch, tempItem.name, (inventory.getX() +
                    inventory.getWidth() / 2) - layout.width / 2, 420);

            layout.setText(descriptionDrawer, tempItem.description);
            descriptionDrawer.setColor(Color.GOLD);
            descriptionDrawer.draw(batch, tempItem.description, (inventory.getX() +
                    inventory.getWidth() / 2) - layout.width / 2, 380);

            dmgHealthDrawer.setColor(Color.SCARLET);
            dmgHealthDrawer.getData().setScale(0.6f);
            if(tempItem.type.equals(Classifier.Weapon)) {
                layout.setText(dmgHealthDrawer, "Damage : " + tempItem.damage);
                dmgHealthDrawer.draw(batch, "Damage : " + tempItem.damage, (inventory.getX() +
                        inventory.getWidth() / 2) - layout.width / 2, 350);
            }

            durabilityDrawer.getData().setScale(0.6f);
            if(!tempItem.type.equals(Classifier.Food) && !tempItem.type.equals(Classifier.Utility)) {
                layout.setText(durabilityDrawer, "Durability : " + tempItem.durability);
                float itemDur = (float) tempItem.durability/tempItem.baseDur;
                if(itemDur > 0.6) {
                    durabilityDrawer.setColor(Color.GREEN);
                } else if(itemDur >= 0.35 && itemDur <= 0.6) {
                    durabilityDrawer.setColor(Color.YELLOW);
                } else if(itemDur < 0.35) {
                    durabilityDrawer.setColor(Color.RED);
                }
                durabilityDrawer.draw(batch, "Durability : " + tempItem.durability, (inventory.getX() +
                        inventory.getWidth() / 2) - layout.width / 2, 330);
            }

        } catch (IndexOutOfBoundsException e) {
        }

        GlyphLayout layout2 = new GlyphLayout();
        layout2.setText(healthDrawer, player.health + " / " + player.hearts);
        healthDrawer.setColor(Color.GREEN);
        healthDrawer.getData().setScale(0.8f);
        healthDrawer.draw(batch, player.health + " / " + player.hearts, (healthbar.getX() +
                healthbar.getWidth() / 2) - layout2.width / 2, 730);

        layout2.setText(coinDrawer, player.coins + "");
        coinDrawer.setColor(Color.BROWN);
        coinDrawer.getData().setScale(0.8f);
        coinDrawer.draw(batch, player.coins + "", (healthbar.getX() +
                healthbar.getWidth() / 2) - layout2.width / 2, 690);

        for(Quest quest : World.quests) {
            quest.renderCard(batch);

            if(quest.card.getBoundingRectangle().contains(Gdx.input.getX(), 800 - Gdx.input.getY())) {
                questHighlight.setPosition(quest.card.getX(), quest.card.getY());
                questHighlight.draw(batch);
                quest.renderText(batch);

                if(Gdx.input.justTouched()) {
                    renderQuestDesc_ = quest;
                }

            } else {
                quest.renderCard(batch);
            }
        }

        if(renderQuestDesc_ != null) {
            renderQuestDesc(renderQuestDesc_, batch);
        }

    }
}
