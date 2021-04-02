package turtle.implementation;

import turtle.Paper;
import turtle.util.Direction;
import turtle.util.Pen;
import turtle.util.Position;

public class WrappingTurtle extends AbstractTurtle {
    public WrappingTurtle(Position pos, Direction dir, Pen penSt, Paper paper, char brush) {
        super(pos, dir, penSt, paper, brush);
    }

    public void move(int distance) {
        for (int i = 0; i < distance; i++) {
            Position newPos = position.move(direction);
            //This could be minimally faster as it doesn’t do floorMod every step
            /*
            if (paper.isInbound(newPos)) {
                 position = newPos;
            } else {
                 newPos.wrap(paper);
                 position = newPos;
            }
            */
            position = newPos;
            position.wrap(paper);

            mark();
        }
    }
}
