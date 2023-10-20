class Enemy extends CircularObject {
    Enemy(double xPos, double yPos, double radius, double speed) {
        super(xPos, yPos, radius, speed);
    }

    public void update(double[] normalizedDirectionVector, double drawIntervalMovementModifier) {
        // System.out.println("0: " + normalizedDirectionVector[0] + " 1: " + normalizedDirectionVector[1]);
        this.addPosX(normalizedDirectionVector[0] * drawIntervalMovementModifier);
        this.addPosY(normalizedDirectionVector[1] * drawIntervalMovementModifier);
    }
}