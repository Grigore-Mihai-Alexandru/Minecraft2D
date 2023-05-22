package entity;

import Main.KeyHandler;
import Main.MouseHandler;
import Main.GamePanel;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	MouseHandler mouseH;
	public final int screenX;
	public final int screenY;
	private String prevAction;
	private int punchDelay = 20;
	
	public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH) {
		this.gp = gp;
		this.keyH = keyH;
		this.mouseH = mouseH;
		
		solidArea = new Rectangle();
		solidArea.x = 3;
		solidArea.y = 4;
		solidArea.width = 26;
		solidArea.height = 28;
		worldX = 0;
		worldY = 0;
		
		screenX = 400;
		screenY = 400;
		
		SetDefaultValues();
	}
	public void SetDefaultValues () {
		worldX = 0;
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
		}catch(IOException e ){
			e.printStackTrace();		
		}
	}
	
	//update or ticks function
	public void update() {
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

		gp.cCollision.checkFloor(this);
		
		//mouse Listener
		mouseListener();
	}
	
	public void draw(Graphics g2) {
		
		getPlayerImage();
		BufferedImage image = null;

		switch(direction) {
		case "left" :
			if(prevAction == "punch" || punchDelay < 20) {
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
			if(prevAction == "punch" || punchDelay < 20) {
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
	
	private void mouseListener() {
		int blockScreenX = (mouseH.mouseX-screenX)/gp.tileSize;
		int blockScreenY = (mouseH.mouseY-screenY)/gp.tileSize;
		int blockWorldX = (mouseH.mouseX - screenX + worldX)/gp.tileSize;
		int blockWorldY = (mouseH.mouseY - screenY + worldY)/gp.tileSize - 2 ; // - player height in blocks
		
		punchDelay++;
		if(mouseH.leftClicked) {
			action = "punch";
			if(blockScreenX < 5 && blockScreenY < 5 && 
					blockWorldX <= gp.maxWorldCol && blockWorldY <= gp.maxWorldRow &&
					blockWorldX >= 0 && blockWorldY >= 0)
				Tile.TileManager.breakBlock(blockScreenX, blockScreenY, blockWorldX, blockWorldY);
			mouseH.leftClicked = false;
			punchDelay = 1;
		}
		if(punchDelay > 1000)
			punchDelay = 20;
	}
	
	//getters and setters
	public int getX() {
		return worldX;
	}
	public int getY() {
		return worldY;
	}
}
