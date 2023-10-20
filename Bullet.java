public class Bullet extends CircularObject {
    double[] normalizedDirectionVector;

    public Bullet(double xPos, double yPos, double speed, double radius, double[] directionVector) {
        super(xPos, yPos, speed, radius);
        this.normalizedDirectionVector = MathHelpers.normalizeVector(directionVector);
    }

    double[] getNormalizedDirectionVector() {
        return normalizedDirectionVector;
    }

    void setNormalizedDirectionVector(double[] normalizedDirectionVector) {
        if (normalizedDirectionVector.length != 2) {
            return;
        }
        this.normalizedDirectionVector = normalizedDirectionVector;
    }

    public double[] getDirectionVector() {
        double[] normalizedDirectionVector = this.getNormalizedDirectionVector();
        return new double[]{normalizedDirectionVector[0] * this.getSpeed(), normalizedDirectionVector[1] * this.getSpeed()};
    }

    public void updatePosX(double timeStep) {
        this.addPosX(this.normalizedDirectionVector[0] * this.getSpeed() * timeStep);
    }

    public void updatePosY(double timeStep) {
        this.addPosY(this.normalizedDirectionVector[1] * this.getSpeed() * timeStep);
    }

    public void updatePos(double timeStep) {
        this.updatePosX(timeStep);
        this.updatePosY(timeStep);
    }
}
