package Tile;

import Main.GamePanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TileManager {
	GamePanel gp;
	Tile[] tile;

	public TileManager (GamePanel  gp) {
		
		this.gp = gp;
		tile = new Tile[10];	
		
		getTileImage();
	}
	
	public void getTileImage() {
			
			try {
				tile[0] = new Tile();
	            tile[1] = new Tile();
	            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_block.png"));
				tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));
			}catch(IOException e) {
				e.printStackTrace();
			}
		
	}
	public void draw(Graphics g2) {
		
		for(int i=0; i<gp.maxScreenCol; i++) {
			g2.drawImage(tile[0].image,i*gp.tileSize,gp.tileSize*(gp.maxScreenRow - 3),gp.tileSize, gp.tileSize,null);//dirt
		}
		for(int i=0; i<gp.maxScreenCol; i++) {
			g2.drawImage(tile[1].image,i*gp.tileSize,gp.tileSize*(gp.maxScreenRow - 2),gp.tileSize, gp.tileSize,null); //stone
			g2.drawImage(tile[1].image,i*gp.tileSize,gp.tileSize*(gp.maxScreenRow - 1),gp.tileSize, gp.tileSize,null); //stone
		}
	}
}