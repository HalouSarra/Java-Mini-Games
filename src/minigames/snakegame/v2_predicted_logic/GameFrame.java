package minigames.snakegame.v2_predicted_logic;

import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame(){
        this.add(new GamePanel());
        this.setTitle("Snake Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack(); // fit all the component we add in the frame
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}