class Planet extends CircularObject {
    double health;

    Planet(double posX, double posY, double speed, double radius, double health) {
        super(posX, posY, speed, radius);
        this.health = health;
    }

    double getHealth() {
        return health;
    }

    void setHealth(double health) {
        this.health = health;
    }
}
