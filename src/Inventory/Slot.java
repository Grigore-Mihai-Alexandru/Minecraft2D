package Inventory;

public class Slot {
	public int id;
	public int quantity = 0;
	public int stackedAt = 1;
	
	public Slot (int id, int quantity, int stackedAt) {
		this.id = id;
		this.quantity = quantity;
		this.stackedAt = stackedAt;
	}
	
}
