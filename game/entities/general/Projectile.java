package game.entities.general;

import game.util.MathHelpers;
import java.awt.Color;
import java.awt.Graphics;

/**
 * Class Projectile
 * Contains all methods and variables that is required for any instance of
 * a projectile within the game.
 */
public abstract class Projectile extends CircularObjectWithUpdate {
    private CircularObject owner;
    private int spriteNumber;

    static final String BLUE_IMAGE_PATH = 
        "game/entities/instance/sprites/BlueBullet.png";
    static final String YELLOW_IMAGE_PATH = 
        "game/entities/instance/sprites/YellowBullet.png";
    static final String RED_IMAGE_PATH = "game/entities/instance/sprites/RedBullet.png";

    public Projectile(double xPos, double yPos, double speed, double radius, 
            double[] normalizedDirectionVector, CircularObject owner, int spriteNumber) {
        super(xPos, yPos, speed, radius, normalizedDirectionVector);
        this.owner = owner;
        this.spriteNumber = spriteNumber;

        if (this.getSpriteNumber() == 0) {
            this.setImagePath(BLUE_IMAGE_PATH);

        } else if (this.getSpriteNumber() == 1) {
            this.setImagePath(YELLOW_IMAGE_PATH);
        } else if (this.getSpriteNumber() == 2) {
            this.setImagePath(RED_IMAGE_PATH);
        }
    }

    public CircularObject getOwner() {
        return this.owner;
    }

    public int getSpriteNumber() {
        return this.spriteNumber;
    }

    @Override
    public void setNormalizedDirectionVector(double[] normalizedDirectionVector) {
        double[] normalVector = new double[] { normalizedDirectionVector[1], 
            -normalizedDirectionVector[0] };
        double degreeInRadians = Math.PI / 180;
        double inaccuracyInDegrees = 5 * degreeInRadians;
        double offsetMultiplierPerUnitLength = Math.tan(inaccuracyInDegrees);
        double offsetDirection = Math.random() * 2 - 1;
        normalVector[0] *= offsetMultiplierPerUnitLength * offsetDirection;
        normalVector[1] *= offsetMultiplierPerUnitLength * offsetDirection;

        super.setNormalizedDirectionVector(
                MathHelpers.normalizeVector(
                    MathHelpers.sumVectors(normalizedDirectionVector, normalVector)));
    }

    @Override
    public void draw(Graphics g) {
        Color lightBlue = new Color(50, 133, 168);
        g.setColor(lightBlue);
        super.draw(g);
    }
}
