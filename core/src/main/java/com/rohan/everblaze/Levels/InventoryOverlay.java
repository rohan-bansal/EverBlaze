package main.java.com.rohan.everblaze.Levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import main.java.com.rohan.everblaze.Classifier;
import main.java.com.rohan.everblaze.TileInteraction.Inventory;
import main.java.com.rohan.everblaze.TileInteraction.Objects.Item;
import main.java.com.rohan.everblaze.TileInteraction.Objects.ItemStack;

import java.util.ArrayList;

public class InventoryOverlay {

    private Sprite inventory, quests, highlighted;

    private BitmapFont nameDrawer = new BitmapFont();
    private BitmapFont descriptionDrawer = new BitmapFont();
    private BitmapFont dmgHealthDrawer = new BitmapFont();


    private ArrayList<Sprite> slots = new ArrayList<Sprite>();
    public ArrayList<ItemStack> inventory_;

    public InventoryOverlay(ArrayList<ItemStack> inventory_) {

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
        inventory.setCenter(750, 450);
    }

    public void render(SpriteBatch batch, ArrayList<ItemStack> inventory_) {

        this.inventory_ = inventory_;

        inventory.draw(batch);

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
            } catch (IndexOutOfBoundsException e) {
            }

        }

        if(this.inventory_.get(Inventory.slotSelected - 1) != null) {
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

        }

    }
}
