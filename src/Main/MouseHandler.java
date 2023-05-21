package Main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener{
	
	public int mouseX, mouseY;
	public boolean leftClicked = false;
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int button = e.getButton();
		if(button == MouseEvent.BUTTON1) {
			leftClicked = true;
			mouseX = e.getX();
			mouseY = e.getY();			
		}else if (button == MouseEvent.BUTTON2) {
//            System.out.println("Middle mouse button clicked.");
        } else if (button == MouseEvent.BUTTON3) {
//            System.out.println("Right mouse button clicked.");
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
