import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

abstract class CircularObject {
    private double posX;
    private double posY;
    private double speed;
    private double radius;

    private double angle;
    private BufferedImage image;
    private String IMAGE_PATH;

    CircularObject(double posX, double posY, double speed, double radius) {
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;
        this.radius = radius;
        this.IMAGE_PATH = this.getImagePath();
        this.setImage();
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

    public BufferedImage getImage() {
        return this.image;
    }

    public double getAngle() {
        return this.angle;
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

    private void setImage() {
        try {
            if (this.IMAGE_PATH == null) {
                throw new Exception();
            }
            this.image = ImageHelpers.toBufferedImage(
                    ImageIO.read(new File(IMAGE_PATH)).getScaledInstance(
                            (int) this.getRadius() * 2, (int) this.getRadius() * 2, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            this.image = null;
        }
       
    }

    public void setAngle(double angle) {
        this.angle = angle;
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
        double distanceBetweenCircles = Math.sqrt(
                Math.pow(circularObject.posX - this.getPosX(), 2) + Math.pow(circularObject.posY - this.getPosY(), 2));
        return distanceBetweenCircles <= radiiSum;
    }

    public double distanceToPoint(double x, double y) {
        return Math.sqrt(Math.pow(x - this.getPosX(), 2) + Math.pow(y - this.getPosY(), 2));
    }

    public void draw(Graphics g) {
        BufferedImage image = this.getImage();
        if (image != null) {
            g.drawImage(ImageHelpers.rotateImage(this.getImage(), this.getAngle()),
                (int) (this.getPosX() - image.getWidth() / 2),
                (int) (this.getPosY() - image.getHeight() / 2), null);
        } else {
            g.fillOval((int) (this.getPosX() - this.getRadius()), (int) (this.getPosY() - this.getRadius()), (int) this.getRadius() * 2, (int) this.getRadius() * 2);
        }
        
    }

    public abstract String getImagePath();
}
