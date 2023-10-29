package game;

public class GameScale {
    private static double scaleX = 1;
    private static double scaleY = 1;

    public static double getScaleX() {
        return scaleX;
    }

    public static double getScaleY() {
        return scaleY;
    }

    public static void setScaleX(double scaleX) {
        GameScale.scaleX = scaleX;
    }

    public static void setScaleY(double scaleY) {
        GameScale.scaleY = scaleY;
    }
}