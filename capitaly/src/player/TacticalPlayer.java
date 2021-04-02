package player;

import game.environment.Field;

public class TacticalPlayer extends Player {
    private boolean shouldBuy;

    /**
     * Creates a player that is tactical
     * @param name The name of the player
     */
    public TacticalPlayer(String name) {
        super(name);
        this.shouldBuy = true;
    }
        
    /**
     * Gets called whenever the player steps onto a field.
     * Buys every second thing
     * @param field The field the player stepped on
     */
    @Override
    public void step(Field field){
        int amount = field.onPlayerHit(this);
        money += amount;
        if(0 < field.getCost(this) && field.getCost(this) <= money) {
            if(shouldBuy){
                money -= field.getCost(this);
                field.buy(this);
            } 
            shouldBuy = !shouldBuy;
        }
    }
}
