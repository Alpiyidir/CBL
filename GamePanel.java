import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

  
public class GamePanel extends JPanel implements ActionListener {
    Player player;
    KeyHandler keyH = new KeyHandler();
    
    Timer renderLoop = new Timer(1000 / 60, this);

    GamePanel(Player player) {
        this.player = player;
        this.addKeyListener(keyH);

        Color lightBlue = new Color(50, 133, 168);
        this.setBackground(lightBlue);
    }

    void startRenderLoop() {
        renderLoop.start();
    }    

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == renderLoop) {
            //Update
            update();
            //Render
            repaint();
        }
    }

    public void update(){
        // Change player position
        boolean[] keysPressed = keyH.getKeysPressed();
        
        
        //
        // Later do other stuff
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        //Paint player
        g.fillOval(player.getXPos(), player.getYPos(), player.getRadius(), player.getRadius());
    }

}