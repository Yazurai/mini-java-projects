package game.view;

public enum TableSize {
    SMALL(3),
    MEDIUM(5),
    LARGE(7);
    
    private final int size;
    
    TableSize(int size){
        this.size = size;
    }
    
    /**
     * @return The size of the game area/table.
     */
    public int getSize(){
        return size;
    }
    
    /**
     * @return The width of the game table in pixels.
     */
    public int getWidth(){
        return size*80;
    }
    
    /**
     * @return The height of the game table in pixels (plus the scoreboard).
     */
    public int getHeight(){
        return size*80+50;
    }
}
