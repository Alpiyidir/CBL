package game.entities.instance.util;

import game.entities.general.Projectile;
import game.entities.instance.Bullet;
import game.entities.instance.Enemy;
import game.entities.instance.Player;
import game.entities.instance.Rocket;
import game.handlers.MouseHandler;
import game.util.MathHelpers;

public class ProjectileFactory {
    public static Projectile createProjectilee(Player player, MouseHandler mouseHandler) {
        double[] playerDirectionVector = player.getDirectionVector();

        if (player.getSelectedWeapon() == 0) {
            // Initialize bullet
            Bullet bullet = new Bullet(player.getPosX(), player.getPosY(), 7, 5,
                    MathHelpers.normalizeVector(new double[] { mouseHandler.getX()
                            - player.getPosX(), mouseHandler.getY() - player.getPosY() }),
                    player, 0);

            double[] bulletDirectionVector = bullet.getDirectionVector();

            // Adjust direction of bullet accounting for velocity of player
            double[] combinedDirectionVector = MathHelpers.sumVectors(playerDirectionVector,
                    bulletDirectionVector);
            bullet.setNormalizedDirectionVector(MathHelpers.normalizeVector(combinedDirectionVector));

            // Set new speed accounting for velocity of player
            bullet.setSpeed(Math.sqrt(Math.pow(
                    combinedDirectionVector[0], 2) + Math.pow(combinedDirectionVector[1], 2)));

            // Set bullet angle
            bullet.setAngle(mouseHandler.getX(), mouseHandler.getY());

            return bullet;
        } else if (player.getSelectedWeapon() == 1) {
            // Initialize rocket
            Rocket rocket = new Rocket(player.getPosX(), player.getPosY(), 4, 5,
                    MathHelpers.normalizeVector(new double[] { mouseHandler.getX()
                            - player.getPosX(), mouseHandler.getY() - player.getPosY() }),
                    player, 1);
            ;

            double[] rocketDirectionVector = rocket.getDirectionVector();

            // Adjust direction of rocket accounting for velocity of player
            double[] combinedDirectionVector = MathHelpers.sumVectors(playerDirectionVector,
                    rocketDirectionVector);
            // bullet.setNormalizedDirectionVector(MathHelpers.normalizeVector(combinedDirectionVector));

            // Set new speed accounting for velocity of player
            rocket.setSpeed(Math.sqrt(Math.pow(
                    combinedDirectionVector[0], 2) + Math.pow(combinedDirectionVector[1], 2)));

            // Set bullet angle
            rocket.setAngle(mouseHandler.getX(), mouseHandler.getY());
            return rocket;
        } else {
            return null;
        }

    }

    public static Projectile createProjectile(Enemy enemy, Player player) {
        
    }
}
