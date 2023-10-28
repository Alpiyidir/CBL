package game.entities.general;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import game.util.ImageHelpers;

public abstract class CircularObject {
    private double posX;
    private double posY;
    private double speed;
    private double radius;

    private double angle;
    private BufferedImage image;
    private String imagePath;

    protected CircularObject(double posX, double posY, double speed, double radius) {
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;
        this.radius = radius;
        this.imagePath = this.getImagePath();
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

    public abstract String getImagePath();

    public void setPosX(double xPos, double scale) {
        this.posX = xPos*scale;
    }

    public void setPosY(double yPos, double scale) {
        this.posY = yPos*scale;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private void setImage() {
        try {
            if (this.imagePath == null) {
                throw new Exception();
            }
            this.image = ImageHelpers.toBufferedImage(
                    ImageIO.read(new File(this.getImagePath())).getScaledInstance(
                            (int) this.getRadius() * 2, (int) this.getRadius() * 2, Image.SCALE_DEFAULT));

        } catch (Exception e) {
            this.image = null;
        }

    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setAngle(double posX, double posY) {
        if ((posX - this.getPosX()) < 0) {
            this.setAngle(-Math
                    .atan((posY - this.getPosY())
                            / -(double) (posX - this.getPosX()))
                    - (Math.PI / 2.0));

        } else {
            this.setAngle(Math
                    .atan((posY - this.getPosY())
                            / (double) (posX - this.getPosX()))
                    + (Math.PI / 2.0));
        }
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // Additional helper methods
    public void setPos(double posX, double posY, double xScale, double yScale) {
        this.setPosX(posX, xScale);
        this.setPosY(posY, yScale);
    }

    public void addPosX(double changeX, double scale) {
        this.setPosX(this.getPosX() + changeX, scale);
    }

    public void addPosY(double changeY, double scale) {
        this.setPosY(this.getPosY() + changeY, scale);
    }

    public void addPos(double changeX, double changeY, double xScale, double yScale) {
        this.addPosX(changeX, xScale);
        this.addPosY(changeY, yScale);
    }

    public void addPos(double[] changeVector, double xScale, double yScale) {
        this.addPosX(changeVector[0], xScale);
        this.addPosY(changeVector[1], yScale);
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
            g.fillOval((int) (this.getPosX() - this.getRadius()), (int) (this.getPosY() - this.getRadius()),
                    (int) this.getRadius() * 2, (int) this.getRadius() * 2);
        }

    }

}
