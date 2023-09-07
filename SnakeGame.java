import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.sound.sampled.*;
import java.io.*;

public class SnakeGame extends JPanel implements ActionListener {

    static final int WIDTH = 800;
    static final int HEIGHT = 800;
    static final int UNIT_SIZE = 20;
    private static final int DELAY = 100;;
    private Snake snake;
    private Apple apple;
    private Timer timer;
    static boolean running;
    private Clip eatSound;
    private Clip gameOverSound;
    private JButton startButton;
    private int score = 0;
    private FileReader fr;
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
        try {
            fr = new FileReader("score.txt");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int ch;
        while ((ch = fr.read()) != -1)
        snake = new Snake(); //instantiates an instance of the Snake class.
        apple = new Apple(); //instantiates an instance of the Apple class.
        try {
            eatSound = AudioSystem.getClip();
            AudioInputStream eatStream = AudioSystem.getAudioInputStream(new File("eat.wav")); //Pulls an audio stream from wave file, which provided the sound for eating an apple.
            eatSound.open(eatStream);
            gameOverSound = AudioSystem.getClip();
            AudioInputStream gameOverStream = AudioSystem.getAudioInputStream(new File("gameover.wav")); //Pulls an audio stream from wave file, which provided the game over sound.
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
        startButton.setFocusable(false);
        add(startButton);
    }

    public void startGame() {
        // Stop and nullify the timer
        if (timer != null) {
            timer.stop();
            timer = null;
        }
        snake.initialize();
        apple.spawn();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
        startButton.setText("New Game"); // Change button text to "New Game"
    }

    public void checkCollision() {
        if (snake.checkSelfCollision() || snake.checkWallCollision()) {
            running = false;
            timer.stop();
            if (score > highScore) {
                
            }
            gameOverSound.stop();
            gameOverSound.setFramePosition(0);
            gameOverSound.start();
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

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Game Over!", WIDTH / 4, HEIGHT / 2);
        startButton.setText("Start Game"); // Change button text to "Start Game"
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            snake.changeDirection(key);
        }
    }
}