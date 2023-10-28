package game.entities.instance;

import game.entities.general.CircularObject;
import game.util.MathHelpers;

import java.util.Random;

public class Enemy extends CircularObject {
    long lastBulletTime = - (long) 1e10;
    boolean isShooter = false;

    static final String IMAGE_PATH = "./game/entities/instance/sprites/SpaceshipNegative.png";

    public Enemy(double xPos, double yPos, double speed, double radius) {
        super(xPos, yPos, speed, radius);
        
        Random random = new Random();
        if (random.nextDouble() * 100 > 66.0) {
            isShooter = true;
            System.out.println("Got a shooter");
        }
    }

    public void updatePos(double[] normalizedDirectionVector, double drawIntervalMovementModifier) {
        // System.out.println("0: " + normalizedDirectionVector[0] + " 1: " + normalizedDirectionVector[1]);
        this.addPosX(normalizedDirectionVector[0] * drawIntervalMovementModifier);
        this.addPosY(normalizedDirectionVector[1] * drawIntervalMovementModifier);
    }

    public Bullet shootBullet(Player player){
        if (System.nanoTime() - 9 * 1e8 > lastBulletTime && isShooter){
            lastBulletTime = System.nanoTime();
            System.out.println("Enemy shot bullet");
            return new Bullet(this.getPosX(), this.getPosY(), 2.5, 5.0, MathHelpers.normalizeVector(new double[] {player.getPosX() - this.getPosX(), player.getPosY() - this.getPosY()}), this, 1);
        }
        return null;
    }

    @Override
    public String getImagePath() {
        return IMAGE_PATH;
    }
}