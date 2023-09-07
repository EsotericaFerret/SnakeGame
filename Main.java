import java.io.IOException;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake Game");
        SnakeGame game;
        try {
            game = new SnakeGame();
            frame.add(game);
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
