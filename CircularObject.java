abstract class CircularObject {
    private double posX;
    private double posY;
    private double radius;
    private double speed;
    private double[] normalizedDirectionVector;

    CircularObject(double posX, double posY, double radius, double speed) {
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        this.speed = speed;
    }

    CircularObject(double posX, double posY, double radius, double speed, double[] directionVector) {
        this(posX, posY, radius, speed);
        this.normalizedDirectionVector = MathHelpers.normalizeVector(directionVector);
    }

    // Default getters/setters

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRadius() {
        return radius;
    }

    public void setPosX(double xPos) {
        this.posX = xPos;
    }

    public void setPosY(double yPos) {
        this.posY = yPos;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    // Additional helper methods
    public void setPos(double posX, double posY) {
        this.setPosX(posX);
        this.setPosY(posY);
    }

    public void addPosX(double changeX) {
        this.setPosX(this.getPosX() + changeX * this.getSpeed());
    }
    
    public void addPosY(double changeY) {
        this.setPosY(this.getPosY() + changeY * this.getSpeed());
    }
}
