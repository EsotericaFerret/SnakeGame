import java.awt.Graphics;
//Parent class for Snake and Apple. Originally had more planned for this, but much of the functionality had to be separated due to how differently they are handled. Still has a single abstract method, however, and could easily be used to implement additional functionality.
public abstract class Entity {

    abstract public void draw(Graphics g);
}
