import java.awt.event.*;

import javax.swing.plaf.synth.SynthSplitPaneUI;

public class MouseHandler implements MouseListener{

   boolean mousePressed;
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

   void saySomething(String eventDescription, MouseEvent e) {
      //textArea.append(eventDescription + " detected on "
                     // + e.getComponent().getClass().getName()
                     //+ "." + newline);
   }

   public boolean getMousePressed(){
      return mousePressed;
   }
}