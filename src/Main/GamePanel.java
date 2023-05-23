package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import Inventory.MinecraftInventory;
import Sound.Sound;
import environment.EnvironmentManager;
import Tile.TileManager;
import entity.Player;
import entity.Zombie;

public class GamePanel extends JPanel implements Runnable{
    
    private static final long serialVersionUID = 1L;
    final int originalTileSize = 16;  //32 * 32
    final int scale = 2;
    
    public final int tileSize = originalTileSize * scale; //64 * 64
    public final int maxScreenCol = 32;
    
    public final int maxScreenRow = 18;
    public final int screenWidth = tileSize * maxScreenCol; // 768px
    public final int screenHeight = tileSize * maxScreenRow; // 565px


    //word settings
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100 ;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    int FPS = 60;
    TileManager tileM = new TileManager(this);
    public MinecraftInventory inventory = new MinecraftInventory(36);
    KeyHandler keyH = new KeyHandler(inventory);
    MouseHandler mouseH = new MouseHandler();
    Thread gameThread;
    public CollisionChecker cCollision = new CollisionChecker(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    public Zombie zombie = new Zombie(this);
    public Player player = new Player(this, keyH, mouseH, zombie, inventory);
    
    public UI ui = new UI(this, player);
    public Sound sound = new Sound();
//	public MapGenerator generator = new MapGenerator(this);
    
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.decode("#81A0FF"));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.addMouseListener(mouseH);
    }
    
    public void setup() {
        eManager = new EnvironmentManager(this); // Create an instance of EnvironmentManager
        eManager.setup(); // Call the setup method of EnvironmentManager
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        playMusic(1);
//		generator.generateWorld();
//		generator.saveWorldToFile("./resources/map/map.txt");
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
        zombie.update(player);
    }
    
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        player.draw(g2);
        zombie.draw(g2);
        eManager.draw(g2); // Call the draw method of EnvironmentManager
        ui.draw(g2, keyH.inventoryActive);
        g2.dispose();
    }
    
    public void playMusic(int i) {
    	sound.setFile(i);
    	sound.play();
    	sound.loop();
    }
    public void stopMusic() {
    	sound.stop();
    }
    public void playSE(int i) {
    	sound.setFile(i);
    	sound.play();
    }
    
}