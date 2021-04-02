package turtle;

import turtle.util.Pen;
import turtle.util.Rotation;

public interface Turtle {
    void changePenState(Pen ps);

    void changeBrush(char character);

    void rotate(Rotation rotation, int multiplier);

    void mark();

    void move(int steps);
}
