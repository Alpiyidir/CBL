package game.entities.instance;

import game.entities.general.CircularObject;
import game.util.MathHelpers;

import java.util.Random;

public class Enemy extends CircularObject {
    long lastBulletTime = - (long) 1e10;
    boolean isShooter = false;

    static final String IMAGE_PATH = ".\\game\\entities\\instance\\sprites\\SpaceshipNegative.png";

    public Enemy(double xPos, double yPos, double speed, double radius) {
        super(xPos, yPos, speed, radius);

        this.setImagePath(IMAGE_PATH);
        
        Random random = new Random();
        if (random.nextDouble() * 100 > 66.0) {
            isShooter = true;
            System.out.println("Got a shooter");
        }
    }

    public boolean getIsShooter() {
        return this.isShooter;
    }

    public long getLastBulletTime() {
        return this.lastBulletTime;
    }

    public void setLastBulletTime(long lastBulletTime) {
        this.lastBulletTime = lastBulletTime;
    }

    public void updatePos(double[] normalizedDirectionVector, double drawIntervalMovementModifier) {
        this.addPosX(normalizedDirectionVector[0] * drawIntervalMovementModifier);
        this.addPosY(normalizedDirectionVector[1] * drawIntervalMovementModifier);
    }

    @Override
    public String getImagePath() {
        return IMAGE_PATH;
    }
}