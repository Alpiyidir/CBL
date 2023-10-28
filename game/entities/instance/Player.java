package game.entities.instance;

import game.entities.general.CircularObjectWithUpdate;
import game.entities.instance.util.WeaponSelector;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player extends CircularObjectWithUpdate {
    int health;
    int selectedWeapon;
    boolean isAlive;
    long lastVisible;

    static final String IMAGE_PATH = "./game/entities/instance/sprites/Spaceship.png";

    public Player(double posX, double posY, double speed, double radius, int health) {
        super(posX, posY, speed, radius);
        this.health = health;
        this.selectedWeapon = 0;
        this.isAlive = true;
    }

    public int getHealth() {
        return this.health;
    }

    public int getSelectedWeapon() {
        return this.selectedWeapon;
    }

    public boolean getIsAlive() {
        return this.isAlive;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setSelectedWeapon(int selectedWeapon) {
        this.selectedWeapon = selectedWeapon;
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