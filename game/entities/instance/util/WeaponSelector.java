package game.entities.instance.util;

import game.entities.instance.Enemy;
import game.entities.instance.Player;

public class WeaponSelector {
    int currentWeapon;

    public WeaponSelector(int currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    void setCurrentWeapon(int currentWeapon) {
        this.currentWeapon = currentWeapon;
    }

    int getCurrentWeapon() {
        return currentWeapon;
    }

    void shoot(Player player, double[] directionVector) {
        switch(currentWeapon) {
            case 0:
                
            case 1:
                break;
            default:
                break;
        }
    }

    void shoot(Enemy enemy, double[] directionVector) {
        
    }
}
