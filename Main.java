import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {
static JFrame frame;
static MainMenu menu;
    public static void main(String[] args) {
        frame = new JFrame("Snake Game");
        menu = new MainMenu();
        menu.setPreferredSize(new Dimension(800, 800));
        frame.add(menu, BorderLayout.CENTER); //Using Border Layout to prevent too many things stacking on top of each other.
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(0, 0);
        frame.setVisible(true);
    }
    
}
