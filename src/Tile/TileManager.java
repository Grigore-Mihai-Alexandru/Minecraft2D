package Tile;

import Main.GamePanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import entity.Player;
public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager (GamePanel  gp) {
		this.gp = gp;
		tile = new Tile[10];	
		mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/map/world.txt");
	}
	
	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].collision = false;
            tile[1] = new Tile();
            tile[1].collision = true;
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_block.png"));
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
	
	}
	public void loadMap(String filepath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filepath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col=0;
			int row=0;
			while(col<gp.maxWorldCol&& row <gp.maxWorldRow) {
			
				String line=br.readLine();
				while(col<gp.maxWorldCol) {
					
					String numbers[]=line.split(" ");
					
					int num = Integer.parseInt(numbers[row]);
					
					mapTileNum[row][col] = num;
					col++;
				}
				
				if(col == gp.maxWorldCol) {
					col=0;
					row++;
				}
			}
		
		br.close();
		}catch(Exception e) {}

	}
	
	public void draw(Graphics g2) {
	    int col = 0;
	    int row = 0;
	    while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
	        int tileNum = mapTileNum[col][row];
	        int worldX = col * gp.tileSize;
	        int worldY = row * gp.tileSize;
	        int screenX = worldX - gp.player.worldX + gp.player.screenX ;
	        int screenY = worldY - gp.player.worldY + gp.player.screenY + 2*gp.tileSize;
	        
	        if (row == 0) {
	            g2.drawImage(tile[0].image, screenX, screenY, gp.tileSize, gp.tileSize, null); // tile[0]
	        } else if (row <= 3) {
	            g2.drawImage(tile[1].image, screenX, screenY, gp.tileSize, gp.tileSize, null); // tile[1]
	        } else {
	            break; // ieșim din bucla while după ce am afișat cele patru rânduri
	        }
	        
	        
	        col++;
	        if (col == gp.maxWorldCol) {
	            col = 0;
	            row++;
	        }
	    }
    }
	    
}	
