import java.awt.event.*;

import javax.swing.plaf.synth.SynthSplitPaneUI;

public class MouseHandler implements MouseListener, MouseMotionListener{

   int xPos;
   int yPos;

   boolean mousePressed;

   public int getX() {
      return xPos;
   }

   public int getY() {
      return yPos;
   }


   public void mousePressed(MouseEvent e) {

      System.out.println("Mouse clicked");
      mousePressed = true;
      saySomething("Mouse pressed; # of clicks: "
                  + e.getClickCount(), e);
   }

   public void mouseReleased(MouseEvent e) {
      mousePressed = false;
      saySomething("Mouse released; # of clicks: "
                  + e.getClickCount(), e);
   }

   public void mouseEntered(MouseEvent e) {
      saySomething("Mouse entered", e);
   }

   public void mouseExited(MouseEvent e) {
      saySomething("Mouse exited", e);
   }

   public void mouseClicked(MouseEvent e) {
      saySomething("Mouse clicked (# of clicks: "
                  + e.getClickCount() + ")", e);
   }

   @Override
   public void mouseMoved(MouseEvent e){
      this.xPos = e.getX();
      this.yPos = e.getY();
   }

   void saySomething(String eventDescription, MouseEvent e) {
      //textArea.append(eventDescription + " detected on "
                     // + e.getComponent().getClass().getName()
                     //+ "." + newline);
   }

   public boolean getMousePressed(){
      return mousePressed;
   }

   @Override
   public void mouseDragged(MouseEvent e) {
      this.xPos = e.getX();
      this.yPos = e.getY();
      // TODO Auto-generated method stub
      //throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
   }
}