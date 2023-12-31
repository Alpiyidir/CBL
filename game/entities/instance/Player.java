package game.entities.instance;

import game.entities.general.CircularObjectWithUpdate;

/**
 * Class Player
 * Contains all methods and variables that is required for a player instance.
 */
public class Player extends CircularObjectWithUpdate {
    double health;
    int selectedWeapon;
    boolean isAlive;
    long lastVisible;
    long lastBulletTime;

    static final String IMAGE_PATH = "game/entities/instance/sprites/Spaceship.png";

    public Player(double posX, double posY, double speed, double radius, double health) {
        super(posX, posY, speed, radius);
        this.health = health;
        this.selectedWeapon = 0;
        this.isAlive = true;
        this.lastBulletTime = - (long) 1e10;
        this.setImagePath(IMAGE_PATH);
    }

    public double getHealth() {
        return this.health;
    }

    public int getSelectedWeapon() {
        return this.selectedWeapon;
    }

    public boolean getIsAlive() {
        return this.isAlive;
    }
    
    public long getLastVisible() {
        return lastVisible;
    }

    public long getLastBulletTime() {
        return this.lastBulletTime;
    }


    public void setHealth(double health) {
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

    public void setLastBulletTime(long lastBulletTime) {
        this.lastBulletTime = lastBulletTime;
    }
}