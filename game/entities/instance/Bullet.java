package game.entities.instance;

import game.entities.general.CircularObject;
import game.entities.general.CircularObjectWithUpdate;
import game.util.MathHelpers;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends CircularObjectWithUpdate {
    CircularObject owner;
    int type;
    
    static final String BLUE_IMAGE_PATH = "./game/entities/instance/sprites/BlueBullet.png";
    static final String YELLOW_IMAGE_PATH = "./game/entities/instance/sprites/YellowBullet.png";
    static final String RED_IMAGE_PATH = "./game/entities/instance/sprites/RedBullet.png";

    public Bullet(double xPos, double yPos, double speed, double radius, int type, CircularObject owner,
            double[] normalizedDirectionVector) {
        super(xPos, yPos, speed, radius, normalizedDirectionVector);
        this.type = type;
        this.owner = owner;
    }

    public int getType() {
        return this.type;
    }

    public CircularObject getOwner() {
        return this.owner;
    }

    // Adds aim triangle to bullet
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
        if (this.type == 0) {
            Color lightBlue = new Color(50, 133, 168);
            g.setColor(lightBlue);
        } else {
            g.setColor(Color.orange);
        }
        super.draw(g);
    }

    @Override
    public String getImagePath() {
        int type = this.getType();
        System.out.println(type);
        switch (type) {
            case 0:
                return BLUE_IMAGE_PATH;
            case 1:
                return YELLOW_IMAGE_PATH;
            default:
                return null;
        }
    }
}
