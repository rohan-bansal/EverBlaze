package main.java.com.rohan.everblaze.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.java.com.rohan.everblaze.Classifier;
import main.java.com.rohan.everblaze.Entities.Player;
import main.java.com.rohan.everblaze.TileInteraction.Inventory;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Item;
import main.java.com.rohan.everblaze.TileInteraction.Objects.ItemStack;

import java.util.ArrayList;

public class InventoryOverlay {

    private Player player;

    private Sprite inventory, quests, healthbar, highlighted;

    private BitmapFont nameDrawer = new BitmapFont();
    private BitmapFont descriptionDrawer = new BitmapFont();
    private BitmapFont dmgHealthDrawer = new BitmapFont();
    private BitmapFont durabilityDrawer = new BitmapFont();
    private BitmapFont healthDrawer = new BitmapFont();
    private BitmapFont coinDrawer = new BitmapFont();



    private ArrayList<Sprite> slots = new ArrayList<Sprite>();
    public ArrayList<ItemStack> inventory_;

    public InventoryOverlay(ArrayList<ItemStack> inventory_, Player player) {

        this.player = player;

        this.inventory_ = inventory_;
        highlighted = new Sprite(new Texture(Gdx.files.internal("UI/invSlot2.jpg")));

        int xPos = 610;
        int yPos = 600;
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

        inventory.setCenter(750, 450);
        healthbar.setCenter(215, 720);
    }

    public void render(SpriteBatch batch, ArrayList<ItemStack> inventory_, BitmapFont itemCounter) {

        this.inventory_ = inventory_;

        inventory.draw(batch);
        healthbar.draw(batch);

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
                    itemCounter.draw(batch, this.inventory_.get(x).count + "", this.inventory_.get(x).stackedItem.sprite.getX() + 25, this.inventory_.get(x).stackedItem.sprite.getY() + 8);
                }
            } catch (IndexOutOfBoundsException e) {
            }

        }

        try {
            Item tempItem = this.inventory_.get(Inventory.slotSelected - 1).stackedItem;
            GlyphLayout layout = new GlyphLayout();
            layout.setText(nameDrawer, tempItem.name);
            nameDrawer.setColor(Color.GOLD);
            nameDrawer.getData().setScale(2);
            nameDrawer.draw(batch, tempItem.name, (inventory.getX() +
                    inventory.getWidth() / 2) - layout.width / 2, 460);

            layout.setText(descriptionDrawer, tempItem.description);
            descriptionDrawer.setColor(Color.GOLD);
            descriptionDrawer.draw(batch, tempItem.description, (inventory.getX() +
                    inventory.getWidth() / 2) - layout.width / 2, 420);

            dmgHealthDrawer.setColor(Color.FIREBRICK);
            if(tempItem.type.equals(Classifier.Weapon)) {
                layout.setText(dmgHealthDrawer, "Damage : " + tempItem.damage);
                dmgHealthDrawer.draw(batch, "Damage : " + tempItem.damage, (inventory.getX() +
                        inventory.getWidth() / 2) - layout.width / 2, 390);
            }

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
                        inventory.getWidth() / 2) - layout.width / 2, 370);
            }

            layout.setText(healthDrawer, player.health + " / " + player.hearts);
            healthDrawer.setColor(Color.GREEN);
            healthDrawer.getData().setScale(2);
            healthDrawer.draw(batch, player.health + " / " + player.hearts, (healthbar.getX() +
                    healthbar.getWidth() / 2) - layout.width / 2, 730);

            layout.setText(coinDrawer, player.coins + "");
            coinDrawer.setColor(Color.GOLDENROD);
            coinDrawer.getData().setScale(2);
            coinDrawer.draw(batch, player.coins + "", (healthbar.getX() +
                    healthbar.getWidth() / 2) - layout.width / 2, 690);


        } catch (IndexOutOfBoundsException e) {
        }

    }
}
