package game.model;

public class Field {
    /**
     * The states of a field. 
     * Empty means that the value is still between 1-4
     * Blue means that blue set it to 4
     * Red means that red set it to 4
     */
    public enum State{
        EMPTY, BLUE, RED;
    }
    
    int value;
    State state;

    public Field() {
        this.value = 0;
        this.state = State.EMPTY;
    }
    
    /**
     * @return The value of the field 1-4
     */
    public int getValue() {
        return value;
    }

    /**
     * Return the state of the field
     * @return 
     */
    public State getState() {
        return state;
    }
}
