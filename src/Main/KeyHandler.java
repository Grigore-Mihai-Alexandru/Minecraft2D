package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Inventory.MinecraftHotbar;
import Inventory.MinecraftInventory;

public class KeyHandler implements KeyListener{

	public boolean upPressed, downPressed, leftPressed, rightPressed;
	public boolean inventoryActive = false;
	
	public KeyHandler(MinecraftInventory inventory) {
		this.inventoryActive = inventory.isActive;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code  == KeyEvent.VK_W) {
			upPressed = true;
		}
		if(code  == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code  == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if(code  == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if(code  == KeyEvent.VK_E) {
			inventoryActive = !inventoryActive;
		}
		
		//hotbar selected
		if(code  == KeyEvent.VK_1) {
			MinecraftHotbar.selectedSlot = 1;
		}
		if(code  == KeyEvent.VK_2) {
			MinecraftHotbar.selectedSlot = 2;
		}
		if(code  == KeyEvent.VK_3) {
			MinecraftHotbar.selectedSlot = 3;
		}
		if(code  == KeyEvent.VK_4) {
			MinecraftHotbar.selectedSlot = 4;
		}
		if(code  == KeyEvent.VK_5) {
			MinecraftHotbar.selectedSlot = 5;
		}
		if(code  == KeyEvent.VK_6) {
			MinecraftHotbar.selectedSlot = 6;
		}
		if(code  == KeyEvent.VK_7) {
			MinecraftHotbar.selectedSlot = 7;
		}
		if(code  == KeyEvent.VK_8) {
			MinecraftHotbar.selectedSlot = 8;
		}
		if(code  == KeyEvent.VK_9) {
			MinecraftHotbar.selectedSlot = 9;
		}
		
		
		if(code == KeyEvent.VK_ESCAPE)
			System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code  == KeyEvent.VK_W) {
			upPressed = false;
		}
		
		if(code  == KeyEvent.VK_S) {
			downPressed = false;
		}
		if(code  == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if(code  == KeyEvent.VK_D) {
			rightPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
	}

	
}
