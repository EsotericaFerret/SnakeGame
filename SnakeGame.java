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

    static final int WIDTH = 800;
    static final int HEIGHT = 800;
    static final int UNIT_SIZE = 20;
    private static final int DELAY = 100;
    Scanner fileScanner = new Scanner(new File("score.txt"));
    private Snake snake;
    private Apple apple;
    private Timer timer;
    static boolean running;
    private Clip eatSound;
    private Clip gameOverSound;
    private JButton startButton;
    private JTextField scoreTextField = new JTextField(4); //Text Field for displaying the score.
    private int score = 0;
    private int highScore;
    private ActionListener startButtonListener = e -> {
        if (running) {
            // If the game is running, clicking the button will reset the game
            startGame();
        } else {
            // If the game is not running, clicking the button will start a new game
            startGame();
        }
    };
    

    public SnakeGame() throws IOException {
        highScore = fileScanner.nextInt();
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
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        startButton = new JButton("Start Game"); //JButton that lets you start the game.
        startButton.addActionListener(startButtonListener);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Trigger the button's ActionListener when Enter key is pressed
                    startButton.doClick();}
                }
            });
        startButton.setFocusable(false);
        scoreTextField.setEditable(false);
        scoreTextField.setFocusable(false);
        Main.frame.add(scoreTextField, BorderLayout.PAGE_END);
        Main.frame.add(startButton, BorderLayout.PAGE_END);
    }
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
    public void startGame() {
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
        startButton.setText("New Game"); // Change button text to "New Game"
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
        } else if (snake.getHead().equals(apple.getPosition())) {
            snake.grow();
            apple.spawn();
            score++;
            scoreTextField.setText(((Integer)score).toString());
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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {
            snake.draw(g);
            apple.draw(g);
        } else {
            gameOver(g);
        }
    }
//Method holds the logic for the Game Over graphic, which displays before the game has begun and after it ends.
    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Game Over!", WIDTH / 4, HEIGHT / 2);
        startButton.setText("Start Game"); // Change button text to "Start Game"
    }
//KeyAdapter that listens for the arrow keys to be pressed, overrides keyPressed to pass the information to the snake's changeDirection method.
    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            snake.changeDirection(key);
        }
    }
}