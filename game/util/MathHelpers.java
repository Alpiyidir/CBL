package game.util;
public class MathHelpers {
    public static double[] normalizeVector2D(double x, double y) {
        double magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        if (magnitude == 0) {
            return new double[]{0, 0};
        }
        return new double[]{x / magnitude, y / magnitude};
    }

    public static double[] normalizeVector(double[] directionVector) {
        double[] normalizedDirectionVector = new double[directionVector.length];

        double squaredSum = 0;
        for (int i = 0; i < directionVector.length; i++) {
            squaredSum += Math.pow(directionVector[i], 2);
        }
        double magnitude = Math.sqrt(squaredSum);
        if (magnitude == 0) {
            return normalizedDirectionVector;
        }

        for (int i = 0; i < directionVector.length; i++) {
            normalizedDirectionVector[i] = directionVector[i] / magnitude;
        }
        return normalizedDirectionVector;
    }

    public static double[] sumVectors(double[] vector1, double[] vector2) {
        if (vector1.length != vector2.length) {
            return null;
        }

        double[] vectorSum = new double[vector1.length];

        for (int i = 0; i < vector1.length; i++) {
            vectorSum[i] = vector1[i] + vector2[i];
        }

        return vectorSum;
    }
}
