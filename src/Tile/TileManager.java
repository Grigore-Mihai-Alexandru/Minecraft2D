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
	
	this.gp=gp;
	tile=new Tile[10];	
	
	
	
	getTileImage();
}
		
	


public void getTileImage() {
		
		try {
			tile[0] = new Tile();
            tile[1] = new Tile();		
			tile[2] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_block.png"));
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));
		
		
		
		}catch(IOException e) {
			e.printStackTrace();
		}
	


}
public void draw(Graphics g2) {
	

g2.drawImage(tile[0].image,602,514,gp.tileSize, gp.tileSize,null);//dirt
g2.drawImage(tile[0].image,634,514,gp.tileSize, gp.tileSize,null);//32*32
g2.drawImage(tile[0].image,666,514,gp.tileSize, gp.tileSize,null);
g2.drawImage(tile[0].image,698,514,gp.tileSize, gp.tileSize,null);
g2.drawImage(tile[0].image,768,514,gp.tileSize, gp.tileSize,null);

g2.drawImage(tile[1].image,698,545,gp.tileSize, gp.tileSize,null);
g2.drawImage(tile[1].image,602,545,gp.tileSize, gp.tileSize,null);//stone
g2.drawImage(tile[1].image,570,545,gp.tileSize, gp.tileSize,null);
g2.drawImage(tile[1].image,538,545,gp.tileSize, gp.tileSize,null);
g2.drawImage(tile[1].image,768,545,gp.tileSize, gp.tileSize,null);
g2.drawImage(tile[1].image,698,545,gp.tileSize, gp.tileSize,null);
}
}