package exceptions;

public class QuestionFormatException extends Exception {

    private static final long serialVersionUID = 1L;

    private final int lineNumber;

    public QuestionFormatException(int lineNumber, String message) {
        super("Line " + lineNumber + " of questions.txt: " + message);
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }
}