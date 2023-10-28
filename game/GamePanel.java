package game;

import game.entities.general.CircularObject;
import game.entities.general.Projectile;
import game.entities.instance.Bullet;
import game.entities.instance.Enemy;
import game.entities.instance.Explosion;
import game.entities.instance.Planet;
import game.entities.instance.Player;
import game.entities.instance.util.ProjectileFactory;
import game.handlers.KeyHandler;
import game.handlers.MouseHandler;
import game.util.MathHelpers;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    final int framesPerSecond = 240;
    int horizontalSize = 1280;
    int verticalSize = 720;

    double scaleX = 1;
    double scaleY = 1;

    JButton gameOverButton;

    Player player;

    Planet planet;

    ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    ArrayList<Explosion> explosions = new ArrayList<Explosion>();

    long lastProjectileTime;

    KeyHandler keyHandler;
    MouseHandler mouseHandler;

    JTextArea planetHealthTextField;

    JTextArea playerHealthTextField;

    // Images for the sprites

    Thread gameThread;

    GamePanel() {
        this.lastProjectileTime = System.nanoTime();

        this.player = new Player(horizontalSize / 2, verticalSize / 2, 2, 20, 5);
        this.playerHealthTextField = new JTextArea("Player health: " + player.getHealth());

        this.planet = new Planet(horizontalSize / 2, verticalSize / 2, 0, 40, 50);
        this.planetHealthTextField = new JTextArea("Planet health: " + planet.getHealth());

        this.gameOverButton = new JButton("Game over...", null);

        this.keyHandler = new KeyHandler();
        this.mouseHandler = new MouseHandler();
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);

        this.add(planetHealthTextField);
        planetHealthTextField.setEnabled(false);
        planetHealthTextField.setBounds(horizontalSize / 2 - 225, 10, 275, 50);
        planetHealthTextField.setOpaque(false);
        planetHealthTextField.setFont(planetHealthTextField.getFont().deriveFont(30f));

        this.add(playerHealthTextField);
        playerHealthTextField.setEnabled(false);
        playerHealthTextField.setBounds(horizontalSize / 2 + 100, 10, 275, 50);
        playerHealthTextField.setOpaque(false);
        playerHealthTextField.setFont(playerHealthTextField.getFont().deriveFont(30f));

        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setLayout(null);

        gameOverButton.setBounds(horizontalSize / 2 - 400 / 2, verticalSize / 2 - 400 / 2, 400, 400);
        gameOverButton.setVisible(false);
        this.add(gameOverButton);
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

        while (!gameThread.isInterrupted()) {

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

        if (gameThread.isInterrupted()) {
            System.out.println("Terminated");
            // On termination of GameWindow, this executes
            gameOverButton.setVisible(true);
            gameOverButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JComponent comp = (JComponent) e.getSource();
                    Window win = SwingUtilities.getWindowAncestor(comp);
                    win.dispose();
                    new Main().startGame();
                }
            });
            // this.setVisible(false);
        }
    }

    public void update(double drawInterval) {
        /*
         * Multiplicative movement speed modifier tied to drawInterval which is tied to
         * frame rate,
         * should be used while updating the position of any object in the game
         * multiplicatively
         * to ensure that movement speed is consistent across all framerates.
         */
        final double drawIntervalMovementModifier = drawInterval / Math.pow(10, 7);

        // Resize game
        Rectangle r = SwingUtilities.getWindowAncestor(this).getBounds();
        scaleX = r.width / (double) horizontalSize;
        scaleY = r.height / (double) verticalSize;

        CircularObject.setScaleX(scaleX);
        CircularObject.setScaleY(scaleY);

        planet.setPos(planet.getPosX(), planet.getPosY());

        // Terminate game if health is smaller than or equal to zero
        if (planet.getHealth() <= 0) {
            gameThread.interrupt();
        }

        // Check for player death
        if (player.getHealth() <= 0 && System.nanoTime() - 20 * 1e8 > player.getLastVisible()) {
            player.setIsAlive(false);
            player.setLastVisible(System.nanoTime());
        }

        // Check for player revival
        if (!player.getIsAlive() && System.nanoTime() - 10 * 1e8 > player.getLastVisible()) {
            player.setPos(horizontalSize / 2, verticalSize / 2);
            player.setIsAlive(true);
            player.setHealth(5);
            playerHealthTextField.setText("Player health: " + player.getHealth());
        }

        // Change player position
        double originalPosX = player.getPosX();
        double originalPosY = player.getPosY();
        player.updatePos(drawIntervalMovementModifier);
        if (player.getPosX() >= horizontalSize - player.getRadius() || player.getPosX() < 0 + player.getRadius()) {
            player.setPosX(originalPosX);
        }
        if (player.getPosY() >= verticalSize - player.getRadius() || player.getPosY() < 0 + player.getRadius()) {
            player.setPosY(originalPosY);
        }

        // Set player angle
        player.rotateTowards(mouseHandler.getX(), mouseHandler.getY());

        // Set current weapon type
        player.setSelectedWeapon(keyHandler.getCurrentWeapon());

        // Bullet movement
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);

            // Remove bullets at screen border
            if (bullet.getPosX() >= horizontalSize || bullet.getPosX() < 0 || bullet.getPosY() >= verticalSize
                    || bullet.getPosY() < 0) {
                bullets.remove(i);
            }

            bullet.updatePos(drawIntervalMovementModifier);
        }

        // adding new enemies
        if (Math.floor(ThreadLocalRandom.current().nextInt(0, 100)) == 0 && enemies.size() < 15) {

            // System.out.println("enemy spawned");
            // add enemies randomly to one of the sides of the screen
            int side = ThreadLocalRandom.current().nextInt(1, 5);
            // System.out.println("Side: " + side);
            switch (side) {
                // up
                case 1:
                    enemies.add(new Enemy((Math.floor(ThreadLocalRandom.current().nextInt(0, horizontalSize))), 10.0,
                            5.0, 10));
                    break;
                // right
                case 2:
                    enemies.add(new Enemy(horizontalSize - 10,
                            (Math.floor(ThreadLocalRandom.current().nextInt(0, verticalSize))), 5.0, 10));
                    break;
                // down
                case 3:
                    enemies.add(new Enemy((Math.floor(ThreadLocalRandom.current().nextInt(0, horizontalSize))),
                            verticalSize - 10.0, 5.0, 10));
                    break;
                // left
                case 4:
                    enemies.add(
                            new Enemy(10, (Math.floor(ThreadLocalRandom.current().nextInt(0, verticalSize))), 5.0, 10));
                    break;
                default:
                    break;
            }
        }

        // Enemy movement and bullet shooting
        for (int i = 0; i < enemies.size(); i++) {
            Enemy curEnemy = enemies.get(i);

            // Set enemy angle to face player
            curEnemy.rotateTowards(player.getPosX(), player.getPosY());

            double[] direction = new double[] { horizontalSize / 2.0 - curEnemy.getPosX(),
                    verticalSize / 2.0 - curEnemy.getPosY() };
            curEnemy.updatePos(MathHelpers.normalizeVector(direction), drawIntervalMovementModifier);

            if (player.getIsAlive()) {
                Bullet bullet = ProjectileFactory.createBullet(curEnemy, player);
                if (bullet != null) {
                    bullets.add(bullet);
                }
            }

        }

        // Collision between bullets and player
        if (player.getIsAlive() && System.nanoTime() - 35 * 1e8 > player.getLastVisible()) {
            for (int i = 0; i < bullets.size(); i++) {
                if (bullets.get(i).intersects(player) && bullets.get(i).getOwner() != player) {
                    player.setHealth(player.getHealth() - 1);
                    playerHealthTextField.setText("Player health: " + player.getHealth());
                    // Delete the bullet
                    bullets.remove(i);
                    break;
                }
            }
        }

        // Collision detection between bullets and enemies
        for (int i = 0; i < enemies.size(); i++) {
            boolean enemyHit = false;
            boolean rocketRemoved = false;
            for (int j = 0; j < bullets.size(); j++) {
                if (enemies.get(i).intersects(bullets.get(j)) && bullets.get(j).getOwner() != enemies.get(i)) {
                    Bullet currBullet = bullets.get(j);
                    switch (currBullet.getType()) {
                        case 0: // Normal bullet - just kill the enemy
                            enemies.remove(i);
                            enemyHit = true;
                            break;
                        case 1: // Rocket - kill enemies in the radius of blast
                            double explosionRadius = 100.0;
                            for (int k = 0; k < enemies.size(); k++) {
                                for (int m = 0; m < enemies.size(); m++) {
                                    if (enemies.get(m).distanceToPoint(currBullet.getPosX(),
                                            currBullet.getPosY()) <= explosionRadius) {
                                        enemies.remove(m);
                                        break;
                                    }
                                }
                            }

                            // Create explosion
                            explosions.add(new Explosion(currBullet.getPosX(), currBullet.getPosY(), 0, explosionRadius));

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
                planet.setHealth(planet.getHealth() - 1);
                planetHealthTextField.setText("Planet health: " + planet.getHealth());
                enemyHit = true;
                break;
            }
            if (enemyHit) {
                continue;
            }
        }

        // Explosions
        for (int i = 0; i < explosions.size(); i++) {
            Explosion explosion = explosions.get(i);

            // Update explosion radius
            explosion.updateRadius();

            // Delete explosion after a period of time passes
            if (explosion.shouldBeDeleted()) {
                explosions.remove(i);
                break;
            }
        }
    }

    public void processInputsThatCreateObjects() {
        if (mouseHandler.getMousePressed() && player.getIsAlive()) {
            Bullet bullet = ProjectileFactory.createBullet(player, mouseHandler);
            if (bullet != null) {
                bullets.add(bullet);
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

        // Paint the planet
        planet.draw(g);

        // Paint all bullets
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }

        // Paint all enemies
        g.setColor(Color.RED);
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        // Paint all explosions
        g.setColor(Color.red);
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).draw(g);
        }

        // Paint player
        if (player.getIsAlive()) {
            g.setColor(Color.ORANGE);
            player.draw(g);
        }
    }

}