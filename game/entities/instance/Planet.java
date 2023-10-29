package game.entities.instance;

import game.entities.general.CircularObject;

/**
 * Class Planet
 * Contains all methods and variables that is required for a planet instance.
 */
public class Planet extends CircularObject {
    double health;

    static final String IMAGE_PATH = ".\\game\\entities\\instance\\sprites\\Planet.png";

    public Planet(double posX, double posY, double speed, double radius, double health) {
        super(posX, posY, speed, radius);
        this.health = health;
        this.setImagePath(IMAGE_PATH);
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
