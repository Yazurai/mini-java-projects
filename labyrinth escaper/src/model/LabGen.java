package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.util.Random;

public class LabGen {
	private final int size;
	private final List<Cell> grid;
	private final List<Cell> reachables = new ArrayList<Cell>();
	private Cell current;

	public LabGen(List<Cell> grid, int size) {
            this.size = size;
            this.grid = grid;
            current = grid.get(0);
            while(!grid.parallelStream().allMatch(c -> c.isVisited())){
                carve();
            } 
            if(size > 2){
                int count = 15; 
                Random rand = new Random();
                while(count > 0){
                    int random = rand.nextInt(grid.size());
                    Cell c = grid.get(random);
                    if(isInsideCell(c)){
                        c.removeSingleWall(grid);
                        count--;
                    }
                }
            }
	}
	
        private boolean isInsideCell(Cell c){
            return 0 < c.getX() &&  c.getX() < size-1 && 0 < c.getY() &&  c.getY() < size-1;
        }
        
	private void carve() {
            current.setVisited(true);

            List<Cell> neighs = current.getUnvisitedNeighboursList(grid);
            reachables.addAll(neighs);
            Collections.shuffle(reachables);

            current = reachables.get(0);

            List<Cell> inNeighs = current.getAllNeighbours(grid);
            inNeighs.removeIf(c -> !c.isVisited());

            if (!inNeighs.isEmpty()) {
                Collections.shuffle(inNeighs);
                current.removeWalls(inNeighs.get(0));
            }

            reachables.removeIf(c -> c.isVisited());
	}
}