package rogue;
public class NotEnoughDoorsException extends Exception {
    /**
     * Exception that occurs when a room does not have any doors.
     */
    public NotEnoughDoorsException() {
        super();
    }

    /**
     * Exception that occurs when a room does not have any doors.
     * @param message A string representing the message to be added to the exception object
     */
    public NotEnoughDoorsException(String message) {
        super(message);
    }
}
