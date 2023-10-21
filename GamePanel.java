import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.concurrent.ThreadLocalRandom;

import javax.swing.*;

  
public class GamePanel extends JPanel implements Runnable {
    final int framesPerSecond = 240;
    final int horizontalSize = 1280;
    final int verticalSize = 720;

    Player player;

    CircularObject planet = new Player(horizontalSize / 2, verticalSize / 2, 0, 40);

    ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    int planetHealth = 25;

    int currentWeapon = 1; // 0 - AR; 1 - Bazooka; 2 - the laser thingy

    long lastBulletTime = System.nanoTime();

    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler();

    JTextArea textField = new JTextArea("Health: " + planetHealth);
    

    Thread gameThread;

    GamePanel() {
        
        this.player = new Player(horizontalSize / 2, verticalSize / 2, 2, 12);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        this.add(textField);
        Color lightBlue = new Color(50, 133, 168);
        this.setBackground(lightBlue);
        textField.setBackground(lightBlue);
        textField.setFont(textField.getFont().deriveFont(30f));
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

        //System.out.println("Player x: " + player.getDirectionVector()[0] + " y: " + player.getDirectionVector()[1]);
        //Shoot bullet
        //System.out.println(mouseHandler.getMousePressed());
        
        //Bullet movement
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);

            // Remove bullets at screen border
            if (bullet.getPosX() >= horizontalSize || bullet.getPosX() < 0 || bullet.getPosY() >= verticalSize || bullet.getPosY() < 0) {
                bullets.remove(i);
            }

            bullet.updatePos(drawIntervalMovementModifier);
        }

        
        // adding new enemies
        if (Math.floor(Math.random() * 100) == 0 && enemies.size() < 15){

            System.out.println("enemy spawned");
            // add enemies randomly to one of the sides of the screen
            int side = ThreadLocalRandom.current().nextInt(1, 5);
            System.out.println("Side: " + side);
            switch (side) {
                //up
                case 1: 
                    enemies.add(new Enemy((Math.floor(Math.random() * horizontalSize)), 10.0, 5.0, 10));
                    break;
                //right
                case 2: 
                    enemies.add(new Enemy(horizontalSize - 10, (Math.floor(Math.random()*verticalSize)), 5.0, 10));
                    break;
                //down
                case 3:
                    enemies.add(new Enemy((Math.floor(Math.random() * horizontalSize)), verticalSize - 10.0, 5.0, 10));
                    break;
                //left
                case 4: 
                    enemies.add(new Enemy(10, (Math.floor(Math.random() * verticalSize)), 5.0, 10));
                    break;
                default:
                    break;
            }
        }
        // Enemy movement
        for (int i = 0; i < enemies.size(); i++){
            Enemy curEnemy = enemies.get(i);
            double[] direction = new double[] {horizontalSize / 2.0 - curEnemy.getPosX(), 
                verticalSize / 2.0 - curEnemy.getPosY()};
            curEnemy.update(MathHelpers.normalizeVector(direction), drawIntervalMovementModifier);
        }

        // Collision detection between bullets and enemies
        for (int i = 0; i < enemies.size(); i++) {
            boolean enemyHit = false;
            boolean rocketRemoved = false;
            for (int j = 0; j < bullets.size(); j++) {
                if (enemies.get(i).intersects(bullets.get(j))) {
                    switch (bullets.get(j).type) {
                        case 0: // Normal bullet - just kill the enemy
                            enemies.remove(i);
                            enemyHit = true;
                            break;
                        case 1: // Rocket - kill enemies in the radius of blast
                            for (int k = 0; k < enemies.size(); k++){
                                for (int m = 0; m < enemies.size(); m++){
                                    if (enemies.get(m).intersects(new Bullet(bullets.get(j).getPosX(), bullets.get(j).getPosY(), 0, 100, 0, null))){
                                        enemies.remove(m);
                                        break;
                                    } 
                                }
                            }
                            // Remove rocket
                            bullets.remove(j);
                            rocketRemoved = true;
                    }
                    break;
                }
                if (rocketRemoved) {
                    break;
                }
            }
            if (enemyHit) {
                continue;
            }
        }
        

        // Collision detection between enemies and the planet
        for (int i = 0; i < enemies.size(); i++) {
            boolean enemyHit = false;
            if (enemies.get(i).intersects(planet)) {
                enemies.remove(i);
                planetHealth -= 1;
                textField.setText("Health: " + planetHealth);
                enemyHit = true;
                break;
            }
            if (enemyHit) {
                continue;
            }
        }
    }

    public void processInputsThatCreateObjects() {
        if (mouseHandler.getMousePressed()) {
            switch(currentWeapon){
                case 0: // Assault Rifle
                    if (System.nanoTime() - 1e8 > lastBulletTime) {
                        System.out.println("Bullet shot");

                        // Initialize bullet
                        Bullet bullet = new Bullet(player.getPosX(), player.getPosY(), 7, 5, 0,
                            MathHelpers.normalizeVector(new double[] {mouseHandler.getX() 
                                - player.getPosX(), mouseHandler.getY() - player.getPosY()}));
                        

                        double[] bulletDirectionVector = bullet.getDirectionVector();
                        double[] playerDirectionVector = player.getDirectionVector();
                        
                        // Adjust direction of bullet accounting for velocity of player
                        double[] combinedDirectionVector = MathHelpers.sumVectors(playerDirectionVector, 
                            bulletDirectionVector);
                        bullet.setNormalizedDirectionVector(MathHelpers.normalizeVector(combinedDirectionVector));
                        
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
                case 1: // Bazooka
                    if (System.nanoTime() - 5*1e8 > lastBulletTime) {
                        System.out.println("Rocket shot");

                        // Initialize bullet
                        Bullet bullet = new Bullet(player.getPosX(), player.getPosY(), 4, 5, 1,
                            MathHelpers.normalizeVector(new double[] {mouseHandler.getX() 
                                - player.getPosX(), mouseHandler.getY() - player.getPosY()}));
                        

                        double[] bulletDirectionVector = bullet.getDirectionVector();
                        double[] playerDirectionVector = player.getDirectionVector();
                        
                        // Adjust direction of bullet accounting for velocity of player
                        double[] combinedDirectionVector = MathHelpers.sumVectors(playerDirectionVector, 
                            bulletDirectionVector);
                        //bullet.setNormalizedDirectionVector(MathHelpers.normalizeVector(combinedDirectionVector));
                        
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
            if (bullet.type == 0) {
                g.setColor(Color.gray);
            }
            else {
                g.setColor(Color.orange);
            }
            double bulletRadius = bullet.getRadius();
            g.fillOval((int) (bullet.getPosX() - bulletRadius), (int) (bullet.getPosY() - bulletRadius), (int) bulletRadius * 2, (int) bulletRadius * 2);
        }

        
        // Paint all enemies
        g.setColor(Color.RED);
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            double enemyRadius = enemy.getRadius();
            g.fillOval((int) (enemy.getPosX() - enemyRadius), (int) (enemy.getPosY() - enemyRadius), (int) enemyRadius * 2, (int) enemyRadius * 2);
        }

        //Pain the planet
        g.setColor(Color.GREEN);
        g.fillOval(horizontalSize/2-(int)planet.getRadius(), verticalSize/2-(int)planet.getRadius(), (int)planet.getRadius()*2, (int)planet.getRadius()*2);
    }

}