package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	public int worldX, worldY;
	public int speed;
	
	public BufferedImage left, right, walk_1_left, walk_1_right, walk_2_jump_left, walk_2_jump_right;
	public String direction;
	public String action;
	
	public Rectangle solidArea;
	public boolean collisionOn = false;
	
	public int spriteCounter = 1;
	public int spriteNum = 0;
	
	
}
