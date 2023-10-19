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

    long lastBulletTime = (long) (System.nanoTime() - 1e8);

    GamePanel(Player player) {
        this.player = player;
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
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
        //System.out.println(mouseHandler.getMousePressed());
        if (mouseHandler.getMousePressed() && System.nanoTime() - 1e8 > lastBulletTime) {
            System.out.println("Mouse pressed: ");
            bullets.add(new Bullet(player.getXPos(), player.getYPos(), new double[] {mouseHandler.getX()-player.getXPos(), mouseHandler.getY()-player.getYPos()}));
            System.out.println(mouseHandler.getX());
            
            System.out.println(mouseHandler.getX()-player.getXPos());
            System.out.println(mouseHandler.getX()-player.getXPos());
            lastBulletTime = System.nanoTime();
        }
        //Bullet movement
        if (bullets != null) {
            for (int i = 0; i < bullets.size(); i++){
                bullets.get(i).addXPos();
                bullets.get(i).addYPos();
            }
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Paint player
        double playerRadius = player.getRadius();
        g.fillOval((int) (player.getXPos() - playerRadius), (int) (player.getYPos() - playerRadius), (int) player.getRadius() * 2, (int) player.getRadius() * 2);
        // Paint all bullets
        if (bullets != null)   {
            for (int i = 0; i < bullets.size(); i++) {
                double radius = 4;
                g.fillOval((int) (bullets.get(i).getXPos() - radius), (int) (bullets.get(i).getYPos() - radius), (int) radius * 2, (int) radius * 2);
            }
        }
        
    }

}