class Player extends CircularObjectWithUpdate {
    int health;

    Player(double xPos, double yPos, double speed, double radius, int health) {
        super(xPos, yPos, speed, radius);
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}