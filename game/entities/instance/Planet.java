package game.entities.instance;

import game.entities.general.CircularObject;

public class Planet extends CircularObject {
    double health;

    static final String IMAGE_PATH = "./game/entities/instance/sprites/Planet.png";

    public Planet(double posX, double posY, double speed, double radius, double health) {
        super(posX, posY, speed, radius);
        this.health = health;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    @Override
    public String getImagePath() {
        return IMAGE_PATH;
    }
}
