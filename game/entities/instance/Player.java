package game.entities.instance;
import javax.imageio.ImageIO;

import game.entities.general.CircularObjectWithUpdate;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends CircularObjectWithUpdate {
    int health;
    boolean isAlive;
    long lastVisible;

    static final String IMAGE_PATH = "./game/entities/instance/sprites/Spaceship.png";

    public Player(double posX, double posY, double speed, double radius, int health) {
        super(posX, posY, speed, radius);
        this.health = health;
        this.isAlive = true;
    }

    public int getHealth() {
        return health;
    }

    public boolean getIsAlive() {
        return this.isAlive;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
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