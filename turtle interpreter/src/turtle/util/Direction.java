package turtle.util;

public class Direction {
    /*NORTH (0),
    NORTH_EAST (1),
    EAST (2),
    SOUTH_EAST (3),
    SOUTH (4),
    SOUTH_WEST (5),
    WEST (6),
    NORTH_WEST (7);*/

    private int directionCode;

    public Direction(int dirCode) {
        this.directionCode = dirCode;
    }

    public int getDirectionCode() {
        return this.directionCode;
    }

    //mirrors the current rotation by the x axis
    public void invertX() {
        directionCode = ((6 - directionCode) + 6) % 8;
    }

    //mirrors the current rotation by the y axis
    public void invertY() {
        directionCode = (8 - directionCode) % 8;
    }

    public void rotate(Rotation rotation, int multiplier) {
        directionCode += (rotation.ordinal() == 0) ? -multiplier : multiplier;
        directionCode = Math.floorMod(directionCode, 8);
    }
}
