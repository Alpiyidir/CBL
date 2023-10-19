class Enemy {
    private double xPos;
    private double yPos;
    private double radius;
    private double speed;
    

    Enemy(double xPos, double yPos, double radius, double speed) {
        this.radius = radius;
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
    }

    public double getRadius() {
        return radius;
    }

    public double getXPos() {
        return xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public double speed() {
        return speed;
    }

    public void addXPos(double changeX) {
        this.xPos += changeX * speed;
    }
    public void addYPos(double changeY) {
        this.yPos += changeY * speed;
    }

    public void setXPos(double XPos) {
        this.xPos = xPos;
    }
    public void setYPos(double YPos) {
        this.yPos = yPos;
    }

    public void setPos(double x, double y) {
        this.xPos = x;
        this.yPos = y;
    }

    public void update(double[] normalizedDirectionVector, double drawIntervalMovementModifier) {
        //System.out.println("0: " + normalizedDirectionVector[0] + " 1: " + normalizedDirectionVector[1]);
        this.addXPos(normalizedDirectionVector[0] * drawIntervalMovementModifier);
        this.addYPos(normalizedDirectionVector[1] * drawIntervalMovementModifier);
    }
        

}