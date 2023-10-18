import java.awt.event.*;

public class KeyHandler implements KeyListener {
    
    private boolean rightPressed, leftPressed, upPressed, downPressed;

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
    }

    public boolean[] getKeysPressed() {
        return new boolean[]{upPressed, downPressed, leftPressed, rightPressed};
    }

    public double[] getNormalizedDirectionVector() {
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

        double sum = 0;
        for (int i = 0; i < directionVector.length; i++) {
            sum += Math.pow(directionVector[i], 2);
        }
        double magnitude = Math.sqrt(sum);
        for (int i = 0; i < directionVector.length; i++) {
            if (directionVector[i] != 0.0) {
                directionVector[i] /= magnitude;
            }
        }

        return directionVector;
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


