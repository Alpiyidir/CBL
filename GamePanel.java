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
    final int horizontalSize = 1280;
    final int verticalSize = 720;

    JButton gameOverButton = new JButton("Game over", null);

    Player player;

    Planet planet = new Planet(horizontalSize / 2, verticalSize / 2, 0, 40, 50);

    ArrayList<Enemy> enemies = new ArrayList<Enemy>();

    ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    ArrayList<Explosion> explosions = new ArrayList<Explosion>();

    int currentWeapon = 1; // 0 - AR; 1 - Bazooka; 2 - the laser thingy

    long lastBulletTime = System.nanoTime();

    KeyHandler keyHandler = new KeyHandler();
    MouseHandler mouseHandler = new MouseHandler();

    JTextArea planetHealthTextField = new JTextArea("Planet health: " + planet.getHealth());

    JTextArea playerHealthTextField = new JTextArea("Player health: " + 5);
    
    // Images for the sprites
    BufferedImage imgPlayer = null;
    double playerAngle = 0;


    Thread gameThread;

    GamePanel() {
        
        this.player = new Player(horizontalSize / 2, verticalSize / 2, 2, 12, 5);
        this.addKeyListener(keyHandler);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);

        this.add(planetHealthTextField);
        planetHealthTextField.setEditable(false);
        planetHealthTextField.setBounds(horizontalSize / 2 - 225, 10, 275, 50);
        planetHealthTextField.setOpaque(false);
        planetHealthTextField.setFont(planetHealthTextField.getFont().deriveFont(30f));

        this.add(playerHealthTextField);
        playerHealthTextField.setEditable(false);
        playerHealthTextField.setBounds(horizontalSize / 2 + 100, 10, 275, 50);
        playerHealthTextField.setOpaque(false);
        playerHealthTextField.setFont(playerHealthTextField.getFont().deriveFont(30f));

        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.setLayout(null);

        gameOverButton.setBounds(horizontalSize / 2 - 400/2, verticalSize / 2 - 400/2, 400, 400);
        gameOverButton.setVisible(false);
        this.add(gameOverButton);
        // Images for the sprites
        try {
            imgPlayer = toBufferedImage(ImageIO.read(new File("Spaceship.png")).getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                    new MainFrame().startGame();
                }
            });
            //this.setVisible(false);
        }
    }

    public void update(double drawInterval) {
        /*
         * Multiplicative movement speed modifier tied to drawInterval which is tied to frame rate,
         * should be used while updating the position of any object in the game multiplicatively
         * to ensure that movement speed is consistent across all framerates.
         */
        final double drawIntervalMovementModifier = drawInterval / Math.pow(10, 7);
        
        // Terminate game if health is smaller than or equal to zero
        if (planet.getHealth() <= 0) {
            gameThread.interrupt();
        }

        // Check for player death
        if (player.getHealth() <= 0 && System.nanoTime() - 20*1e8>player.getLastVisible()){
            player.setAlive(false);
            player.setLastVisible(System.nanoTime());
            //player.setHealth(5);
        }

        // Check for player revival
        //System.out.println(player.getLastVisible());
        //System.out.println((long)(System.nanoTime() - 1e8)>player.getLastVisible());
        if (!player.isAlive && System.nanoTime() - 10*1e8>player.getLastVisible()){
            player.setPos(horizontalSize / 2, verticalSize / 2);
            player.setAlive(true);
            player.setHealth(5);
            playerHealthTextField.setText("Player health: " + player.getHealth());
            //System.out.println(";last time|: " + player.getLastVisible());
            //System.out.println("Player revived, time:" + System.nanoTime());
            //System.out.println("diefferrence: " + (System.nanoTime() - 10*1e8));
            //System.out.println();
        }


        // Change player position
        player.updatePos(drawIntervalMovementModifier);

        // Set current weapon type
        currentWeapon = keyHandler.getCurrentWeapon();

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
        if (Math.floor(ThreadLocalRandom.current().nextInt(0, 100)) == 0 && enemies.size() < 15){

            //System.out.println("enemy spawned");
            // add enemies randomly to one of the sides of the screen
            int side = ThreadLocalRandom.current().nextInt(1, 5);
            //System.out.println("Side: " + side);
            switch (side) {
                //up
                case 1: 
                    enemies.add(new Enemy((Math.floor(ThreadLocalRandom.current().nextInt(0, horizontalSize))), 10.0, 5.0, 10));
                    break;
                //right
                case 2: 
                    enemies.add(new Enemy(horizontalSize - 10, (Math.floor(ThreadLocalRandom.current().nextInt(0, verticalSize))), 5.0, 10));
                    break;
                //down
                case 3:
                    enemies.add(new Enemy((Math.floor(ThreadLocalRandom.current().nextInt(0, horizontalSize))), verticalSize - 10.0, 5.0, 10));
                    break;
                //left
                case 4: 
                    enemies.add(new Enemy(10, (Math.floor(ThreadLocalRandom.current().nextInt(0, verticalSize))), 5.0, 10));
                    break;
                default:
                    break;
            }
        }
        // Enemy movement and bullet shooting
        for (int i = 0; i < enemies.size(); i++){
            Enemy curEnemy = enemies.get(i);
            double[] direction = new double[] {horizontalSize / 2.0 - curEnemy.getPosX(), 
                verticalSize / 2.0 - curEnemy.getPosY()};
            curEnemy.update(MathHelpers.normalizeVector(direction), drawIntervalMovementModifier);
            if (player.isAlive){
                Bullet enemyBullet = curEnemy.shootBullet(player);
                if (enemyBullet != null){
                    System.out.println("added enemy bullet");
                    bullets.add(enemyBullet);
                }
            }
            
            
        }

        // Collision between bullets and player
        if(player.isAlive && System.nanoTime() - 35*1e8 > player.lastVisible){
            for (int i = 0; i < bullets.size(); i++){
                if (bullets.get(i).intersects(player) && !bullets.get(i).getKillsEnemy()){
                    player.setHealth(player.getHealth()-1);
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
                if (enemies.get(i).intersects(bullets.get(j)) && bullets.get(j).getKillsEnemy()) {
                    Bullet currBullet = bullets.get(j);
                    switch (currBullet.type) {
                        case 0: // Normal bullet - just kill the enemy
                            enemies.remove(i);
                            enemyHit = true;
                            break;
                        case 1: // Rocket - kill enemies in the radius of blast
                            for (int k = 0; k < enemies.size(); k++) {
                                for (int m = 0; m < enemies.size(); m++){
                                    if (enemies.get(m).distanceToPoint(currBullet.getPosX(), currBullet.getPosY()) <= 100){
                                        enemies.remove(m);
                                        break;
                                    } 
                                }
                            }
                            
                            // Create explosion
                            explosions.add(new Explosion(currBullet.getPosX(), currBullet.getPosY(), 0, 100, System.nanoTime()));

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

        // Deleting explosions
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < explosions.size(); j++){
                if(System.nanoTime() - 2e8 > explosions.get(j).getTimeCreated()){
                    explosions.remove(j);
                    break;
                }
            }
        }
    }

    public void processInputsThatCreateObjects() {
        if (mouseHandler.getMousePressed() && player.isAlive) {
            // Set the right player angle
            if((mouseHandler.posX-player.getPosX()) < 0){
                playerAngle = -Math.atan((mouseHandler.posY-player.getPosY()) / -(double)(mouseHandler.posX-player.getPosX())) - (Math.PI/2.0);

            } else {
                playerAngle = Math.atan((mouseHandler.posY-player.getPosY()) / (double)(mouseHandler.posX-player.getPosX())) + (Math.PI/2.0);

            }

            

            switch(currentWeapon){
                case 0: // Assault Rifle
                    if (System.nanoTime() - 1e8 > lastBulletTime) {
                        //System.out.println("Bullet shot");

                        // Initialize bullet
                        Bullet bullet = new Bullet(player.getPosX(), player.getPosY(), 7, 5, 0, true,
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
                    break;
                case 1: // Bazooka
                    if (System.nanoTime() - 5*1e8 > lastBulletTime) {
                        //System.out.println("Rocket shot");

                        // Initialize bullet
                        Bullet bullet = new Bullet(player.getPosX(), player.getPosY(), 4, 5, 1, true,
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
                    break;
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
        g.setColor(Color.GREEN);
        g.fillOval(horizontalSize/2-(int)planet.getRadius(), verticalSize/2-(int)planet.getRadius(), (int)planet.getRadius()*2, (int)planet.getRadius()*2);

        g.setColor(Color.BLACK);
        // Paint all bullets
        for (int i = 0; i < bullets.size(); i++) {
            
            Bullet bullet = bullets.get(i);
            if (bullet.type == 0) {
                Color lightBlue = new Color(50, 133, 168);
                g.setColor(lightBlue);
            }
            else {
                g.setColor(Color.orange);
            }
            if(!bullet.getKillsEnemy()){
                g.setColor(Color.PINK);
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

        // Paint all explosions
        g.setColor(Color.red);
        for (int i = 0; i < explosions.size(); i++) {
            Explosion currExplosion = explosions.get(i);
            int size = ((int)Math.floor(((System.nanoTime() - currExplosion.getTimeCreated()) /100000.0 / (2*1e3)) * currExplosion.getRadius()*2));

            g.fillOval((int)(currExplosion.getPosX()-size/2.0), (int)(currExplosion.getPosY()-size/2.0), (int)size, (int)size);
        }
        
        
        // Paint player
        if(player.isAlive){
            g.setColor(Color.ORANGE);
            double playerRadius = player.getRadius();
            g.drawImage(rotateImage(imgPlayer, playerAngle), (int) (player.getPosX() - playerRadius), (int) (player.getPosY() - playerRadius), null);
        }
    }

    public Image rotateImage(BufferedImage image, double angle){
        // Rotation information

        double rotationRequired = angle;
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        // Drawing the rotated image at the required drawing locations
        return op.filter(image, null);
    }

    public static BufferedImage toBufferedImage(Image img)
{
    if (img instanceof BufferedImage)
    {
        return (BufferedImage) img;
    }

    // Create a buffered image with transparency
    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    // Draw the image on to the buffered image
    Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    // Return the buffered image
    return bimage;
}
}