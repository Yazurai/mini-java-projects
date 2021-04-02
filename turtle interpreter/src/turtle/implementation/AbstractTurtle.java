package turtle.implementation;

import turtle.Paper;
import turtle.Turtle;
import turtle.util.*;

abstract class AbstractTurtle implements Turtle {
     protected Position position;
     protected char brush;
     protected Direction direction;
     protected Pen penState;
     protected Paper paper;

     public AbstractTurtle(Position pos, Direction dir, Pen penSt,
                           Paper paper, char brush) {
     this.position = pos;
     this.brush = brush;
     this.direction = dir;
     this.penState = penSt;
     this.paper = paper;
     }

     public void changePenState(Pen penSt) {
     penState = penSt;
     }

     public void changeBrush(char character) {
     brush = character;
     }

     public void rotate(Rotation rotation, int multiplier) {
     this.direction.rotate(rotation, multiplier);
     }

     public void mark() {
     if (penState == Pen.DOWN) {
         paper.mark(position.getX(), position.getY(), brush);
         }
     }

     public abstract void move(int steps);
 }
