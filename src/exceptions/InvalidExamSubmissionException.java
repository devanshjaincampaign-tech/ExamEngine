package exceptions;

public class InvalidExamSubmissionException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidExamSubmissionException(String message) {
        super(message);
    }
}