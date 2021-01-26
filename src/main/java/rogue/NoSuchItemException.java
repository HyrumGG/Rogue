package rogue;
public class NoSuchItemException extends Exception {
    /**
     * Exception that occurs when an item id does not exist in the games item list.
     */
    public NoSuchItemException() {
        super();
    }

    /**
     * Exception that occurs when an item id does not exist in the games item list.
     * @param message A string representing the message to be added to the exception object.
     */
    public NoSuchItemException(String message) {
        super(message);
    }
}
