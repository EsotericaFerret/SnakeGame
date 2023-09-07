import java.awt.*;
import java.util.Random;

public class Apple {

    private Point position;

    public Apple() {
        position = new Point();
    }

    public void spawn() {
        Random random = new Random();
        int x = random.nextInt(SnakeGame.WIDTH / SnakeGame.UNIT_SIZE);
        int y = random.nextInt(SnakeGame.HEIGHT / SnakeGame.UNIT_SIZE);
        position.setLocation(x, y);
    }

    public Point getPosition() {
        return position;
    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(position.x * SnakeGame.UNIT_SIZE, position.y * SnakeGame.UNIT_SIZE, SnakeGame.UNIT_SIZE, SnakeGame.UNIT_SIZE);
    }
}