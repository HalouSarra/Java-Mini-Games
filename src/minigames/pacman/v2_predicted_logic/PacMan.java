package minigames.pacman.v2_predicted_logic;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class PacMan extends JPanel implements ActionListener, KeyListener {
    class Block{
        int x;
        int y;
        int width;
        int height;
        Image image;

        int startX;
        int startY;
        char direction = 'U';
        int velocityX = 0;
        int velocityY = 0;

        Block(Image image, int x, int y, int width, int height){
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
        }

        void updateDirection(char direction){
            char prevDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x += this.velocityX;
            this.y += this.velocityY;
            for(Block wall : walls){
                if(collision(this, wall)){
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = prevDirection;
                    updateVelocity();
                }

            }
        }

        void updateVelocity(){
            if(this.direction == 'U'){
                velocityX = 0;
                velocityY = -tileSize/4;
            }
            else if(this.direction == 'D'){
                velocityX = 0;
                velocityY = +tileSize/4;

            }
            else if(this.direction == 'L'){
                velocityX = -tileSize/4;
                velocityY = 0;
            }
            else if(this.direction == 'R'){
                velocityX = +tileSize/4;
                velocityY = 0;
            }
        }

        void reset(){
            this.x = this.startX;
            this.y = this.startY;
        }
    }

    private final int rowCount = 21;
    private final int columnCount = 19;
    private final int tileSize = 32;
    private final int borderWidth = columnCount * tileSize;
    private final int borderHeight = rowCount * tileSize;

    private final Image wallImage;
    private final Image blueGhostImage;
    private final Image orangeGhostImage;
    private final Image pinkGhostImage;
    private final Image redGhostImage;

    private final Image pacmanUpImage;
    private final Image pacmanDownImage;
    private final Image pacmanLeftImage;
    private final Image pacmanRightImage;

    HashSet<Block> walls;
    HashSet<Block> ghosts;
    HashSet<Block> foods;
    Block pacman;
    Timer gameLoop;
    char[] directions = {'U', 'D', 'L', 'R'};
    Random random = new Random();
    int score = 0;
    int lives = 3;
    boolean gameOver = false;

    //X = wall, O = skip, P = pac man, ' ' = food
    //Ghosts: b = blue, o = orange, p = pink, r = red
    private final String[] tileMap = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "O       bpo       O",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"
    };

    PacMan(){
        setPreferredSize(new Dimension(borderWidth, borderHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);// make sure the JPanel is the component listening to key presses

        //load images
        wallImage = new ImageIcon(getClass().getResource("/minigames/pacman/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("/minigames/pacman/blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("/minigames/pacman/orangeGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("/minigames/pacman/pinkGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("/minigames/pacman/redGhost.png")).getImage();

        pacmanUpImage = new ImageIcon(getClass().getResource("/minigames/pacman/pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("/minigames/pacman/pacmanDown.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("/minigames/pacman/pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("/minigames/pacman/pacmanRight.png")).getImage();

        loadMap();
        for(Block ghost : ghosts){
            char newDirection = directions[random.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
        //how long it takes to start timer, milliseconds gone between frames
        gameLoop = new Timer(50,this); //20fps (1000/50)
        gameLoop.start();
    }

    public void loadMap(){
        walls = new HashSet<>();
        foods = new HashSet<>();
        ghosts = new HashSet<>();

        for(int r = 0; r < rowCount; r++){
            String row = tileMap[r];
            for(int c = 0; c < columnCount; c++){
                char tileMapChar = row.charAt(c);
                int x = c * tileSize;
                int y = r * tileSize;

                if(tileMapChar == 'X'){ //block wall
                    Block wall = new Block(wallImage, x, y, tileSize, tileSize);
                    walls.add(wall);
                }
                else if(tileMapChar == 'b'){
                    Block ghost = new Block(blueGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if(tileMapChar == 'o'){
                    Block ghost = new Block(orangeGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if(tileMapChar == 'p'){
                    Block ghost = new Block(pinkGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if(tileMapChar == 'r'){
                    Block ghost = new Block(redGhostImage, x, y, tileSize, tileSize);
                    ghosts.add(ghost);
                }
                else if(tileMapChar == 'P'){
                    pacman = new Block(pacmanRightImage, x, y, tileSize, tileSize);
                }
                else if(tileMapChar == ' '){
                    Block food = new Block(null, x + 14, y + 14, 4, 4);
                    foods.add(food);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.drawImage(pacman.image, pacman.x, pacman.y, tileSize, tileSize, null);

        for(Block wall:walls){
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }

        for(Block ghost:ghosts){
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }

        g.setColor(Color.WHITE);
        for(Block food:foods){
            g.fillOval(food.x, food.y, food.width, food.height);
        }

        //score
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        if(gameOver){
            g.drawString("Game Over: " + String.valueOf(score), tileSize / 2, tileSize / 2);
        }
        else{
            g.drawString("x" + String.valueOf(lives) + "Score: " + String.valueOf(score), tileSize / 2, tileSize / 2);
        }


    }

    public void move(){
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        //check pacman collisions
        for(Block wall : walls){
            if(collision(pacman, wall)){
                //Undo the previous steps
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
                break;
            }
        }

        //check ghost collisions
        for(Block ghost : ghosts){
            if(collision(ghost, pacman)){
                lives--;
                resetPositions();
            }
            if(ghost.y == tileSize * 9 && ghost.direction != 'U' && ghost.direction != 'D'){
                ghost.updateDirection('U');
            }
            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;
            for(Block wall : walls){
                if(collision(ghost, wall) || ghost.x <= 0 || ghost.x + ghost.width >= borderWidth){
                    ghost.x -= ghost.velocityX;
                    ghost.y -= ghost.velocityY;
                    char newDirection = directions[random.nextInt(4)];
                    ghost.updateDirection(newDirection);
                }
            }
        }

        //check food collisions
        Block foodEaten = null;
        for(Block food : foods){
            if(collision(pacman, food)){
                foodEaten = food;
                score += 10;
            }
        }
        foods.remove(foodEaten);
    }

    public boolean collision(Block a, Block b){
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint(); //call paintComponent again!
        //we define the things we want to repeat in actionPerformed function
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //keys with corespondent character
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // you can hold into the key as well
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // triggered only when press a key and let go
        if(e.getKeyCode() == KeyEvent.VK_UP){
            pacman.updateDirection('U');
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            pacman.updateDirection('D');
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            pacman.updateDirection('L');
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            pacman.updateDirection('R');
        }

        if(pacman.direction == 'U'){
            pacman.image = pacmanUpImage;
        }
        else if(pacman.direction == 'D'){
            pacman.image = pacmanDownImage;
        }
        else if(pacman.direction == 'L'){
            pacman.image = pacmanLeftImage;
        }
        else if(pacman.direction == 'R'){
            pacman.image = pacmanRightImage;
        }


    }
}