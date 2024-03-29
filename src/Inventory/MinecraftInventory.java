package Inventory;


public class MinecraftInventory {
	public static Slot[] slots = new Slot[36];
    private int size;
    public boolean isActive = false;

    public MinecraftInventory(int size) {
        this.size = size;
        slots = new Slot[size];
    }

    public void setItem(int slot, int itemId, int quantity) {
        if (slot >= 0 && slot < size) {
            slots[slot].id = itemId;
            if(slots[slot].quantity + quantity <= slots[slot].stackedAt) {
				slots[slot].quantity += quantity;
			}else {
				slots[slot].quantity = slots[slot].stackedAt;
			}
		} else {
            System.out.println("Invalid slot!");
        }
    }

    public void setItemFirstSlotAvailable(int itemId, int quantity) {
    	boolean breakCheck = false;
    	for(int slot = 27; slot < size; slot++) {
    		if(slots[slot] != null && slots[slot].id == itemId && slots[slot].quantity < slots[slot].stackedAt) {
    			slots[slot].id = itemId;
    			if(slots[slot].quantity + quantity <= slots[slot].stackedAt) {
    				slots[slot].quantity += quantity;
    			}else
    				slots[slot].quantity = slots[slot].stackedAt;
    			breakCheck = true;
    			break;
    		}else if(slots[slot] == null) {
    			slots[slot] = new Slot(itemId,quantity,64);
    			breakCheck = true;
    			break;
    		}
    	}
    	// case if hotbar is full
    	if(breakCheck)
    		return ;

    	for(int slot = 0; slot < size; slot++) {
    		if(slots[slot] != null && slots[slot].id == itemId && slots[slot].quantity < slots[slot].stackedAt) {
    			slots[slot].id = itemId;
    			if(slots[slot].quantity + quantity <= slots[slot].stackedAt) {
    				slots[slot].quantity += quantity;
    			}else
    				slots[slot].quantity = slots[slot].stackedAt;
    			break;
    		}else if(slots[slot] == null) {
    			slots[slot] = new Slot(itemId,quantity,64);
    			break;
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
        	if(slots[i]!=null)
            System.out.println("Slot " + i + ":id=" + slots[i].id + " quantity=" + slots[i].quantity);
        }
    }

}
