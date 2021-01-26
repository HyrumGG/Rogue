package rogue;

public class Food extends Item implements Edible {

    /**
     * Default constructor.
     */
    public Food() {
        super();
    }

    /**
     * Returns a string representing that the food was eaten.
     * @return String containing message for eating food
     */
    @Override
    public String eat() {
        return getDescription();
    }
}
