

class Player extends CircularObject {
    private int radius;
    private int xPos;
    private int yPos;

    Player(int radius, int xPos, int yPos) {
        this.radius = radius;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    Player(int radius) {
        this.radius = radius;
        this.xPos = 1280 / 2;
        this.yPos = 720 / 2;
    }

    public int getRadius() {
        return radius;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }
    
    public void updatePos(int changeX, int changeY) {
        this.xPos += changeX;
        this.yPos += changeY;
    }

    public void setPos(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }
    
}