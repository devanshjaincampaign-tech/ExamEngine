package exceptions;

public class TimeExpiredException extends Exception {

    private static final long serialVersionUID = 1L;

    public TimeExpiredException(String message) {
        super(message);
    }
}