package game.entities.general;

import java.awt.Color;
import java.awt.Graphics;

import game.util.MathHelpers;

public abstract class Projectile extends CircularObjectWithUpdate {
    private CircularObject owner;
    private int spriteNumber;

    final String BLUE_IMAGE_PATH = ".\\game\\entities\\instance\\sprites\\BlueBullet.png";
    final String YELLOW_IMAGE_PATH = ".\\game\\entities\\instance\\sprites\\YellowBullet.png";
    final String RED_IMAGE_PATH = ".\\game\\entities\\instance\\sprites\\RedBullet.png";

    public Projectile(double xPos, double yPos, double speed, double radius, double[] normalizedDirectionVector, CircularObject owner, int spriteNumber) {
        super(xPos, yPos, speed, radius, normalizedDirectionVector);
        this.owner = owner;
        this.spriteNumber = spriteNumber;
    }

    public CircularObject getOwner() {
        return this.owner;
    }

    public int getSpriteNumber() {
        return this.spriteNumber;
    }

    @Override
    public void setNormalizedDirectionVector(double[] normalizedDirectionVector) {
        double[] normalVector = new double[] { normalizedDirectionVector[1], -normalizedDirectionVector[0] };
        double degreeInRadians = Math.PI / 180;
        double inaccuracyInDegrees = 5 * degreeInRadians;
        double offsetMultiplierPerUnitLength = Math.tan(inaccuracyInDegrees);
        double offsetDirection = Math.random() * 2 - 1;
        normalVector[0] *= offsetMultiplierPerUnitLength * offsetDirection;
        normalVector[1] *= offsetMultiplierPerUnitLength * offsetDirection;

        super.setNormalizedDirectionVector(
                MathHelpers.normalizeVector(MathHelpers.sumVectors(normalizedDirectionVector, normalVector)));
    }

    @Override
    public void draw(Graphics g) {
        Color lightBlue = new Color(50, 133, 168);
        g.setColor(lightBlue);
        super.draw(g);
    }

    @Override
    public String getImagePath() {
        switch (this.getSpriteNumber()) {
            case 0:
                return BLUE_IMAGE_PATH;
            case 1:
                return RED_IMAGE_PATH;
            case 2:
                return YELLOW_IMAGE_PATH;
            default:
                return null;
        }
    }
}
