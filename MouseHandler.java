import java.awt.event.*;

public class MouseHandler implements MouseListener, MouseMotionListener {
   int posX;
   int posY;

   boolean mousePressed;

   public int getX() {
      return posX;
   }

   public int getY() {
      return posY;
   }

   public boolean getMousePressed() {
      return mousePressed;
   }

   public void mousePressed(MouseEvent e) {
      System.out.println("Mouse clicked");
      mousePressed = true;
   }

   public void mouseReleased(MouseEvent e) {
      mousePressed = false;
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