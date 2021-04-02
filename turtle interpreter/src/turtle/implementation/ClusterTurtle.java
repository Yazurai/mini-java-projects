package turtle.implementation;

import turtle.Turtle;
import turtle.util.Pen;
import turtle.util.Rotation;

public class ClusterTurtle implements Turtle {
    Turtle[] turtles;

    public ClusterTurtle(Turtle[] ts) {
        turtles = ts;
    }

    public void changePenState(Pen ps) {
        for (Turtle t : turtles) {
            t.changePenState(ps);
        }
    }

    public void changeBrush(char character) {
        for (Turtle t : turtles) {
            t.changeBrush(character);
        }
    }

    public void rotate(Rotation rotation, int multiplier) {
        for (Turtle t : turtles) {
            t.rotate(rotation, multiplier);
        }
    }

    public void mark() {
        for (Turtle t : turtles) {
            t.mark();
        }
    }

    public void move(int steps) {
        for (Turtle t : turtles) {
            t.move(steps);
        }
    }
}
