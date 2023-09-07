import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

public class Main {
static JFrame frame;
    public static void main(String[] args) {
        frame = new JFrame("Snake Game");
        SnakeGame game;
        try {
            game = new SnakeGame();
            frame.add(game, BorderLayout.CENTER); //Using Border Layout to hopefully prevent too many things stacking on top of each other.
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}
