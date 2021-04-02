package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Cell {

    private final int x, y;

    private boolean visited = false;

    // top, right, bottom, left 
    public final boolean[] walls = {true, true, true, true};
    
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean[] getWalls() {
        return walls;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private boolean hasWalls() {
        for(boolean w : walls) if(w) return true;
        return false;
    }

    public boolean touches(Cell other) {
        if (isNeighbour(other)) {
            return !wallExists(other);
        }
        return false;
    }

    private boolean isNeighbour(Cell other) {
        if (x == other.x) {
            return Math.abs(y - other.y) <= 1;
        } else if (y == other.y) {
            return Math.abs(x - other.x) <= 1;
        }
        return false;
    }

    private boolean wallExists(Cell other) {
        int x = this.x - other.x;
        // top 0, right 1, bottom 2, left 3

        if (x == 1) {
            return walls[3];
        } else if (x == -1) {
            return walls[1];
        }

        int y = this.y - other.y;

        if (y == 1) {
            return walls[0];
        } else if (y == -1) {
            return walls[2];
        }
        return false;
    }

    public boolean legalMove(Direction d) {
        switch (d) {
            case UP:
                return !walls[0];
            case RIGHT:
                return !walls[1];
            case DOWN:
                return !walls[2];
            case LEFT:
                return !walls[3];
        }
        return false;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void draw(Graphics2D g, int w) {
        int x2 = x * w;
        int y2 = y * w;
        g.setColor(Color.WHITE);
        if (walls[0]) {
            g.drawLine(x2, y2, x2 + w, y2);
        }
        if (walls[1]) {
            g.drawLine(x2 + w, y2, x2 + w, y2 + w);
        }
        if (walls[2]) {
            g.drawLine(x2 + w, y2 + w, x2, y2 + w);
        }
        if (walls[3]) {
            g.drawLine(x2, y2 + w, x2, y2);
        }
    }

    public void removeSingleWall(List<Cell> grid) {
        if (hasWalls()) {
            boolean removed = false;
            Random r = new Random();
            List<Cell> neighbours = getAllNeighbours(grid);
            while (!removed) {
                int i = r.nextInt(4);
                if (walls[i]) {
                    removeWalls(neighbours.get(i));
                    removed = true;
                }
            }
        }
    }

    public void removeWalls(Cell other) {
        int x = this.x - other.x;
        // top 0, right 1, bottom 2, left 3

        if (x == 1) {
            walls[3] = false;
            other.walls[1] = false;
        } else if (x == -1) {
            walls[1] = false;
            other.walls[3] = false;
        }

        int y = this.y - other.y;

        if (y == 1) {
            walls[0] = false;
            other.walls[2] = false;
        } else if (y == -1) {
            walls[2] = false;
            other.walls[0] = false;
        }
    }

    private Cell checkCellExists(List<Cell> grid, Cell neighbour) {
        if (grid.contains(neighbour)) {
            return grid.get(grid.indexOf(neighbour));
        } else {
            return null;
        }
    }

    public List<Cell> getUnvisitedNeighboursList(List<Cell> grid) {

        List<Cell> neighbours = getAllNeighbours(grid);

        Cell top = checkCellExists(grid, new Cell(x, y - 1));
        Cell right = checkCellExists(grid, new Cell(x + 1, y));
        Cell bottom = checkCellExists(grid, new Cell(x, y + 1));
        Cell left = checkCellExists(grid, new Cell(x - 1, y));

        if (top != null) {
            if (!top.visited) {
                neighbours.add(top);
            }
        }
        if (right != null) {
            if (!right.visited) {
                neighbours.add(right);
            }
        }
        if (bottom != null) {
            if (!bottom.visited) {
                neighbours.add(bottom);
            }
        }
        if (left != null) {
            if (!left.visited) {
                neighbours.add(left);
            }
        }

        return neighbours;
    }

    public List<Cell> getAllNeighbours(List<Cell> grid) {
        List<Cell> neighbours = new ArrayList<Cell>();

        Cell top = checkCellExists(grid, new Cell(x, y - 1));
        Cell right = checkCellExists(grid, new Cell(x + 1, y));
        Cell bottom = checkCellExists(grid, new Cell(x, y + 1));
        Cell left = checkCellExists(grid, new Cell(x - 1, y));

        if (top != null) {
            neighbours.add(top);
        }
        if (right != null) {
            neighbours.add(right);
        }
        if (bottom != null) {
            neighbours.add(bottom);
        }
        if (left != null) {
            neighbours.add(left);
        }

        return neighbours;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + this.x;
        hash = 43 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Cell other = (Cell) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }
}
