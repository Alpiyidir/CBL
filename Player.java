class Player extends CircularObject {
    private double radius;
    private double xPos;
    private double yPos;
    private double xMultiplier = 1;
    private double yMultiplier = 1;

    Player(double radius, double xPos, double yPos) {
        this.radius = radius;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    Player(double radius) {
        this.radius = radius;
        this.xPos = 1280 / 2;
        this.yPos = 720 / 2;
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

    public double getXDelta() {
        return xMultiplier;
    }

    public double getYDelta() {
        return yMultiplier;
    }
    
    public void addXPos(double changeX) {
        this.xPos += changeX * xMultiplier;
    }
    public void addYPos(double changeY) {
        this.yPos += changeY * yMultiplier;
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
        System.out.println("0: " + normalizedDirectionVector[0] + " 1: " + normalizedDirectionVector[1]);
        this.addXPos(normalizedDirectionVector[0] * drawInterval / Math.pow(10, 7));
        this.addYPos(normalizedDirectionVector[1] * drawInterval / Math.pow(10, 7));
    }
    
}