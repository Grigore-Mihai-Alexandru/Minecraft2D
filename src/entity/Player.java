package entity;

import Main.KeyHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Main.GamePanel;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		SetDefaultValues();
	}
	public void SetDefaultValues () {
		x=100;
		y=200;
		speed = 4;
	}
	
	public void update() {
		if(keyH.upPressed == true) {
			y -= speed;
		}
		if(keyH.downPressed == true) {
			y += speed;
		}
		if(keyH.leftPressed == true) {
			x -= speed;
		}
		if(keyH.rightPressed == true) {
			x += speed;
		}
	}
	
	public void draw(Graphics g2) {
		g2.setColor(Color.white);
		
		g2.fillRect(x, y, gp.tileSize , 2 * gp.tileSize);
		
	}
}
