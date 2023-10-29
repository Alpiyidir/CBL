package game.entities.general;

import game.entities.general.util.GameScale;
import game.util.ImageHelpers;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Class CircularObject
 * Contains all methods and variables that is required for any instance of
 * a circular object within the game.
 */
public abstract class CircularObject {
    private double posX;
    private double posY;
    private double speed;
    private double radius;

    private double angle;
    private BufferedImage image;
    private String imagePathWin;
    private String imagePathMac;

    protected CircularObject(double posX, double posY, double speed, double radius) {
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

    public BufferedImage getImage() {
        return this.image;
    }

    public double getAngle() {
        return this.angle;
    }

    public static double getScaleX() {
        return GameScale.getScaleX();
    }

    public static double getScaleY() {
        return GameScale.getScaleY();
    }

    public String getImagePathWin() {
        return this.imagePathWin;
    }
    public String getImagePathMac() {
        return this.imagePathMac;
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

    /**
     * Setter for image, uses the filepath to create a bufferedimage with instance state.
     */
    public void setImage() {
        try {
            if (this.imagePathWin == null) {
                throw new Exception();
            }
            this.image = ImageHelpers.toBufferedImage(
                    ImageIO.read(new File(this.getImagePathWin())).getScaledInstance(
                        (int) this.getRadius() * 2, 
                        (int) this.getRadius() * 2, 
                        Image.SCALE_DEFAULT));

        } catch (Exception e) {
            try {
                if (this.imagePathMac == null) {
                    throw new Exception();
                }
                this.image = ImageHelpers.toBufferedImage(
                        ImageIO.read(new File(this.getImagePathMac())).getScaledInstance(
                            (int) this.getRadius() * 2, 
                            (int) this.getRadius() * 2, 
                            Image.SCALE_DEFAULT));

            } catch (Exception ec) {
                this.image = null;
            }
        }

    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    /**
     * Rotates the current sprite towards position x, y relative to current position.
     * @param posX x position
     * @param posY y position
     */
    public void rotateTowards(double posX, double posY) {
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

    /**
     * Sets the imagePath to the location where the sprite is located.
     * @param imagePath image path
     */
    public void setImagePathWin(String imagePath) {
        this.imagePathWin = imagePath;
        this.setImage();
    }

    public void setImagePathMac(String imagePath) {
        this.imagePathMac = imagePath;
        this.setImage();
    }
    public static void setScaleX(double scaleX) {
        GameScale.setScaleX(scaleX);
    }

    public static void setScaleY(double scaleY) {
        GameScale.setScaleY(scaleY);
    }

    // Additional helper methods

    /**
     * Updates the x and y position of the circular object.
     * 
     * @param posX x position
     * @param posY y position
     */
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

    /**
     * Adds the changeX and changeY to an object's position.
     * @param changeX change in x direction
     * @param changeY change in y direction
     */
    public void addPos(double changeX, double changeY) {
        this.addPosX(changeX);
        this.addPosY(changeY);
    }

    /**
     * Adds the components of a changeVector to object's position.
     * @param changeVector 2D Vector with x and y component
     */
    public void addPos(double[] changeVector) {
        this.addPosX(changeVector[0]);
        this.addPosY(changeVector[1]);
    }

    /**
     * Computes whether this circular object instance intersects the param circular object.
     * @param circularObject the circular object to be checked against
     * @return True if they intersect, false otherwise.
     */
    public boolean intersects(CircularObject circularObject) {
        double radiiSum = circularObject.radius + this.radius;
        double distanceBetweenCircles = Math.sqrt(
                Math.pow(circularObject.posX - this.getPosX(), 2) 
                + Math.pow(circularObject.posY - this.getPosY(), 2));
        return distanceBetweenCircles <= radiiSum;
    }

    public double distanceToPoint(double x, double y) {
        return Math.sqrt(Math.pow(x - this.getPosX(), 2) + Math.pow(y - this.getPosY(), 2));
    }


    /**
     * Draws the current object given its state. 
     * Draws a sprite if path exists otherwise draws circle.
     * 
     * @param g Graphics
     */
    public void draw(Graphics g) {
        BufferedImage image = this.getImage();
        if (image != null) {
            g.drawImage(
                ImageHelpers.rotateImage(
                    ImageHelpers.toBufferedImage(this.getImage().getScaledInstance(
                        (int) (image.getWidth() * getScaleX()), 
                        (int) (image.getHeight() * getScaleY()), Image.SCALE_DEFAULT)), 
                        this.getAngle()),
                        (int) (((this.getPosX() - image.getWidth() / 2)) * getScaleX()),
                        (int) ((this.getPosY() - image.getHeight() / 2) * getScaleY()), null);
        } else {
            g.fillOval((int) ((this.getPosX() - this.getRadius()) * getScaleX()),
                    (int) ((this.getPosY() - this.getRadius()) * getScaleY()),
                    (int) (this.getRadius() * 2 * getScaleX()), 
                    (int) (this.getRadius() * 2 * getScaleY()));
        }

    }

}
