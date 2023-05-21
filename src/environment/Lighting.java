package environment;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.RadialGradientPaint;
import Main.GamePanel;

public class Lighting {
    public GamePanel gp;
    public BufferedImage darknessFilter;
int daycounter;
float filterAlpha =0f;
final int day = 0;
final int dusk = 1;
final int night= 2;
final int dawn = 3;
int dayState = day;
    public Lighting(GamePanel gp, int circleSize) {
        this.gp = gp;
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = darknessFilter.createGraphics();

        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));
        int centerX = gp.maxScreenCol;
        int centerY = gp.maxScreenRow;

        double x = centerX - (circleSize / 2);
        double y = centerY - (circleSize / 2);

        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);

        Area lightArea = new Area(circleShape);
        screenArea.subtract(lightArea);
Color color[]= new Color[5];
float fraction[]= new float[5];

        color[0]= new Color(0,0,0,0f);
        color[1]= new Color(0,0,0,0.25f);
        color[2]= new Color(0,0,0,0.5f);
        color[3]= new Color(0,0,0,0.75f);
        color[4]= new Color(0,0,0,0.98f);
        
        
        fraction[0]= 0f;
        
        fraction[0]= 0.25f;
        
        fraction[0]= 0.5f;
        
        fraction[0]= 0.75f;  
        fraction[0]= 1f;
        
        	RadialGradientPaint qPaint = new RadialGradientPaint (centerX, centerY,(circleSize/2),fraction, color);
        	
        g2.setPaint(qPaint);
        
        
        g2.fill(screenArea);
        g2.dispose();
    
   }
    
    public void update() {
    	
    	if (dayState == day){
    		dayState++;
    		if(daycounter > 600) {
    			
    			dayState = dusk;
    			daycounter = 0;
    			
    			
    			
    		}
    		
    		
    	}
    	
    	if(dayState == dusk) {
    		
    		filterAlpha += 0.001f;
    		if(filterAlpha >1f)
    			filterAlpha = 1f;
    		dayState = night;
    		
    		
    	}
    if(dayState == night) {
    	daycounter++;
    	if(daycounter > 600)
    		dayState = dawn;
    	
    	
    	
    	
    }
    if(dayState == dawn) {
    	
    	filterAlpha-= 0.001f;
    	if(filterAlpha <0) {
    		
    		filterAlpha = 0;
    		dayState = day;
    	}
    }
    
    }

    public void draw(Graphics2D g2) {
       g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
    	g2.drawImage(darknessFilter, 0, 0, null);
    	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    
    
    String situation = "";
    switch(dayState) {
    
    
    case day: situation = "Day";break;
    
    
    case dusk: situation = "Dusk";break;
    
    case night : situation = "Night";break;
    
    case dawn : situation = "Dawn";break;
    }
    
    }
    
}