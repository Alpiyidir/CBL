class Player extends CircularObject {
    private double radius;
    private double xPos;
    private double yPos;
    private double xDelta = 10;
    private double yDelta = 10;

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
        return xDelta;
    }

    public double getYDelta() {
        return yDelta;
    }
    
    public void setXPos(double changeX) {
        this.xPos += changeX * xDelta;
    }
    public void setYPos(double changeY) {
        this.yPos += changeY * yDelta;
    }

    public void setPos(double x, double y) {
        this.xPos = x;
        this.yPos = y;
    }
    
}