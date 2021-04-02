package turtle.util;

import turtle.Paper;

public class Position {
    int x;
    int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void wrap(Paper paper) {
        x = Math.floorMod(x, paper.getWidth());

        y = Math.floorMod(y, paper.getHeight());
    }

    public Position move(Direction direction) {
        int dirCode = direction.getDirectionCode();
        switch (dirCode) {
            case 0:
                return new Position(x, y + 1);
            case 1:
                return new Position(x + 1, y + 1);
            case 2:
                return new Position(x + 1, y);
            case 3:
                return new Position(x + 1, y - 1);
            case 4:
                return new Position(x, y - 1);
            case 5:
                return new Position(x - 1, y - 1);
            case 6:
                return new Position(x - 1, y);
            case 7:
                return new Position(x - 1, y + 1);
            default:
                return this;
        }
    }
}
