import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

  
public class GamePanel extends JPanel implements Runnable {
    final int FPS = 240;

    Player player;
    ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler();

    
    Thread gameThread;

    GamePanel(Player player) {
        this.player = player;
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
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

        //Shoot bullet
        System.out.println(mouseHandler.getMousePressed());;
        if (mouseHandler.getMousePressed()){
            System.out.println("Mouse pressed: ");
            bullets.add(new Bullet(player.getXPos(), player.getYPos(), new int[] {0, 0}));
        }
        //Bullet movement
        if (bullets != null){
            for (int i = 0; i < bullets.size(); i++){
                
            }
        }

        
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Paint player
        g.fillOval((int) player.getXPos(), (int) player.getYPos(), (int) player.getRadius(), (int) player.getRadius());

        // Paint all bullets
        if (bullets != null){
            for (int i = 0; i < bullets.size(); i++){
                g.fillOval((int) bullets.get(i).getXPos(), (int) bullets.get(i).getYPos(), 5, 5);
            }
        }
        
    }

}