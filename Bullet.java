public class Bullet extends CircularObjectWithUpdate {

    int type;

    public Bullet(double xPos, double yPos, double speed, double radius, int type, double[] normalizedDirectionVector) {
        super(xPos, yPos, speed, radius, normalizedDirectionVector);
        this.type = type;
    }

    // Adds aim cone to bullet
    @Override
    public void setNormalizedDirectionVector(double[] normalizedDirectionVector) {
        double[] normalVector = new double[]{normalizedDirectionVector[1], -normalizedDirectionVector[0]};
        double degree = Math.PI / 180;
        double offsetMultiplierPerUnitLength = Math.tan(2 * degree);
        double offsetDirection = Math.random() * 2 - 1;
        normalVector[0] *= offsetMultiplierPerUnitLength * offsetDirection;
        normalVector[1] *= offsetMultiplierPerUnitLength * offsetDirection;

        super.setNormalizedDirectionVector(MathHelpers.normalizeVector(MathHelpers.sumVectors(normalizedDirectionVector, normalVector)));
    }
}
