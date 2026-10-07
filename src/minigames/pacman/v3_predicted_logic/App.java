package minigames.pacman.v3_predicted_logic;

import javax.swing.*;

public class App {
    public static void main(String[] args){
        int rowCount = 21;
        int columnCount = 19;
        int tileSize = 32;
        int borderWidth = columnCount * tileSize;
        int borderHeight = rowCount * tileSize;

        JFrame frame = new JFrame("Pac Man");
        frame.setSize(borderWidth, borderHeight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacMan pacmanGame = new PacMan();
        frame.add(pacmanGame);
        frame.pack(); //get the full size of our JPanel withing our window
        pacmanGame.requestFocus(); //after setting the focus on the JPanel
        frame.setVisible(true);
    }
}