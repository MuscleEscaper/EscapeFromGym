public class InventoryManage {
    private final int capacity = 3;
    private int massitem = 0;
    private ItemData[] slots;

    private static InventoryManage instance;

    public static InventoryManage getInstance() {
        if (instance == null) {
            instance = new InventoryManage();
        }
        return instance;
    }

    public InventoryManage() {
        this.slots = new ItemData[capacity];
    }

    public boolean haveHoldingItem() {
        for (int i = 0; i < capacity; i++) {
            if (slots[i] != null && slots[i].getIsHolding()) {
                return true;
            }
        }
        return false;
    }

    public void resetIsHolding() {
        for (int i = 0; i < capacity; i++) {
            if (slots[i] != null) {
                slots[i].setIsHolding(false);
            }
        }
    }

    /**
     * 何も入っていないスペースを見つけてItemを追加する。
     */
    public void addItem(ItemData item) {
        for (int i = 0; i < capacity; i++) {
            if (slots[i] == null) {
                slots[i] = item;
                massitem++;
                break;
            }
        }
    }

    /**
     * Itemを取り出し、Inventoryを左詰めにする
     */
    public ItemData removeItem(int slotIndex) {
        if (slotIndex >= 0 && slotIndex < capacity && slots[slotIndex] != null) {
            ItemData removed = slots[slotIndex];
            slots[slotIndex] = null;
            // 左詰め処理
            for (int i = slotIndex; i < massitem; ++i) {
                if (i != capacity - 1) { // 8ではなくcapacity-1と書くのが安全
                    slots[i] = slots[i + 1];
                } else {
                    slots[i] = null;
                }
            }
            massitem--;
            return removed;
        }
        return null;
    }

    /**
     * 指定したItemをInventoryから取り除く
     * 
     * @param item 取り除きたいItem
     */
    public void removeItem(ItemData item) {
        int index = -1;
        for (int i = 0; i < capacity; i++) {
            if (slots[i] == item) {
                index = i;
                slots[i] = null;
                break;
            }
        }
        for (int i = index; i < massitem; i++) {
            slots[i] = slots[i + 1];
        }
        massitem--;
    }

    /**
     * 指定したインデックスのアイテムを取得する（UI表示用）
     * これがないとInventoryUIがデータを読めません。
     */
    public ItemData getItem(int index) {
        if (index >= 0 && index < capacity) {
            return slots[index];
        }
        return null;
    }

    public int getCapacity() {
        return capacity;
    }
}