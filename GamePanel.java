import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

  
public class GamePanel extends JPanel implements Runnable {
    final int framesPerSecond = 240;
    final int horizontalSize = 1280;
    final int verticalSize = 720;

    Player player;

    ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    long lastBulletTime = System.nanoTime();

    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler();

    Thread gameThread;

    GamePanel() {
        this.player = new Player(horizontalSize / 2, verticalSize / 2, 5, 12);
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
                processInputsThatDontCreateObjects();
                update(drawInterval);
                processInputsThatCreateObjects();
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
        player.updatePos(drawIntervalMovementModifier);

        System.out.println("Player x: " + player.getDirectionVector()[0] + " y: " + player.getDirectionVector()[1]);
        //Shoot bullet
        //System.out.println(mouseHandler.getMousePressed());
        
        //Bullet movement
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);

            // Remove bullets at screen border (hardcoded for now)
            if (bullet.getPosX() >= horizontalSize || bullet.getPosX() < 0 || bullet.getPosY() >= verticalSize || bullet.getPosY() < 0) {
                bullets.remove(i);
            }

            bullet.updatePos(drawIntervalMovementModifier);
        }

        // TODO: Enemy movement
        // adding new enemies
        if (Math.floor(Math.random()*100) == 14 && enemies.size() < 15){
            enemies.add(new Enemy((Math.floor(Math.random()*horizontalSize)), (Math.floor(Math.random()*verticalSize)), 5.0, 5.0));
        }
    }

    public void processInputsThatCreateObjects() {
        if (mouseHandler.getMousePressed()) {
            if (System.nanoTime() - 1e8 > lastBulletTime) {
                System.out.println("Bullet shot");

                // Initialize bullet
                Bullet bullet = new Bullet(player.getPosX(), player.getPosY(), 2, 5, 
                    MathHelpers.normalizeVector(new double[] {mouseHandler.getX() 
                        - player.getPosX(), mouseHandler.getY() - player.getPosY()}));
                

                double[] bulletDirectionVector = bullet.getDirectionVector();
                double[] playerDirectionVector = player.getDirectionVector();
                
                // Adjust direction of bullet accounting for velocity of player
                double[] combinedDirectionVector = MathHelpers.sumVectors(playerDirectionVector, 
                    bulletDirectionVector);
                double[] normalizedCombinedDirectionVector 
                    = MathHelpers.normalizeVector(combinedDirectionVector);
                bullet.setNormalizedDirectionVector(normalizedCombinedDirectionVector);
                
                // Set new speed accounting for velocity of player
                bullet.setSpeed(Math.sqrt(Math.pow(
                    combinedDirectionVector[0], 2) + Math.pow(combinedDirectionVector[1], 2)));

                // Add current bullet to bullets arraylist
                bullets.add(bullet);

                /*System.out.println(mouseHandler.getX());
                System.out.println(mouseHandler.getX()-player.getXPos());
                System.out.println(mouseHandler.getX()-player.getXPos());*/
                lastBulletTime = System.nanoTime();
            }
        }
    }

    public void processInputsThatDontCreateObjects() {
        this.player.setNormalizedDirectionVector(
            keyHandler.getNormalizedDirectionVectorFromKeys());
    }

    @Override
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