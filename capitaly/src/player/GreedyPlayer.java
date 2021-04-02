package player;

import game.environment.Field;

public class GreedyPlayer extends Player {
    /**
     * Creates a player that is greedy
     * @param name The name of the player
     */
    public GreedyPlayer(String name) {
        super(name);
    }
    
    /**
     * Gets called whenever the player steps onto a field.
     * Buys everything if it's within budget
     * @param field The field the player stepped on
     */
    @Override
    public void step(Field field){
        int amount = field.onPlayerHit(this);
        money += amount;
        if(0 < field.getCost(this) && field.getCost(this) <= money){
            money -= field.getCost(this);
            field.buy(this);
        }
    }
}
