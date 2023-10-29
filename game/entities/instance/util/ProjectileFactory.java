package game.entities.instance.util;

import game.entities.instance.Bullet;
import game.entities.instance.Enemy;
import game.entities.instance.Player;
import game.handlers.MouseHandler;
import game.util.MathHelpers;

/**
 * Factory for creating all derived types from Projectile.
 */
public class ProjectileFactory {
    /**
     * Create a bullet that originates from a player that goes to a mouse pos.
     */
    public static Bullet createBullet(Player player, MouseHandler mouseHandler) {
        if (System.nanoTime() - 1e8 <= player.getLastBulletTime() 
                && player.getSelectedWeapon() == 0) {
            return null;
        } else if (System.nanoTime() - 5 * 1e8 <= player.getLastBulletTime() 
                && player.getSelectedWeapon() == 1) {
            return null;
        }

        player.setLastBulletTime(System.nanoTime());

        double[] playerDirectionVector = player.getDirectionVector();

        double speed;
        switch (player.getSelectedWeapon()) {
            case 0:
                speed = 7;
                break;
            case 1:
                speed = 4;
                break;
            default:
                return null;
        }

        // Initialize bullet
        Bullet bullet = new Bullet(player.getPosX(), player.getPosY(), speed, 5,
                MathHelpers.normalizeVector(new double[] {mouseHandler.getX() - player.getPosX(),
                    mouseHandler.getY() - player.getPosY()}), player, 
                    player.getSelectedWeapon(), player.getSelectedWeapon());

        double[] bulletDirectionVector = bullet.getDirectionVector();

        // Adjust direction of bullet accounting for velocity of player
        double[] combinedDirectionVector = MathHelpers.sumVectors(playerDirectionVector,
                bulletDirectionVector);
        bullet.setNormalizedDirectionVector(MathHelpers.normalizeVector(combinedDirectionVector));

        // Set new speed accounting for velocity of player
        bullet.setSpeed(Math.sqrt(Math.pow(
                combinedDirectionVector[0], 2) + Math.pow(combinedDirectionVector[1], 2)));

        // Set bullet angle
        bullet.rotateTowards(mouseHandler.getX(), mouseHandler.getY());

        return bullet;
    }
    
    /** 
     * Creates a bullet that originates from an enemy that should travel 
     * to the current position of the player.
     */
    public static Bullet createBullet(Enemy enemy, Player player) {
        if (System.nanoTime() - 9 * 1e8 <= enemy.getLastBulletTime() || !enemy.getIsShooter()) {
            return null;
        }
        enemy.setLastBulletTime(System.nanoTime());
        Bullet bullet = new Bullet(enemy.getPosX(), enemy.getPosY(), 2.5, 5.0,
                MathHelpers.normalizeVector(
                        new double[]{player.getPosX() - enemy.getPosX(), 
                            player.getPosY() - enemy.getPosY()}),
                enemy, 2, 0);
        bullet.rotateTowards(player.getPosX(), player.getPosY());
        return bullet;

    }
}
