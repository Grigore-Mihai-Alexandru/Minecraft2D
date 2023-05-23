package Inventory;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Tile.TileManager;

public class Slot {
	public int id;
	public int quantity = 0;
	public int stackedAt = 64;
	public BufferedImage image;
	
	public Slot (int id, int quantity, int stackedAt) {
		this.id = id;
		this.quantity = quantity;
		this.stackedAt = stackedAt;
		image = TileManager.tile[id].image;
	}
	
}
