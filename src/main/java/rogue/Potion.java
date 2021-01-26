package rogue;

public class Potion extends Magic implements Edible, Tossable {

    /**
     * Default constructor.
     */
    public Potion() {
        super();
    }

    /**
     * Returns a string representing that the potion was consumed.
     * @return String containing message for consuming the potion
     */
    @Override
    public String eat() {
        return getDescription().split(":")[0];
    }

    /**
     * Returns a string representing that the potion was tossed.
     * @return String containing message for tossing the potion
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
