import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Snake {

    private LinkedList<Point> body;
    private char direction;
    private boolean grow;

    public Snake() {
        body = new LinkedList<>();
        direction = 'R';
        grow = false;
    }

    public void initialize() {
        body.clear();
        body.add(new Point(2, 0));
        body.add(new Point(1, 0));
        body.add(new Point(0, 0));
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
        g.setColor(Color.green);
        for (Point point : body) {
            g.fillRect(point.x * SnakeGame.UNIT_SIZE, point.y * SnakeGame.UNIT_SIZE, SnakeGame.UNIT_SIZE, SnakeGame.UNIT_SIZE);
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