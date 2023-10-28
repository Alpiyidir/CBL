import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Player extends CircularObjectWithUpdate {
    int health;
    boolean isAlive;
    long lastVisible;

    static final String IMAGE_PATH = "./sprites/Spaceship.png";

    Player(double posX, double posY, double speed, double radius, int health) {
        super(posX, posY, speed, radius);
        this.health = health;
        this.isAlive = true;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setAngle(MouseHandler mouseHandler) {
        if ((mouseHandler.posX - this.getPosX()) < 0) {
            this.setAngle(-Math
                    .atan((mouseHandler.posY - this.getPosY()) 
                            / -(double) (mouseHandler.posX - this.getPosX()))
                    - (Math.PI / 2.0));

        } else {
            this.setAngle(Math
                    .atan((mouseHandler.posY - this.getPosY()) 
                            / (double) (mouseHandler.posX - this.getPosX()))
                    + (Math.PI / 2.0));
        }
    }

    public void setLastVisible(long lastVisible) {
        this.lastVisible = lastVisible;
    }

    public long getLastVisible() {
        return lastVisible;
    }

    @Override
    public String getImagePath() {
        return IMAGE_PATH;
    }
}