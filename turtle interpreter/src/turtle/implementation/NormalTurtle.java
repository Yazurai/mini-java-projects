package turtle.implementation;

import turtle.Paper;
import turtle.util.Direction;
import turtle.util.Pen;
import turtle.util.Position;

public class NormalTurtle extends AbstractTurtle {

    public NormalTurtle(Position pos, Direction dir, Pen penSt,
                        Paper paper, char brush) {
        super(pos, dir, penSt, paper, brush);
    }

    public void move(int steps) {
        for (int i = 0; i < steps; i++) {
            Position newPos = position.move(direction);
            if (paper.isInbound(newPos)) {
                position = newPos;
            }
            mark();
        }
    }
}

