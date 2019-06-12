package main.java.com.rohan.everblaze.TileInteraction.Objects;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.text.Bidi;
import java.util.HashMap;

public class ItemStack {

    public int count;
    public Item stackedItem;

    public ItemStack(Item stackedItem, int count) {
        this.stackedItem = stackedItem;
        this.count = count;
    }

    public boolean dropItem() {
        if(count > 1) {
            count -= 1;
            return false;
        } else {
            return true;
        }
    }

    public void addItem() {
        count += 1;
    }
}
