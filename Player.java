class Player extends CircularObject {
    private double[] normalizedDirectionVector;

    Player(double xPos, double yPos, double speed, double radius) {
        super(xPos, yPos, speed, radius);
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

    public void update(double[] normalizedDirectionVector, double drawInterval) {
        //System.out.println("0: " + normalizedDirectionVector[0] + " 1: " + normalizedDirectionVector[1]);
        this.setNormalizedDirectionVector(normalizedDirectionVector);
        this.addPosX(normalizedDirectionVector[0] * this.getSpeed() * drawInterval);
        this.addPosY(normalizedDirectionVector[1] * this.getSpeed() * drawInterval);
    }
    
}