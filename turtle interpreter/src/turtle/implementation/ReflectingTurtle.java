package turtle.implementation;

import turtle.Paper;
import turtle.util.Direction;
import turtle.util.Pen;
import turtle.util.Position;

public class ReflectingTurtle extends AbstractTurtle {
    public ReflectingTurtle(Position pos, Direction dir, Pen penSt,
                            Paper paper, char brush) {
        super(pos, dir, penSt, paper, brush);
    }

    public void move(int steps) {
        for (int i = 0; i < steps; i++) {
            Position newPos = position.move(direction);
            if (paper.isInbound(newPos)) {
                position = newPos;
            } else {
                if (!paper.checkX(newPos.getX())) {
                    direction.invertY();
                    newPos = new Position(position.getX(), newPos.getY());
                }

                if (!paper.checkY(newPos.getY())) {
                    direction.invertX();
                    newPos = new Position(newPos.getX(), position.getY());
                }

                position = newPos;
            }
            mark();
        }
    }
}
