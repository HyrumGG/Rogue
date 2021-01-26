package rogue;

public interface Wearable {
    /**
     * Returns a string representing that the clothing was worn.
     * @return String containing message for wearing the clothing
     */
    String wear();

    /**
     * Returns boolean of whether the clothing is being worn.
     * @return Boolean representing whether the clothing object is being worn
     */
    boolean isWorn();

    /**
     * Sets boolean of whether the clothing is being worn.
     * @param newWorn Boolean representing whether the clothing object is being worn
     */
    void setWorn(boolean newWorn);
}
