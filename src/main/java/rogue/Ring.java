package rogue;

public class Ring extends Magic implements Wearable {
    private boolean worn = false;

    /**
     * Default constructor.
     */
    public Ring() {
        super();
    }

    /**
     * Returns a string representing that the ring was worn.
     * @return String containing message for wearing the ring
     */
    @Override
    public String wear() {
        setWorn(true);
        return getDescription();
    }

    /**
     * Returns boolean of whether the clothing is being worn.
     * @return Boolean representing whether the clothing object is being worn
     */
    @Override
    public boolean isWorn() {
        return worn;
    }

    /**
     * Sets boolean of whether the clothing is being worn.
     * @param newWorn Boolean representing whether the clothing object is being worn
     */
    @Override
    public void setWorn(boolean newWorn) {
        worn = newWorn;
    }
}
