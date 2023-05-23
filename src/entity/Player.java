package entity;

import Main.KeyHandler;
import Main.MouseHandler;
import Main.GamePanel;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

import Inventory.MinecraftHotbar;
import Inventory.MinecraftInventory;


public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	MouseHandler mouseH;
	Zombie zombie;
	public MinecraftInventory inventory = new MinecraftInventory(36);
	public final int screenX;
	public final int screenY;
	private String prevAction;
	private int punchDelay = 20;
	private int placeDelay = 20;
	
	public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH, Zombie zombie, MinecraftInventory inventory) {
		this.gp = gp;
		this.keyH = keyH;
		this.mouseH = mouseH;
		this.zombie = zombie;
		this.inventory = inventory;
		
		solidArea = new Rectangle();
		solidArea.x = 3;
		solidArea.y = 4;
		solidArea.width = 26;
		solidArea.height = 28;
		worldX = 0;
		worldY = 0;
		
		screenX = gp.tileSize*gp.maxScreenCol/2;
		screenY = 400;
		
		SetDefaultValues();
	}
	public void SetDefaultValues () {
		worldX = 520;
		worldY = 0;
		
		floorHeight = 0;
		jumpStrength = 0;
		weight = 2;
		speed = 4;
		direction = "right";
	}
	
	public void getPlayerImage() {
		try {
			left = ImageIO.read(getClass().getResourceAsStream("/player/steve_left.png"));
			right = ImageIO.read(getClass().getResourceAsStream("/player/steve_right.png"));
			walk_1_left = ImageIO.read(getClass().getResourceAsStream("/player/steve_firstmove_left.png"));
			walk_1_right = ImageIO.read(getClass().getResourceAsStream("/player/steve_firstmove_right.png"));
			walk_2_jump_left = ImageIO.read(getClass().getResourceAsStream("/player/steve_secondmove_jump_left.png"));
			walk_2_jump_right = ImageIO.read(getClass().getResourceAsStream("/player/steve_secondmove_right.png"));
			punch_place_left = ImageIO.read(getClass().getResourceAsStream("/player/steve_punch_place_left.png"));
			punch_place_right = ImageIO.read(getClass().getResourceAsStream("/player/steve_punch_place_right.png"));
			crouch_left= ImageIO.read(getClass().getResourceAsStream("/player/steve_crouch_left.png"));
			crouch_right = ImageIO.read(getClass().getResourceAsStream("/player/steve_crouch_right.png"));
		}catch(IOException e ){
			e.printStackTrace();		
		}
	}
	
	//update or ticks function
	public void update() {
		if(health > 0) {
		prevAction = null;
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if(keyH.upPressed == true) {
				action = "jump";
			}
			if(keyH.downPressed == true) {
				action = "crouch";
			}
			if(keyH.leftPressed == true) {
				direction = "left";
			}
			if(keyH.rightPressed == true) {
				direction = "right";
			}
			
			collisionOn = false;
			gp.cCollision.checkTile(this);
			
			if(collisionOn == false) {
				switch(direction) {
				case "left":
					if(keyH.leftPressed == true)
						worldX -= speed;
					break;
				case "right":
					if(keyH.rightPressed == true)
						worldX += speed;
					break;
				}
				if(action != null) {
					switch(action) {
					case "jump":
						if(worldY >= floorHeight && jumpCounter > 25) {
							jumpStrength = 14;
							jumpCounter = 1;
						}
						break;
					case "crouch":
						speed = 2;
						break;
					}
				}
				// jumpCounter for jumping delay
				jumpCounter++;
				if(speed == 2 && keyH.downPressed == false)
					speed = 4;
			}
			prevAction = action;
			action = null;
						
			//changing walking image based on keyHold
			spriteCounter++;
			
			if(spriteCounter > 10 ) {
				if(spriteNum == 0) {
					spriteNum = 1;
				}else if(spriteNum == 1) {
					spriteNum = 2;
				}else {
					spriteNum = 0;
				}
				spriteCounter = 1;
			}
			
		}else {
			spriteNum = 0;
		}
		
		//gravity implementation
		gravity();

		gp.cCollision.checkFloor(this);
		
		//mouse Listener
		mouseListener(zombie);
		}
	}
	
	public void draw(Graphics g2) {
		
		getPlayerImage();
		BufferedImage image = null;
		if(health > 0) {
			switch(direction) {
			case "left" :
				if(prevAction == "crouch" && keyH.downPressed) {
					image = crouch_left;
				}else if(prevAction == "punch" || punchDelay < 20) {
					image = punch_place_left;
				}else if(prevAction == "jump" && keyH.leftPressed == false) {
					image = left;
				}else if(spriteNum == 1) {
					image = walk_1_left;
				}else if(spriteNum == 2){
					image = walk_2_jump_left;
				}else if(spriteNum == 0){
					image = left;
				}
				break;
			case "right" :
				if(prevAction == "crouch") {
					image = crouch_right;
				}else if(prevAction == "punch" || punchDelay < 20) {
					image = punch_place_right;
				}else if(prevAction == "jump" && keyH.rightPressed == false) {
					image = right;
				}else {
					if(spriteNum == 1) {
						image = walk_1_right;
					}else if(spriteNum == 0){
						image = right;
					}else if(spriteNum == 2){
						image = walk_2_jump_right;
					}
				}
				break;
			}
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, 2*gp.tileSize, null);
		}
	}
	
	private void mouseListener(Entity entity) {
		int blockScreenX = (mouseH.mouseX - screenX)/gp.tileSize;
		int blockScreenY = (mouseH.mouseY - screenY)/gp.tileSize;
		int blockWorldX = (mouseH.mouseX - screenX + worldX)/gp.tileSize;
		int blockWorldY = (mouseH.mouseY - screenY + worldY)/gp.tileSize - 2 ; // - player height in blocks
		
		int blockId;
		
		punchDelay++;
		placeDelay++;
		if(mouseH.leftClicked) {
			action = "punch";
			if(zombie.screenX/gp.tileSize == mouseH.mouseX/gp.tileSize &&
					(zombie.screenY/gp.tileSize == mouseH.mouseY/gp.tileSize 
					|| zombie.screenY/gp.tileSize == mouseH.mouseY/gp.tileSize - 1 
					|| zombie.screenY/gp.tileSize == mouseH.mouseY/gp.tileSize + 1))
				hit(1,6, entity, gp);
			else if(blockScreenX < 5 && blockScreenY < 5 && 
					blockWorldX <= gp.maxWorldCol && blockWorldY <= gp.maxWorldRow &&
					blockWorldX >= 0 && blockWorldY >= 0) {
				blockId = Tile.TileManager.breakBlock(blockScreenX, blockScreenY, blockWorldX, blockWorldY);
				//add to inventory item
				if(blockId != 0)
					inventory.setItemFirstSlotAvailable(blockId,1);
//				inventory.printInventory();
			}
			mouseH.leftClicked = false;
			punchDelay = 1;
		}
//		right click logic
		
		int hotbarSlot = MinecraftHotbar.selectedSlot + 26;

		if(mouseH.rightClicked  && MinecraftInventory.slots[hotbarSlot] != null  && placeDelay >=20) {
			int hotbarSlotId = MinecraftInventory.slots[hotbarSlot].id;
			action = "punch";
			if(zombie.screenX/gp.tileSize != mouseH.mouseX/gp.tileSize &&
					zombie.screenY/gp.tileSize != mouseH.mouseY/gp.tileSize &&
					blockScreenX != screenX && blockScreenY != screenY) {	
					if(blockScreenX < 5 && blockScreenY < 5 && 
						blockWorldX <= gp.maxWorldCol && blockWorldY <= gp.maxWorldRow &&
						blockWorldX >= 0 && blockWorldY >= 0) 
					{
						Tile.TileManager.placeBlock(blockWorldX,blockWorldY,
								MinecraftInventory.slots[hotbarSlot].id);
						//remove item from inventory
						
//						if(Tile.TileManager.mapTileNum[blockWorldX][blockWorldY] == 0)
							gp.player.inventory.slots[hotbarSlot].quantity -= 1;
						
						
						if(gp.player.inventory.slots[hotbarSlot].quantity == 0)
							gp.player.inventory.slots[hotbarSlot] = null;
					}
				
			}
			mouseH.rightClicked = false;
			placeDelay = 1;
		}
		
		if(punchDelay > 1000)
			punchDelay = 20;
		if(placeDelay > 1000)
			placeDelay = 20;
	}
	
	//getters and setters
	public int getX() {
		return worldX;
	}
	public int getY() {
		return worldY;
	}
}
