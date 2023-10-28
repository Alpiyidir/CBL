package game.entities.instance;

import game.entities.general.CircularObject;
import game.entities.general.CircularObjectWithUpdate;
import game.entities.general.Projectile;
import game.util.MathHelpers;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Projectile {
    public Bullet(double xPos, double yPos, double speed, double radius, double[] normalizedDirectionVector,
            CircularObject owner, int spriteNumber) {
        super(xPos, yPos, speed, radius, normalizedDirectionVector, owner, spriteNumber);
    }
}
