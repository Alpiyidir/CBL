package game.handlers;

import game.entities.general.util.GameScale;
import java.awt.event.*;

/**
 * Class MouseHandler
 * Handles mouse input.
 */
public class MouseHandler implements MouseListener, MouseMotionListener {
    int posX;
    int posY;

    boolean mousePressed;

    public int getX() {
        return (int) (posX / GameScale.getScaleX());
    }

    public int getY() {
        return (int) (posY / GameScale.getScaleY());
    }

    public boolean getMousePressed() {
        return mousePressed;
    }

    /**
     * Sets mousePressed to true when LMB is pressed.
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            System.out.println("Mouse Button Left Pressed");
            mousePressed = true;
        }
        
    }

    /**
     * Sets mousePressed to false when LMB is released.
     * @param e MouseEvent
     */
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1) {
            System.out.println("Mouse Button Left Released");
            mousePressed = false;
        }
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        this.posX = e.getX();
        this.posY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.posX = e.getX();
        this.posY = e.getY();
    }
}