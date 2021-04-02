package player;

import game.environment.Field;

public abstract class Player {
    protected int money;
    protected int location;
    protected String name;

    /**
     * Creates a player (used for subclasses to call)
     * @param name The name of the player
     */
    public Player(String name) {
        this.money = 10000;
        this.location = 0;
        this.name = name;
    }

    /**
     * @return The name of the player
     */
    public String getName(){
        return name;
    }
    
    /**
     * Returns the location of where the player is at in the playboard (starts from 0)
     * @return Location index
     */
    public int getLocation() {
        return location;
    }
    
    /**
     * Returns the amount of money the player has(used for testing).
     * @return the current money of the player
     */
    public int getMoney() {
        return money;
    }
    
    /**
     * Moves the player a given number of fields(the playfield wraps around)
     * @param fields The amount of fields to move
     * @param total The total number of fields in the playfield
     */
    public void move(int fields, int total){
        location += fields;
        location %= total;
    }
    
    /**
     * Pay another player a set amount
     * @param p The player to pay
     * @param value The amount to pay the other player
     */
    public void payPlayer(Player p, int value){
        if(money < value){
            p.money += money;
            money = -1;
        } else {
            p.money += value;
            money -= value;
        }
    }
    
    /**
     * This gets called every time the player steps onto a new field
     * @param field The field the player stepped on
     */
    public abstract void step(Field field);
    
    /**
     * @return Whether the player is alive (has more or equal money to 0)
     */
    public boolean isAlive(){
        return money >= 0;
    }
}
