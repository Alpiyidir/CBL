package game.entities.instance;

import game.entities.general.CircularObject;

public class Rocket extends Bullet {
    static final String YELLOW_IMAGE_PATH = ".\\game\\entities\\instance\\sprites\\YellowBullet.png";

    public Rocket(double xPos, double yPos, double speed, double radius, double[] normalizedDirectionVector, CircularObject owner, int spriteNumber) {
        super(xPos, yPos, speed, radius, normalizedDirectionVector, owner, spriteNumber);
    }
}
