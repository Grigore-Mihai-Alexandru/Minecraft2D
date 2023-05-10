package entity;

import java.awt.image.BufferedImage;

import Main.KeyHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	public int worldX;
	public int worldY;
	private String prevAction;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		solidArea = new Rectangle();
		solidArea.x = 3;
		solidArea.y = 4;
		solidArea.width = 10;
		solidArea.height = 28;
		
		screenX = 400;
		screenY = 400;
		
		SetDefaultValues();
	}
	public void SetDefaultValues () {
		worldX = gp.tileSize*gp.maxScreenCol/2;
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
			
		}catch(IOException e ){
			e.printStackTrace();		
		}
	}
	
	//update or ticks function
	public void update() {
		
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
						worldX-= speed;
					break;
				case "right":
					if(keyH.rightPressed == true)
						worldX += speed;
					break;
				}
				if(action != null) {
					switch(action) {
					case "jump":
						if(worldY >= floorHeight && jumpCounter > 20) {
							jumpStrength = 14;
							jumpCounter = 1;
						}
						break;
					case "crouch":
						speed = 2;
						break;
					}
				}
				if(speed == 2 && keyH.downPressed == false)
					speed = 4;
				prevAction = action;
				action = null;
				
			}
						
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
		
		// jumpCounter for jumping delay
		jumpCounter++;
		
		//gravity implementation
		if(falling && jumpStrength == 0) {
			System.out.println(worldY );
			if(worldY + gravity >= floorHeight)
				worldY = floorHeight;
			else 
				worldY += gravity;
			
		}
		if(jumpStrength >= 0) {			
			worldY -= jumpStrength;
			jumpStrength -= weight;
		}else if(jumpStrength <= 0)
			jumpStrength = 0;
	}
	
	public void draw(Graphics g2) {
		
		getPlayerImage();
		BufferedImage image = null;

		switch(direction) {
		case "left" :
			if(prevAction == "jump" && keyH.leftPressed == false) {
				image = left;
				// add another else if for crouch action here!
			}else if(spriteNum == 1) {
				image = walk_1_left;
			}else if(spriteNum == 2){
				image = walk_2_jump_left;
			}else if(spriteNum == 0){
				image = left;
			}
			break;
		case "right" :
			if(prevAction == "jump" && keyH.rightPressed == false) {
				image = right;
				// same here
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
