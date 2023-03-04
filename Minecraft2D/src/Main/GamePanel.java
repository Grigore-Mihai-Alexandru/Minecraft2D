package Main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	final int originalTileSize = 16;  //16 * 16
	final int scale = 3;
	
	final int tileSize = originalTileSize * scale; //48 * 48
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol; // 768px
	final int screenHeight = tileSize * maxScreenRow; // 565px
	
	Thread gameThread;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}