package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Inventory.MinecraftHotbar;
import entity.Player;

public class UI {
	GamePanel gp;
	Player player;
	Font arial_40;
	public Color lightGray = new Color(198,198,198,200);
	public Color darkGray = new Color(139,139,139,200);
	
	public UI(GamePanel gp, Player player) {
		this.gp = gp;
		this.player = player;
		arial_40 = new Font("Arial",Font.PLAIN, 20);
	}
	
	public void draw(Graphics2D g2, boolean isActive) {
		g2.setFont(arial_40);
		
		BufferedImage heartImg = null;
		try {
			heartImg = ImageIO.read(getClass().getResourceAsStream("/player/heart.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 1; i <= player.health; i++) {
			g2.drawImage(heartImg, 11 * (5 * i)/2 , 10, 25, 25, null);
		}
		
		if(isActive) {
			drawInventory(g2);
		}
		
		drawHotbar(g2);
	}
	
	private void drawInventory(Graphics2D g2) {
		int frameX = gp.tileSize * 7 , frameY = gp.tileSize * 3;
		int frameWidth = gp.tileSize * 9, frameHeight = gp.tileSize * 4;
		drawSubWindow(frameX, frameY, frameWidth *2, frameHeight*2, g2);
		g2.drawString("Hotbar", frameX + 10, frameHeight*2 + gp.tileSize + 5);
	}
	
	private void drawSubWindow(int x, int y, int width, int height, Graphics2D g2) {
		g2.setColor(lightGray);
		g2.setStroke(new BasicStroke(5));
		g2.fillRoundRect(x, y, width, height, 25, 25);
		
		g2.setColor(Color.WHITE);
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
		
		int row = height/gp.tileSize/2; //4 rows
		int col = width/gp.tileSize/2;	//9 columns
	
		for(int i = 0; i<col; i++) {
			for(int j = 0; j<row; j++) {
				int slot =  i % 9 + j * 9;
				drawSlot(10 + x + gp.tileSize * i * 2,
					10 + y + gp.tileSize * 2 * j
					,gp.tileSize*5/4,gp.tileSize*5/4,g2 , slot);
			}
		}
		
		
	}
	
	private void drawSlot(int x, int y, int width, int height, Graphics2D g2, int slot) {
		g2.setColor(darkGray);
		g2.fillRect(x, y, width, height);
		// logic for selected slot
		if(slot - 26 == MinecraftHotbar.selectedSlot)
			g2.setColor(Color.YELLOW);
		else 
			g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(2));
		g2.drawRoundRect(x, y, width, height, 5, 5);
		if(player.inventory.slots[slot] != null) {
			g2.drawImage(player.inventory.slots[slot].image, x+5, y+5, width - 10, height - 10, null);			
			g2.drawString(String.valueOf(player.inventory.slots[slot].quantity), x + width - 25, y + height - 5);
		}
	}
	
	private void drawHotbar(Graphics2D g2) {
		int frameX = gp.tileSize * 7, frameY = gp.tileSize * 16;
		int frameWidth = gp.tileSize * 9, frameHeight = gp.tileSize * 1;
		drawSubWindow(frameX, frameY, frameWidth *2, frameHeight*2, g2);
		
		int row = frameHeight *2/gp.tileSize/2; //4 rows
		int col = frameWidth *2/gp.tileSize/2;
		
		//draw slots
		for(int i = 0; i<col; i++) {
			for(int j = 0; j<row; j++) {
				int slot = i+27;
				drawSlot(10 + frameX + gp.tileSize * i * 2,
					10 + frameY + gp.tileSize * 2 * j
					,gp.tileSize*5/4,gp.tileSize*5/4,g2 , slot);
			}
		}
		
	}
}
