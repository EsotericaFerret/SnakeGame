import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu extends JPanel {

    private JButton easyButton;
    private JButton medButton;
    private JButton hardButton;
    private JButton insaneButton;
    public SnakeGame snakeGame;
    public JPanel buttonGroup;
    private ActionListener easyButtonListener = e -> {
            // If the game is not running, clicking the button will start a new game
            try {

                snakeGame = new SnakeGame();
                snakeGame.startGame(1);
                add(snakeGame);
                buttonGroup.setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    };
    private ActionListener medButtonListener = e -> {
            // If the game is not running, clicking the button will start a new game
            try {
                snakeGame = new SnakeGame();
                snakeGame.startGame(2);
                add(snakeGame);
                buttonGroup.setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    };
    private ActionListener hardButtonListener = e -> {
            // If the game is not running, clicking the button will start a new game
            try {
                snakeGame = new SnakeGame();
                snakeGame.startGame(3);
                add(snakeGame);
                buttonGroup.setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    };
    private ActionListener insaneButtonListener = e -> {
            // If the game is not running, clicking the button will start a new game
            try {
                snakeGame = new SnakeGame();
                snakeGame.startGame(4);
                add(snakeGame);
                buttonGroup.setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    };

    public MainMenu() {

    easyButton = new JButton("Start Easy Game"); //JButton that lets you start the game.
        easyButton.setBackground(Color.cyan);
        easyButton.addActionListener(easyButtonListener);
    medButton = new JButton("Start Medium Game"); //JButton that lets you start the game.
        medButton.setBackground(Color.green);
        medButton.addActionListener(medButtonListener);
    hardButton = new JButton("Start Hard Game"); //JButton that lets you start the game.
        hardButton.setBackground(Color.orange);
        hardButton.addActionListener(hardButtonListener);
    insaneButton = new JButton("Start Insane Game"); //JButton that lets you start the game.
        insaneButton.setBackground(Color.red);
        insaneButton.addActionListener(insaneButtonListener);

    buttonGroup = new JPanel();
    buttonGroup.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridwidth = GridBagConstraints.REMAINDER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.ipady = 50;
    gbc.ipadx = 50;
    gbc.insets = new Insets(75, 0, 0, 0);

    buttonGroup.setBackground(Color.black);
    buttonGroup.add(easyButton, gbc);
    buttonGroup.add(medButton, gbc);
    buttonGroup.add(hardButton, gbc);
    buttonGroup.add(insaneButton, gbc);
    
    setBackground(Color.black);
    add(buttonGroup);
}
}
