package Main;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Player;

public class UI {
	GamePanel gp;
	Player player;
	Font arial_40;
	
	public UI(GamePanel gp, Player player) {
		this.gp = gp;
		this.player = player;
		
		arial_40 = new Font("Arial",Font.PLAIN, 20);
	}
	
	public void draw(Graphics2D g2) {
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
	}
	
}
