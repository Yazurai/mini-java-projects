package game.environment;

import player.Player;

public abstract class Field {
    /**
     * This gets called whenever a given player steps onto a field
     * @param p The player that stepped onto the field
     * @return The amount the player receives or pays when stepping onto this field
     */
    public int onPlayerHit(Player p){
        return 0;
    }
    
    /**
     * Returns the cost if the given player wants to buy the current field.
     * If the player can't buy the field, the return value is 0
     * 
     * @param p The player that stepped onto the field
     * @return The cost of the field to the given player if applicable
     */
    public int getCost(Player p){
        return 0;
    }
    
    /**
     * Gets called when the given player want to purchase or upgrade the field.
     * Unauthorized buy requests should be checked
     * @param p The player that stepped onto the field
     */
    public void buy(Player p){};
}
