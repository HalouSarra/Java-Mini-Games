package minigames.pacman.v2_predicted_logic;

import javax.swing.*;

public class App {
    public static void main(String[] args){
        int rowCount = 21;
        int columnCount = 19;
        int tileSize = 32;
        int borderWidth = columnCount * tileSize;
        int borderHeight = rowCount * tileSize;

        JFrame frame = new JFrame("Pac Man");
        frame.setVisible(true);
        frame.setSize(borderWidth, borderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        PacMan pacManGame = new PacMan();
        frame.add(pacManGame);
        frame.pack();
        pacManGame.requestFocus();
    }
}