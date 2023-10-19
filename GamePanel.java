import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

  
public class GamePanel extends JPanel implements Runnable {
    final int FPS = 240;

    Player player;

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    long lastBulletTime = (long) (System.nanoTime());

    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler();

    Thread gameThread;

    GamePanel() {
        this.player = new Player(12);
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
        /*
         * Multiplicative movement speed modifier tied to drawInterval which is tied to frame rate,
         * should be used while updating the position of any object in the game multiplicatively
         * to ensure that movement speed is consistent across all framerates.
         */
        final double drawIntervalMovementModifier = drawInterval / Math.pow(10, 7);
        
        // Change player position
        player.update(keyHandler.getNormalizedDirectionVectorFromKeys(), drawIntervalMovementModifier);

        //Shoot bullet
        //System.out.println(mouseHandler.getMousePressed());
        
        //Bullet movement
        for (int i = 0; i < bullets.size(); i++){
            bullets.get(i).updatePos(drawIntervalMovementModifier);
        }

        /* 
         * All objcets should be created after an update is finished so that they are not updated 
         * in the current loop
         */
        createNewObjects();
    }

    public void createNewObjects() {
        if (mouseHandler.getMousePressed()) {
            if (System.nanoTime() - 1e8 > lastBulletTime) {
                System.out.println("Bullet shot");
                bullets.add(new Bullet(player.getXPos(), player.getYPos(), 5, 5, new double[] {mouseHandler.getX()-player.getXPos(), mouseHandler.getY()-player.getYPos()}));

                /*System.out.println(mouseHandler.getX());
                System.out.println(mouseHandler.getX()-player.getXPos());
                System.out.println(mouseHandler.getX()-player.getXPos());*/
                lastBulletTime = System.nanoTime();
            }
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Paint player
        double playerRadius = player.getRadius();
        g.fillOval((int) (player.getXPos() - playerRadius), (int) (player.getYPos() - playerRadius), (int) playerRadius * 2, (int) playerRadius * 2);

        // Paint all bullets
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            g.fillOval((int) (bullet.getXPos() - bullet.radius), (int) (bullet.getYPos() - bullet.radius), (int) bullet.radius * 2, (int) bullet.radius * 2);
        }
    }

}