package game.entities.instance;

import game.entities.general.CircularObject;

/**
 * Class Planet
 * Contains all methods and variables that is required for a planet instance.
 */
public class Planet extends CircularObject {
    double health;

    static final String IMAGE_PATH_WIN = ".\\CBL\\game\\entities\\instance\\sprites\\Planet.png";
    static final String IMAGE_PATH_MAC = "game/entities/instance/sprites/Planet.png";

    public Planet(double posX, double posY, double speed, double radius, double health) {
        super(posX, posY, speed, radius);
        this.health = health;
        this.setImagePathWin(IMAGE_PATH_WIN);
        this.setImagePathMac(IMAGE_PATH_MAC);
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }
}
