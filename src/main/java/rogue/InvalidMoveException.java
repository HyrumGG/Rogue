package rogue;

public class InvalidMoveException extends Exception {
    /**
     * Exception that occurs when a player move is invalid.
     */
    public InvalidMoveException() {
        super();
    }

    /**
     * Exception that occurs when a player move is invalid.
     * @param message A string representing the message to be added to the exception object
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
