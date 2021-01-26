package rogue;
public class ImpossiblePositionException extends Exception {
    /**
     * Exception that occurs when an item position is invalid/overlaps.
     */
    public ImpossiblePositionException() {
        super();
    }

    /**
     * Exception that occurs when an item position is invalid/overlaps.
     * @param message A string representing the message to be added to the exception object
     */
    public ImpossiblePositionException(String message) {
        super(message);
    }
}
