package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.GamePanel;

public class Entity {
	public int worldX;
	public int worldY;
	public int speed;
	public int health = 10;
	
	public BufferedImage left, right, walk_1_left, walk_1_right,
		walk_2_jump_left, walk_2_jump_right, punch_place_left, punch_place_right, crouch_left, crouch_right;
	public String direction;
	public String action;
	
	public Rectangle solidArea;
	public boolean collisionOn = false;
	public boolean jumpCollision = false;
	
	protected int spriteCounter = 1;
	protected int spriteNum = 0;
	
	protected int jumpCounter = 1;
	
	protected boolean falling = true;
	
	public int jumpStrength;
	protected int weight;
	
	protected int gravity = 9;
	
	public int floorHeight;
	
	protected void gravity() {
		
		if(falling && jumpStrength == 0) {
			if(worldY + gravity >= floorHeight) 
				worldY = floorHeight;
			else
				worldY += gravity*3/2;
		}
		if(jumpStrength >= 0) {
			worldY -= jumpStrength;
			jumpStrength -= weight;
		}else if(jumpStrength <= 0)
			jumpStrength = 0;
	}
	
	public void hit(int damage, int distance, Entity entity, GamePanel gp) {
		if( (distance >= Math.abs(worldX/gp.tileSize - entity.worldX/gp.tileSize) &&
				distance >= Math.abs(entity.floorHeight - floorHeight) ) &&
				entity.health > 0) {
			System.out.println(Math.abs(entity.floorHeight - floorHeight));
			gp.playSE(0);
			entity.health -= damage;
		}
			
	}
	
}
