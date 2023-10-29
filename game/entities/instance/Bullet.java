package game.entities.instance;

import game.entities.general.CircularObject;
import game.entities.general.Projectile;

public class Bullet extends Projectile {
    private int type;

    public Bullet(double xPos, double yPos, double speed, double radius, double[] normalizedDirectionVector,
            CircularObject owner, int spriteNumber, int type) {
        super(xPos, yPos, speed, radius, normalizedDirectionVector, owner, spriteNumber);
        this.type = type;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
