import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import javax.swing.*;



class GameController {
    Player player;
    KeyHandler keyHandler;

    GameController() {
        this.player = new Player(25);
        this.keyHandler = new KeyHandler();
    }

    void startGame() {
        JFrame frame = new JFrame ("Game Window");

        frame.addKeyListener(new KeyHandler());

        GamePanel gamePanel = new GamePanel(player);

        frame.setSize(1280, 720);
        frame.add(gamePanel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gamePanel.startGameThread();
    }

    public static void main(String[] args) {
        new GameController().startGame();
    }
}