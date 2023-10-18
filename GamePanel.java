import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

  
public class GamePanel extends JPanel implements Runnable {
    final int FPS = 240;

    Player player;
    KeyHandler keyHandler = new KeyHandler();

    
    Thread gameThread;

    GamePanel(Player player) {
        this.player = player;
        this.addKeyListener(keyHandler);
        Color lightBlue = new Color(50, 133, 168);
        this.setBackground(lightBlue);
        this.setFocusable(true);
    }

    void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update(drawInterval);
                repaint();
                delta--;
            }
        }
    }

    public void update(double drawInterval) {
        // Change player position
        player.update(keyHandler.getNormalizedDirectionVector(), drawInterval);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Paint player
        g.fillOval((int) player.getXPos(), (int) player.getYPos(), (int) player.getRadius(), (int) player.getRadius());
    }

}