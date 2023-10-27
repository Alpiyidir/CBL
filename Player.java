class Player extends CircularObjectWithUpdate {
    int health;
    boolean isAlive;
    long lastVisible;

    Player(double xPos, double yPos, double speed, double radius, int health) {
        super(xPos, yPos, speed, radius);
        this.health = health;
        this.isAlive = true;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    public void setLastVisible(long lastVisible) {
        this.lastVisible = lastVisible;
    }
    public long getLastVisible() {
        return lastVisible;
    }
}