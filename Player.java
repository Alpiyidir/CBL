class Player extends CircularObject {
    Player(double xPos, double yPos, double radius, double speed) {
        super(xPos, yPos, radius, speed);
    }

    public void update(double[] normalizedDirectionVector, double drawInterval) {
        //System.out.println("0: " + normalizedDirectionVector[0] + " 1: " + normalizedDirectionVector[1]);
        this.addPosX(normalizedDirectionVector[0] * drawInterval);
        this.addPosY(normalizedDirectionVector[1] * drawInterval);
    }
    
}