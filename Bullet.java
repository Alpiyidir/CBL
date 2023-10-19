import java.util.ArrayList;

public class Bullet {

    double xPos; 
    double yPos;

    int[] direction;

    static int speed = 10;


    public Bullet(double x, double y, int[] direction){
        xPos = (int) x;
        yPos = (int) y;
        this.direction = direction;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void addXPos(double changeX) {
        this.xPos += changeX * speed;
    }
    public void addYPos(double changeY) {
        this.yPos += changeY * speed;
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
