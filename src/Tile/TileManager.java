package Tile;

import Main.GamePanel;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Inventory.MinecraftHotbar;

public class TileManager {
	public GamePanel gp;
	public static Tile[] tile;
	public static int mapTileNum[][];
	
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
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass_block.png"));
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/oak_log.png"));
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/azalea_leaves.png"));
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/clouds.png"));
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/iron_ore.png"));
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/coal_ore.png"));
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/bedrock.png"));
			tile[8].breakable = false;
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
	    }
  }
	
	public static int breakBlock(int screenX, int screenY, int col, int row) {
		int aux = mapTileNum[row][col];
		if(mapTileNum[row][col] != 0 && tile[mapTileNum[row][col]].breakable)
			mapTileNum[row][col] = 0;
		return aux;
	}
	//place block logic here
	
	public static void placeBlock(int col, int row, int id) {
		if(mapTileNum[row][col] == 0 ) {
			mapTileNum[row][col] = id;
		}
	}
	
}	
