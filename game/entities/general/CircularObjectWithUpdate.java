package game.entities.general;

/**
 * Class CircularObjectWithUpdate
 * Contains all methods and variables that is required for any instance of
 * a circular object that can update within the game.
 */
public abstract class CircularObjectWithUpdate extends CircularObject {
    private double[] normalizedDirectionVector;

    protected CircularObjectWithUpdate(double xPos, double yPos, double speed, double radius) {
        super(xPos, yPos, speed, radius);
    }

    protected CircularObjectWithUpdate(double xPos, double yPos, double speed,
            double radius, double[] normalizedDirectionVector) {
        this(xPos, yPos, speed, radius);
        this.normalizedDirectionVector = normalizedDirectionVector;
    }

    public double[] getNormalizedDirectionVector() {
        return normalizedDirectionVector;
    }

    public void setNormalizedDirectionVector(double[] normalizedDirectionVector) {
        this.normalizedDirectionVector = normalizedDirectionVector;
    }

    /**
     * Computes the directionvector of the current object
     * using normalized direction vector and speed.
     * @return direction vector
     */
    public double[] getDirectionVector() {
        double[] normalizedDirectionVector = this.getNormalizedDirectionVector();
        return new double[] { normalizedDirectionVector[0] * this.getSpeed(),
                normalizedDirectionVector[1] * this.getSpeed() };
    }

    public void updatePosX(double timeStep) {
        this.addPosX(this.normalizedDirectionVector[0] * this.getSpeed() * timeStep);
    }

    public void updatePosY(double timeStep) {
        this.addPosY(this.normalizedDirectionVector[1] * this.getSpeed() * timeStep);
    }

    /**
     * Updates the x and y position of the object given its current direction and speed.
     * @param timeStep timestep
     */
    public void updatePos(double timeStep) {
        this.updatePosX(timeStep);
        this.updatePosY(timeStep);
    }
}