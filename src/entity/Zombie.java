package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import Main.GamePanel;


public class Zombie extends Entity{

	GamePanel gp;
	
	public Zombie(GamePanel gp) {
		this.gp = gp;
		
		solidArea = new Rectangle();
		solidArea.x = 3;
		solidArea.y = 4;
		solidArea.width = 26;
		solidArea.height = 28;
		
		worldX = 0;
		worldY = 0;
		
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
	
	public void getZombieImage() {
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
	
	public void moveTowardsPlayer(Player player) {
		int playerX = player.getX();
        int playerY = player.getY();

        // Calculate the direction towards the player
        int deltaX = playerX - worldX;
        int deltaY = playerY - worldY;

        // Update the zombie's position based on the direction
        if (deltaX != 0) {
        	worldX += deltaX / Math.abs(deltaX); // Move horizontally
        }
        if (deltaY != 0) {
        	worldY += deltaY / Math.abs(deltaY); // Move vertically
        }
    }
	public void update(Player player) {
		
		collisionOn = false;
//		gp.cCollision.checkTile(this);
//		System.out.println(collisionOn);
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
		}else {
			spriteNum = 0;
		}
		
		// jumpCounter for jumping delay
		jumpCounter++;
		
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
			
		moveTowardsPlayer(player);
		
	}
	
	public void draw(Graphics g2) {
			
			getZombieImage();
			BufferedImage image = null;
	
			switch(direction) {
			case "left" :
				if(spriteNum == 1) {
					image = walk_1_left;
				}else if(spriteNum == 2){
					image = walk_2_jump_left;
				}else if(spriteNum == 0){
					image = left;
				}
				break;
			case "right" :
				if(spriteNum == 1) {
					image = walk_1_right;
				}else if(spriteNum == 0){
					image = right;
				}else if(spriteNum == 2){
					image = walk_2_jump_right;
				}
				break;
		}
	}
}
