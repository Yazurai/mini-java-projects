package game.environment;

import player.Player;

public class ServiceField extends Field{
    private int fee;

    /**
     * Creates a service field where the player has to pay
     * @param fee The amount of money the player has to pay
     * @throws IllegalArgumentException When the given fee amount is negative
     */
    public ServiceField(int fee) throws IllegalArgumentException {
        if(fee < 0){
            throw new IllegalArgumentException();
        } else {
            this.fee = -fee;
        }
    }
    
    /**
     * The player has to pay a given amount when stepping onto the field
     * @param p The player that stepped onto the field
     * @return The amount the player pays when stepping onto this field
     */
    @Override
    public int onPlayerHit(Player p){
        return fee;
    }
    
    @Override
    public String toString(){
        return "Ser";
    }
}
