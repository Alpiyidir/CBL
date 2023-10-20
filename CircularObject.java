import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

abstract class CircularObject {
    private double posX;
    private double posY;
    private double speed;
    private double radius;
    Rectangle2D.Double collisionShape = new Rectangle2D.Double();


    CircularObject(double posX, double posY, double speed, double radius) {
        this.posX = posX;
        this.posY = posY;
        this.collisionShape.width = radius;
        this.collisionShape.height = radius;
        this.collisionShape.x = posX;
        this.collisionShape.y = posY;

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
        this.collisionShape.x = xPos;

    }

    public void setPosY(double yPos) {
        this.posY = yPos;
        this.collisionShape.y = yPos;

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
}
