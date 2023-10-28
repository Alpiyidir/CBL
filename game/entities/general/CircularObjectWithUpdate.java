package game.entities.general
;
public abstract class CircularObjectWithUpdate extends CircularObject {
    private double[] normalizedDirectionVector;

    protected CircularObjectWithUpdate(double xPos, double yPos, double speed, double radius) {
        super(xPos, yPos, speed, radius);
    }

    protected CircularObjectWithUpdate(double xPos, double yPos, double speed, double radius, double[] normalizedDirectionVector) {
        this(xPos, yPos, speed, radius);
        this.normalizedDirectionVector = normalizedDirectionVector;
    }

    public double[] getNormalizedDirectionVector() {
        return normalizedDirectionVector;
    }

    public void setNormalizedDirectionVector(double[] normalizedDirectionVector) {
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