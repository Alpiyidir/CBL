package game;

import javax.swing.*;

class Main {
    void startGame() {
        JFrame frame = new JFrame("Game Window");
        GamePanel gamePanel = new GamePanel();

        frame.add(gamePanel);
        frame.setSize(1280, 720);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.startGameThread();
    }

    public static void main(String[] args) {
        new Main().startGame();
    }
}