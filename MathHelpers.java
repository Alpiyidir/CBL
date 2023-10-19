public class MathHelpers {
    public static double[] normalize2DVector(double x, double y) {
        double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        return new double[]{x / magnitude, y / magnitude};
    }

    public static double[] normalizeVector(double[] directionVector) {
        double squaredSum = 0;
        for (int i = 0; i < directionVector.length; i++) {
            squaredSum += Math.pow(directionVector[i], 2);
        }
        double magnitude = Math.sqrt(squaredSum);
        if (magnitude == 0) {
            return directionVector;
        }

        for (int i = 0; i < directionVector.length; i++) {
            directionVector[i] /= magnitude;
        }
        return directionVector;
    }
}
