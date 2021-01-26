package rogue;

public class SmallFood extends Food implements Tossable {

    /**
     * Default constructor.
     */
    public SmallFood() {
        super();
    }

    /**
     * Returns a string representing that the food was eaten.
     * @return String containing message for eating food
     */
    @Override
    public String eat() {
        return getDescription().split(":")[0];
    }

    /**
     * Returns a string representing that the food was tossed.
     * @return String containing message for tossing the item
     */
    @Override
    public String toss() {
        String[] arr = getDescription().split(": ");
        if (arr.length > 1) {
            return arr[1];
        }
        return getName() + " is tossed";
    }
}
