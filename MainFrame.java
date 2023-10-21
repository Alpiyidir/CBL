import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.event.ActionEvent;
import javax.swing.*;

class MainFrame {
    void startGame() {
        JFrame frame = new JFrame ("Game Window");
        GamePanel gamePanel = new GamePanel();

        frame.addKeyListener(new KeyHandler());
        frame.add(gamePanel);
        frame.setSize(1280, 720);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.startGameThread();
    }

    public static void main(String[] args) {
        new MainFrame().startGame();
    }
}