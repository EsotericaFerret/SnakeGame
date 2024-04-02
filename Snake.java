import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;
import javax.imageio.ImageIO;

//Snake class for the controllable element of the game.
public class Snake extends Entity{

    private LinkedList<Point> body;
    private char direction;
    private boolean grow;
    private BufferedImage snake;
    private Graphics2D rescaledSnake;

    public Snake() {
        body = new LinkedList<>(); //The Snake uses a linked list to track the growth and position of it's body.
        direction = 'R';
        grow = false;
    }

    public void initialize() throws IOException {
        body.clear();
        body.add(new Point(2, 0));
        body.add(new Point(1, 0));
        body.add(new Point(0, 0));
        snake = ImageIO.read(new File("wormGreen.png"));
        direction = 'R';
        grow = false;
    }

    public Point getHead() {
        return body.getFirst();
    }

    public void move() {
        Point newHead = (Point) getHead().clone();

        switch (direction) {
            case 'U':
                newHead.translate(0, -1);
                break;
            case 'D':
                newHead.translate(0, 1);
                break;
            case 'L':
                newHead.translate(-1, 0);
                break;
            case 'R':
                newHead.translate(1, 0);
                break;
        }

        body.addFirst(newHead);

        if (!grow) {
            body.removeLast();
        } else {
            grow = false;
        }
    }

    public boolean checkSelfCollision() {
        Point head = getHead();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkWallCollision() {
        Point head = getHead();
        int maxX = (SnakeGame.WIDTH / SnakeGame.UNIT_SIZE) - 1;
        int maxY = (SnakeGame.HEIGHT / SnakeGame.UNIT_SIZE) - 1;
    
        return head.x < 0 || head.y < 0 || head.x > maxX || head.y > maxY;
    }

    public void draw(Graphics g) {
        for (Point point : body) {
            g.drawImage(snake, point.x * SnakeGame.UNIT_SIZE, point.y * SnakeGame.UNIT_SIZE, null);
        }
    }

    public void changeDirection(int key) {
        switch (key) {
            case KeyEvent.VK_UP:
                if (direction != 'D')
                    direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U')
                    direction = 'D';
                break;
            case KeyEvent.VK_LEFT:
                if (direction != 'R')
                    direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L')
                    direction = 'R';
                break;
        }
    }

    public void grow() {
        grow = true;
    }
}