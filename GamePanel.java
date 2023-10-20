import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

  
public class GamePanel extends JPanel implements Runnable {
    final int framesPerSecond = 240;

    Player player;

    ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    long lastBulletTime = System.nanoTime();

    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler();

    Thread gameThread;

    GamePanel() {
        this.player = new Player(1280 / 2, 720 / 2, 12, 5);
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
        double drawInterval = 1000000000 / framesPerSecond;
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
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).updatePos(drawIntervalMovementModifier);
        }

        // TODO: Enemy movement
        // adding new enemies
        if (Math.floor(Math.random()*100) == 14 && enemies.size() < 15){
            enemies.add(new Enemy((Math.floor(Math.random()*1280)), (Math.floor(Math.random()*720)), 5.0, 5.0));
        }

        /* 
         * All objects should be created after an update is finished so that they are not updated 
         * in the current loop
         */
        createNewObjects();
    }

    public void createNewObjects() {
        if (mouseHandler.getMousePressed()) {
            if (System.nanoTime() - 1e8 > lastBulletTime) {
                System.out.println("Bullet shot");
                bullets.add(new Bullet(player.getPosX(), player.getPosY(), 5, 5, new double[] {mouseHandler.getX()-player.getPosX(), mouseHandler.getY()-player.getPosY()}));

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
        g.fillOval((int) (player.getPosX() - playerRadius), (int) (player.getPosY() - playerRadius), (int) playerRadius * 2, (int) playerRadius * 2);

        // Paint all bullets
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            double bulletRadius = bullet.getRadius();
            g.fillOval((int) (bullet.getPosX() - bulletRadius), (int) (bullet.getPosY() - bulletRadius), (int) bulletRadius * 2, (int) bulletRadius * 2);
        }

        // Paint all enemies
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            double enemyRadius = enemy.getRadius();
            g.fillOval((int) (enemy.getPosX() - enemyRadius), (int) (enemy.getPosY() - enemyRadius), (int) enemyRadius * 2, (int) enemyRadius * 2);
        }
    }

}