import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

  
public class GamePanel extends JPanel implements ActionListener {
    Player player;
    KeyHandler keyHandler = new KeyHandler();

    
    Timer renderLoop = new Timer(1000 / 60, this);

    GamePanel(Player player) {
        this.player = player;
        this.addKeyListener(keyHandler);
        Color lightBlue = new Color(50, 133, 168);
        this.setBackground(lightBlue);
        this.setFocusable(true);
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
        double[] normalizedDirectionVector = keyHandler.getCurrentUnitDirectionVector();

        player.setXPos(normalizedDirectionVector[0]);
        player.setYPos(normalizedDirectionVector[1]);
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        //Paint player
        g.fillOval((int) player.getXPos(), (int) player.getYPos(), (int) player.getRadius(), (int) player.getRadius());
    }

}