class Player extends CircularObject {
    private double radius;
    private double xPos;
    private double yPos;
    private double speed;

    Player(double xPos, double yPos, double radius, double speed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.radius = radius;
        this.speed = speed;
    }

    Player(double radius) {
        this.radius = radius;
        this.xPos = 1280 / 2;
        this.yPos = 720 / 2;
        this.speed = 5;
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

    public double getSpeed() {
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

    public void update(double[] normalizedDirectionVector, double drawInterval) {
        //System.out.println("0: " + normalizedDirectionVector[0] + " 1: " + normalizedDirectionVector[1]);
        this.addXPos(normalizedDirectionVector[0] * drawInterval);
        this.addYPos(normalizedDirectionVector[1] * drawInterval);
    }
    
}