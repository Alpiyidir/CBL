import java.awt.event.*;

public class KeyHandler implements KeyListener {
    
    private boolean rightPressed, leftPressed, upPressed, downPressed, spacePressed;
    private int weaponType;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
        if (code == KeyEvent.VK_1) {
            weaponType = 0;
        }
        if (code == KeyEvent.VK_2) {
            weaponType = 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S ){
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
    }

    public boolean[] getKeysPressed() {
        return new boolean[]{upPressed, downPressed, leftPressed, rightPressed};
    }

    public int getCurrentWeapon(){
        return weaponType;
    }

    public double[] getNormalizedDirectionVectorFromKeys() {
        double[] directionVector = new double[2];
        if (!(upPressed && downPressed)) {
            if (upPressed) {
                directionVector[1] = -1;
            } else if (downPressed) {
                directionVector[1] = 1;
            }
        }

        if (!(leftPressed && rightPressed)) {
            if (leftPressed) {
                directionVector[0] = -1;
            } else if (rightPressed) {
                directionVector[0] = 1;
            }
        }

        return MathHelpers.normalizeVector(directionVector);
    }

    /*protected class KeysPressed {
        boolean upPressed;
        boolean downPressed;
        boolean leftPressed;
        boolean rightPressed;

        KeysPressed(boolean upPressed, boolean downPressed, boolean leftPressed, boolean rightPressed) {
            this.upPressed = upPressed;
            this.downPressed = downPressed;
            this.leftPressed = leftPressed;
            this.rightPressed = rightPressed;
        }
    }*/
}


