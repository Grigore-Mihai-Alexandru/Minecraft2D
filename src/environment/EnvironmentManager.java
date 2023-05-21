package environment;

import java.awt.Graphics2D;
import Main.GamePanel;

public class EnvironmentManager {
    private GamePanel gp;
    private Lighting lighting;

    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
    }

    public void setup() {
        lighting = new Lighting(gp, 350);
    }

    public void draw(Graphics2D g2) {
        if (lighting != null) {
            lighting.update();
            lighting.draw(g2);
        }
    }
}