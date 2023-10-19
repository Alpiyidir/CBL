import java.util.ArrayList;

public class Bullet {

    double xPos; 
    double yPos;

    double[] direction;

    static double speed = 5;


    public Bullet(double x, double y, double[] direction){
        xPos = (int) x;
        yPos = (int) y;
        this.direction = direction;
        double vectorLength = Math.pow(Math.pow(this.direction[0],2) + Math.pow(this.direction[1], 2), 1.0/2.0);
        System.out.println("vectorLength: " + vectorLength);
        this.direction[0] = direction[0] / vectorLength;
        this.direction[1] = direction[1] / vectorLength;
        System.out.println("X: " + this.direction[0] + " Y: " + this.direction[1]);
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void addXPos(double changeX) {
        this.xPos += this.direction[0] * speed;
    }
    public void addYPos(double changeY) {
        this.yPos += this.direction[1] * speed;
    }

    public void setXPos(double XPos) {
        this.xPos = xPos;
    }
    public void setYPos(double YPos) {
        this.yPos = yPos;
    }

    public void setPos(double x, double y) {
        this.xPos = x;
        this.yPos = y;
    }
}
