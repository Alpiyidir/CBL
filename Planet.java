class Planet extends CircularObject {
    double health;

    static final String IMAGE_PATH = "./sprites/Planet.png";

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

    @Override
    public String getImagePath() {
        return IMAGE_PATH;
    }
}
