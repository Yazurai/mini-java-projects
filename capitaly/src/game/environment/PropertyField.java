package game.environment;

import player.Player;

public class PropertyField extends Field {
    /**
     * An enum that hold the different states of a property
     */
    public enum PropertyState {
        EMPTY(0,0), BOUGHT(1000,500), SINGLE(4000,2000);
        
        private final int buildCost;
        private final int stayCost;
        private static final PropertyState[] vals = values();

        private PropertyState(int buildCost, int stayCost) {
            this.buildCost = buildCost;
            this.stayCost = stayCost;
        }
        
        /**
         * Gets the next state in line
         * @return The state that is next in the list of states
         */
        public PropertyState next() {
            return vals[(this.ordinal()+1) % vals.length];
        }

        /**
         * @return The building cost to build the current state
         */
        public int getBuildCost() {
            return this.buildCost;
        }

        /**
         * @return The amount it costs to stay at the current state
         */
        public int getStayCost() {
            return this.stayCost;
        }
    };
    private PropertyState state;
    private Player owner;

    /**
     * Creates a unoccupied property field
     */
    public PropertyField() {
        this.state = PropertyState.EMPTY;
        owner = null;
    }

    /**
     * @return The current state of the property
     */
    public PropertyState getState() {
        return state;
    }

    /**
     * Returns the player that owns the property (returns a null if there are no owners)
     * @return The player that is the owner
     */
    public Player getOwner() {
        return owner;
    }
    
    /**
     * Resets the owner to null if the given player matches with the current owner
     * @param p The player to check the current owner against
     */
    public void reset(Player p){
        if(p.equals(owner)){
            state = PropertyState.EMPTY;
            owner = null;
        }
    }
    
    /**
     * Sends a buy request to the property. If the property is without an owner, the player
     * will become the new owner, otherwise only the owner player can upgrade the property
     * @param p The player that wants to buy/upgrade the property
     */
    @Override
    public void buy(Player p){
        if(state == PropertyState.EMPTY){
            state = state.next();
            owner = p;
        } else if(state == PropertyState.BOUGHT){
            if(p.equals(owner)){
                state = state.next();
            }
        }
    }
    
    /**
     * Returns the cost of buying the property or upgrading it if applicable to the given player.
     * (if not applicable then the return value is 0)
     * @param p The player looking to buy
     * @return The amount of money buying/upgrading would cost
     */
    @Override
    public int getCost(Player p){
        if(p.equals(owner)){
            if(state != PropertyState.SINGLE){
                return state.next().getBuildCost();
            } else {
                return 0;
            }
        } else {
            if(state == PropertyState.EMPTY){
                return state.next().getBuildCost();
            } else {
                return 0;
            }
        }
    }
    
    /**
     * If the property has an owner and the given player is not the owner, then the player
     * pays an amount to the owner
     * @param p The player that stepped onto the property
     * @return The amount the player receives or pays when stepping onto this field
     */
    @Override
    public int onPlayerHit(Player p){
        if(!p.equals(owner)){
            if(owner != null){
                p.payPlayer(owner, state.getStayCost());
            }
        }
        return 0;
    }
    
    @Override
    public String toString(){
        return "Res";
    }
}
