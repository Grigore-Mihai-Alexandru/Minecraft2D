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
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		solidArea = new Rectangle(4,0,6,6);
		
		
		SetDefaultValues();
	}
	public void SetDefaultValues () {
		x=100;
		y=gp.tileSize*(gp.maxScreenRow - 5);
		speed = 3;
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
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			
			if(keyH.upPressed == true) {
				action = "jump";
				y -= speed;
			}
			if(keyH.downPressed == true) {
				action = "crouch";
				y += speed;
			}
			if(keyH.leftPressed == true) {
				direction = "left";
				x -= speed;
			}
			if(keyH.rightPressed == true) {
				direction = "right";
				x += speed;
			}
			
			collisionOn = false;
			gp.cCollision.checkTile(this);
			
			spriteCounter++;
			
			if(spriteCounter > 10) {
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
		
	}
	
	public void draw(Graphics g2) {
		
		getPlayerImage();
		BufferedImage image = null;

		switch(direction) {
		case "left" :
			if(action == "jump") {
				image = walk_2_jump_left;
			}else {
				if(spriteNum == 1) {
					image = walk_1_left;
				}else if(spriteNum == 0){
					image = left;
				}else if(spriteNum == 2){
					image = walk_2_jump_left;
				}
			}
			break;
		case "right" :
			if(action == "jump") {
				image = walk_2_jump_right;
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
		
		g2.drawImage(image, x, y, gp.tileSize, 2*gp.tileSize, null);
	}
}
