package main.java.com.rohan.everblaze.TileInteraction.Objects;

public class ItemStack {

    public int count;
    public Item stackedItem;

    public ItemStack(Item stackedItem, int count) {
        this.stackedItem = stackedItem;
        this.count = count;
    }

    public ItemStack() {

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


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Item getStackedItem() {
        return stackedItem;
    }

    public void setStackedItem(Item stackedItem) {
        this.stackedItem = stackedItem;
    }
}
