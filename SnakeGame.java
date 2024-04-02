import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.sound.sampled.*;
import java.io.*;
import java.util.Scanner;

public class SnakeGame extends JPanel implements ActionListener { //The SnakeGame itself is a JPanel container.

    static public int WIDTH;
    static public int HEIGHT;
    static int UNIT_SIZE = 20;
    private static final int DELAY = 100;
    Scanner fileScanner;
    private Snake snake;
    private Apple apple;
    private Timer timer;
    static boolean running;
    private Clip eatSound;
    private Clip gameOverSound;
    private int score = 0;
    private int highScore;
    
    
    public SnakeGame() throws IOException {
        score = 0;
        snake = new Snake(); //instantiates an instance of the Snake class.
        apple = new Apple(); //instantiates an instance of the Apple class.
        try {
            eatSound = AudioSystem.getClip();
            AudioInputStream eatStream = AudioSystem.getAudioInputStream(new File("eat.wav")); //Pulls an audio stream from wave file, which provides the sound for eating an apple.
            eatSound.open(eatStream);
            gameOverSound = AudioSystem.getClip();
            AudioInputStream gameOverStream = AudioSystem.getAudioInputStream(new File("gameover.wav")); //Pulls an audio stream from wave file, which provides the game over sound.
            gameOverSound.open(gameOverStream);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
        setBackground(Color.black);
        Main.menu.setBackground(Color.white);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        
    };
//Method to check the current score versus the score stored in score.txt. If the new score is higher, it will write the new score to score.txt. Otherwise, it writes the old score back to the same file.
    public void checkScore(int newScore, int oldScore) {
        FileWriter fw = null;
        try {
            fw = new FileWriter("score.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (newScore > oldScore) {
            try {
                fw.write(((Integer) newScore).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            highScore = newScore;
        }
        else {
            try {
                fw.write(((Integer) oldScore).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//Method starts the game, placing the snake and apple on the field.
    public void startGame(int difficulty) throws Exception {
        //Set window size based on difficulty
        if (difficulty == 1){
            fileScanner = new Scanner(new File("scoreeasy.txt"));
            Main.menu.setPreferredSize(new Dimension(1020, 1020));
            setPreferredSize(new Dimension(1000, 1000));
            WIDTH = 1000;
            HEIGHT = 1000;
            Main.frame.pack();
        }
        else if (difficulty == 2){
            fileScanner = new Scanner(new File("scoremedium.txt"));
            Main.menu.setPreferredSize(new Dimension(820, 820));
            setPreferredSize(new Dimension(800, 800));
            WIDTH = 800;
            HEIGHT = 800;
            Main.frame.pack();
        }
        else if (difficulty == 3) {
            fileScanner = new Scanner(new File("scorehard.txt"));
            Main.menu.setPreferredSize(new Dimension(620, 620));
            setPreferredSize(new Dimension(600, 600));
            WIDTH = 600;
            HEIGHT = 600;
            Main.frame.pack();
        }
        else if (difficulty == 4) {
            fileScanner = new Scanner(new File("scoreinsane.txt"));
            Main.menu.setPreferredSize(new Dimension(420, 420));
            setPreferredSize(new Dimension(400, 400));
            WIDTH = 400;
            HEIGHT = 400;
            Main.frame.pack();
        }
        // Stop and nullify the timer
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        score = 0;
        snake.initialize();
        apple.spawn();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        highScore = fileScanner.nextInt();
    }
//Checks for snake collisions with itself, the walls and an apple. Walls or itself result in game over, while an apple causes the snake to grow and the eating sound to play.
    public void checkCollision() {
        if (snake.checkSelfCollision() || snake.checkWallCollision()) {
            running = false;
            timer.stop();
            checkScore(score, highScore);
            gameOverSound.stop();
            gameOverSound.setFramePosition(0);
            gameOverSound.start();
            menuReturnButton();
        } else if (snake.getHead().equals(apple.getPosition())) {
            snake.grow();
            apple.spawn();
            score++;
            eatSound.stop();
            eatSound.setFramePosition(0); // Rewind to the beginning of the sound
            eatSound.start();
        }
    }

    @Override
     public void actionPerformed(ActionEvent e) {
        if (running) {
            snake.move();
            checkCollision();
        }
        repaint();
    }
//Overide of paintComponent method to redraw the screen.
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (running) {
            snake.draw(g);
            apple.draw(g);
        } else {
            gameOver(g);
        }
        highScoreCounter(g);
        currentScoreCounter(g);
    }
//Method holds the logic for the Game Over graphic, which displays before the game has begun and after it ends.
    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Game Over!", WIDTH * 3 / 8, HEIGHT * 3 / 7);
    }
    public void currentScoreCounter(Graphics g) {
        g.setColor(Color.green);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(((Integer)score).toString(), 10, 25);
    }
    public void highScoreCounter(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(((Integer)highScore).toString(), WIDTH - 25, 25);
    }
//KeyAdapter that listens for the arrow keys to be pressed, overrides keyPressed to pass the information to the snake's changeDirection method.
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            actionPerformed(null);
            int key = e.getKeyCode();
            snake.changeDirection(key);
        }
    }
    private void menuReturnButton() {
        JButton menuButton = new JButton("Main Menu");
        menuButton.addActionListener(menuButtonListener);
        menuButton.setBackground(Color.red);
        add(menuButton, BorderLayout.CENTER);
        Main.frame.pack();
    }

    private ActionListener menuButtonListener = e -> {
        Main.menu.buttonGroup.setVisible(true);
        Main.menu.snakeGame.setVisible(false);
        Main.menu.setBackground(Color.black);
        Main.menu.setPreferredSize(new Dimension(800, 800));
        Main.frame.pack();
    };
}