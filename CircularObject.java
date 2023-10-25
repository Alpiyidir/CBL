abstract class CircularObject {
    private double posX;
    private double posY;
    private double speed;
    private double radius;

    CircularObject(double posX, double posY, double speed, double radius) {
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;
        this.radius = radius;
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
        this.setPosX(this.getPosX() + changeX);
    }
    
    public void addPosY(double changeY) {
        this.setPosY(this.getPosY() + changeY);
    }

    public void addPos(double changeX, double changeY) {
        this.addPosX(changeX);
        this.addPosY(changeY);
    }

    public void addPos(double[] changeVector) {
        this.addPosX(changeVector[0]);
        this.addPosY(changeVector[1]);
    }

    public boolean intersects(CircularObject circularObject) {
        double radiiSum = circularObject.radius + this.radius;
        double distanceBetweenCircles = Math.sqrt(Math.pow(circularObject.posX - this.posX, 2) + Math.pow(circularObject.posY - this.posY , 2));
        return distanceBetweenCircles <= radiiSum;
    }

    public double distanceToPoint(double x, double y) {
        return Math.sqrt(Math.pow(x - this.posX, 2) + Math.pow(y - this.posY, 2));
    }
}
