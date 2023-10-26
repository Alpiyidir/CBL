public class Bullet extends CircularObjectWithUpdate {

    int type;
    private boolean killsEnemy;

    public Bullet(double xPos, double yPos, double speed, double radius, int type, boolean killsEnemy, double[] normalizedDirectionVector) {
        super(xPos, yPos, speed, radius, normalizedDirectionVector);
        this.type = type;
        this.killsEnemy = killsEnemy;
    }

    // Adds aim triangle to bullet
    @Override
    public void setNormalizedDirectionVector(double[] normalizedDirectionVector) {
        double[] normalVector = new double[]{normalizedDirectionVector[1], -normalizedDirectionVector[0]};
        double degreeInRadians = Math.PI / 180;
        double inaccuracyInDegrees = 5 * degreeInRadians;
        double offsetMultiplierPerUnitLength = Math.tan(inaccuracyInDegrees);
        double offsetDirection = Math.random() * 2 - 1;
        normalVector[0] *= offsetMultiplierPerUnitLength * offsetDirection;
        normalVector[1] *= offsetMultiplierPerUnitLength * offsetDirection;

        super.setNormalizedDirectionVector(MathHelpers.normalizeVector(MathHelpers.sumVectors(normalizedDirectionVector, normalVector)));
    }

    boolean getKillsEnemy(){
        return killsEnemy;
    }
}
