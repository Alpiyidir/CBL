public class Bullet extends CircularObject {

    double xPos; 
    double yPos;
    double radius;
    double speed;

    double[] normalizedDirectionVector;

    public Bullet(double xPos, double yPos, double radius, double speed, double[] directionVector){
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
        this.speed = speed;
        this.normalizedDirectionVector = MathHelpers.normalizeVector(directionVector);
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void updateXPos(double timeStep) {
        this.xPos += this.normalizedDirectionVector[0] * speed * timeStep;
    }
    public void updateYPos(double timeStep) {
        this.yPos += this.normalizedDirectionVector[1] * speed * timeStep;
    }

    public void updatePos(double timeStep) {
        updateXPos(timeStep);
        updateYPos(timeStep);
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
