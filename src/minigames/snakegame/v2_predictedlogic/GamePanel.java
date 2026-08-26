package minigames.snakegame.v2_predictedlogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalTime;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int eatenApples = 0;
    int appleX, appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true); // When this window is open, send all keyboard typing directly to this canvas.
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
//        appleX = (int)(random.nextInt(UNIT_SIZE));
//        appleY = (int)(random.nextInt(UNIT_SIZE));
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //draw the vertical lines
        for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++){
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }
    }

    public void newApple(){

    }

    public void move(){

    }

    public void checkApple(){

    }

    public void checkCollisions(){

    }

    public void eatenApple(){

    }

    public void gameOver(Graphics g){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // Keyboard Controls
    public class MyKeyAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e){

        }

    }
}