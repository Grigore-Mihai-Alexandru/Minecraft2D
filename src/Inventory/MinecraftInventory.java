package Inventory;

public class MinecraftInventory {
	private Slot[] slots = new Slot[5];
    private int size;

    public MinecraftInventory(int size) {
        this.size = size;
        slots = new Slot[size];
    }

    public void setItem(int slot, int itemId, int quantity) {
        if (slot >= 0 && slot < size) {
            slots[slot].id = itemId;
            slots[slot].quantity = quantity;
        } else {
            System.out.println("Invalid slot!");
        }
    }

    public void setItemFirstSlotAvailable(int itemId, int quantity) {
    	for(int slot = 0; slot < size; slot++) {
    		if(slots[slot].id == itemId && slots[slot].quantity < slots[slot].stackedAt) {
    			slots[slot].id = itemId;
    			if(slots[slot].quantity + quantity <= slots[slot].stackedAt) {
    				slots[slot].quantity += quantity;    				
    			}else
    				slots[slot].quantity = slots[slot].stackedAt;
    		}
    	}
    }
    
    public Slot getItem(int slot) {
        if (slot >= 0 && slot < size) {
            return slots[slot];
        } else {
            System.out.println("Invalid slot!");
            return null;
        }
    }

    public void printInventory() {
        System.out.println("Inventory:");
        for (int i = 0; i < size; i++) {
            System.out.println("Slot " + i + ": " + slots[i]);
        }
    }

}
