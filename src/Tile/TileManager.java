package Tile;

import Main.GamePanel;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class TileManager {
	public GamePanel gp;
	public static Tile[] tile;
	public static int mapTileNum[][];
	int worldMaxCol;
	int worldMaxRow;
	
	public TileManager (GamePanel gp) {
		this.gp = gp;
		tile = new Tile[10];	
		mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/map/world.txt");
	}
	
	public void getTileImage() {
		try {
			tile[1] = new Tile();
			tile[1].collision = true;
            tile[2] = new Tile();
            tile[2].collision = true;
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_block.png"));
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));
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
			while(col<gp.maxWorldCol && row <gp.maxWorldRow) {
			
				String line = br.readLine();
				while(col<gp.maxWorldCol) {
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
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
	        int tileNum = mapTileNum[row][col];
	        
	        int worldX = col * gp.tileSize;
	        int worldY = row * gp.tileSize;
	        int screenX = worldX - gp.player.worldX + gp.player.screenX ;
	        int screenY = worldY - gp.player.worldY + gp.player.screenY + 2*gp.tileSize;
	        
	        if(tileNum != 0)
	        	g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	        
	        col++;
	        if (col == gp.maxWorldCol) {
	            col = 0;
	            row++;
	        }
	        worldMaxCol = gp.maxScreenCol;
	        worldMaxRow = gp.maxScreenRow;
	    }
    }
	
	public static void breakBlock(int screenX, int screenY, int col, int row) {
		//handle outside of border case
		if(mapTileNum[row][col] != 0 && tile[mapTileNum[row][col]].breakable)
			mapTileNum[row][col] = 0;
	}
	
	
}	
