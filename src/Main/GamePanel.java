package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Tile.TileManager;
import entity.Player;

public class GamePanel extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 1L;
	final int originalTileSize = 16;  //32 * 32
	final int scale = 2;
	
	public final int tileSize = originalTileSize * scale; //64 * 64
	public final int maxScreenCol = 32;
	
	public final int maxScreenRow = 18;
	public final int screenWidth = tileSize * maxScreenCol; // 768px
    public final int screenHeight = tileSize * maxScreenRow; // 565px
	//world settings
	public final int maxWorldCol = 20;
	public final int maxWorldRow = 20 ;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	int FPS = 60;
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public CollisionChecker cCollision = new CollisionChecker(this);
	public Player player = new Player(this, keyH);
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.decode("#81A0FF"));
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;  //0,01666 sec
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null) {
		
			update();
			
			repaint();
		
			try {
				double remainingTime = nextDrawTime - System.nanoTime();				
				remainingTime = remainingTime / 1000000;
				
				if(remainingTime < 0)
					remainingTime = 0;
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval; 
			} catch(InterruptedException e){
				e.printStackTrace();
			}
			
		}
	}
	
	public void update() {
		
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics g2 = (Graphics2D)g;
		tileM.draw(g2);
		player.draw(g2);
		
		g2.dispose();
	}
	
}