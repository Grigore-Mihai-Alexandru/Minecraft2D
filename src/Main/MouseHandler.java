package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{
	
	public int mouseX, mouseY;
	public boolean leftClicked = false;
	public boolean rightClicked = false;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int button = e.getButton();
		if(button == MouseEvent.BUTTON1) {
			leftClicked = true;
			mouseX = e.getX();
			mouseY = e.getY();
		}
		if (button == MouseEvent.BUTTON3) {
            rightClicked = true;
			mouseX = e.getX();
			mouseY = e.getY();
        }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
