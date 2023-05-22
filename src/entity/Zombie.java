package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import Main.GamePanel;


public class Zombie extends Entity{

	GamePanel gp;
	private boolean moving = false;
	int screenX, screenY;
	int hitDelay = 1;
	
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
		worldX = 100;
		worldY = 0;
		
		floorHeight = 0;
		jumpStrength = 0;
		weight = 2;
		speed = 2;
		direction = "left";
	}
	
	public void getZombieImage() {
		try {
			left = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_left.png"));
			right = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_right.png"));
			walk_1_left = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_moveleft1.png"));
			walk_1_right = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_moveright1.png"));
			walk_2_jump_left = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_moveleft2.png"));
			walk_2_jump_right = ImageIO.read(getClass().getResourceAsStream("/zombie/zombie_moveright2.png"));
			
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
        if (deltaX != 0 && Math.abs(deltaX) < gp.tileSize * 10 ) {
        	if(!collisionOn) {
        		worldX += deltaX / Math.abs(deltaX); // Move horizontally
        		moving = true;
        	}else moving = false;
        	if (worldX < player.worldX)
        		direction = "right";
        	else direction = "left";
        }
        
        if (deltaY < 0 && Math.abs(deltaX) < gp.tileSize * 10 && !jumpCollision) { // change based on collision of floorheight
        	collisionOn = false;
        	action = "jump";
        }
        if(Math.abs(deltaX) >= gp.tileSize * 10 || Math.abs(deltaY) >= gp.tileSize * 10)
        	moving = false;
    }
	public void update(Player player) {
		if(health > 0) {
			collisionOn = false;
			jumpCollision = false;
			gp.cCollision.checkTile(this);
			gp.cCollision.checkZombieJump(this);
			
			//changing walking image based on keyHold
			if(collisionOn == false || jumpCollision == false) {
				if(action == "jump") {
					if(worldY >= floorHeight && jumpCounter > 55) {
						jumpStrength = 14;
						jumpCounter = 1;
					}
				}
				jumpCounter++;
				action = null;
			}
			
			
			if(moving) {
				spriteCounter++;
				if(spriteCounter > 20 ) {
					if(spriteNum == 0) {
						spriteNum = 1;
					}else if(spriteNum == 1) {
						spriteNum = 2;
					}else {
						spriteNum = 0;
					}
					spriteCounter = 1;
				}
			}
			else {
				spriteNum = 0;
			}
			
			// jumpCounter for jumping delay
			jumpCounter++;
			
			//gravity implementation
			gravity();
	
			gp.cCollision.checkFloor(this);
			moveTowardsPlayer(player);
			hitDelay++;
			if(hitDelay > 70) {
				hit(1,1,player,gp);
				hitDelay = 1;
			}
		}
	}
	
	public void draw(Graphics g2) {
			
		getZombieImage();
		BufferedImage image = null;
		if(health > 0) {
		switch(direction) {
		case "left" :
			if(spriteNum == 0 && !moving) {
				image = left;
			}else if(moving) {
				if(spriteNum == 1)
					image = walk_1_left;
				else
					image = walk_2_jump_left;
			}
			break;
		case "right" :
			if(spriteNum == 0 && !moving) {
				image = right;
			}else if(moving) {
				if(spriteNum == 1)
					image = walk_1_right;
				else
					image = walk_2_jump_right;
			}
			break;
		}

        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        
		g2.drawImage(image, screenX, screenY, gp.tileSize, 2*gp.tileSize, null);
		}
	}
}
