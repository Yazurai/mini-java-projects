package game.model;

import game.model.Field.State;

public class Model {
    private int size;
    private Field[][] table;
    private int bluePoint;
    private int redPoint;
    private boolean currentPlayer;
    private int emptyCount;

    public Model(int size) {
        setup(size);
    }
    
    /**
     * Sets up a new game configuration with the given game area
     * @param size The game area side size
     */
    public void setup(int size){
        this.size = size;
        emptyCount = size*size;
        table = new Field[size][size]; 
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                table[i][j] = new Field();
            }
        }
        bluePoint = 0;
        redPoint = 0;
        currentPlayer = true;
    }

    /**
     * Tries to "click" onto a field with the current player, if it's a legal click
     * then update the adjacent fields and switch the current player.
     * @param x The horizontal index
     * @param y The vertical index
     */
    public void stepOnField(int x, int y){
        if(increaseField(x,y)){
            increaseField(x+1,y);
            increaseField(x-1,y);
            increaseField(x,y+1);
            increaseField(x,y-1);
            currentPlayer = !currentPlayer;
        }
    }
    
    /**
     * Increases a single field if the given coordinates are legal.
     * After increasing updates the state of the field.
     * @param x The horizontal index
     * @param y The vertical index
     * @return if there was an update to the field(it was increased)
     */
    private boolean increaseField(int x, int y) {
        if(checkValidCoord(x, y)){
            if(table[x][y].value < 4){
                table[x][y].value++;
                if(table[x][y].value == 4){
                    emptyCount--;
                    if(currentPlayer){
                        table[x][y].state = State.BLUE;
                        bluePoint++;
                    } else {
                        table[x][y].state = State.RED;
                        redPoint++;
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks whether the given coordinates are legal indexes in the grid
     * @param x The horizontal index
     * @param y The vertical index
     * @return if coordinates are legal
     */
    private boolean checkValidCoord(int x, int y){
        return 0 <= x && x < size
                && 0 <= y && y < size;
    }

    /**
     * @return The size of the game
     */
    public int getSize(){
        return size;
    }

    /**
     * @return a field in the game area
     */
    public Field getField(int row, int column) {
        return table[row][column];
    }
    
    /**
     * @return the current player, true for blue and false for red
     */
    public boolean getPlayer() {
        return currentPlayer;
    }
    
    /**
     * @return blue points
     */
    public int getBluePoint(){
        return bluePoint;
    }
    
    /**
     * @return red points
     */
    public int getRedPoint(){
        return redPoint;
    }
    
    /**
     * @return the number of remaining field which are not 4
     */
    public int getRemaining(){
        return emptyCount;
    }
}
