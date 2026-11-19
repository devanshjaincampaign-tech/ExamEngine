package model;

public abstract class Question implements Evaluatable {

    protected final String id;
    protected final String text;
    protected final double marks;

    public Question(String id, String text) {
        this(id, text, 1);
    }

    public Question(String id, String text, double marks) {
        if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("Question id is required.");
        if (text == null || text.trim().isEmpty()) throw new IllegalArgumentException("Question text is required.");
        if (marks < 0) throw new IllegalArgumentException("Question marks cannot be negative.");
        this.id = id.trim();
        this.text = text;
        this.marks = marks;
    }

    public abstract String getCorrectAnswer();

    @Override
    public double getMarks() {
        return marks;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getPrompt() {
        return text;
    }

    public boolean checkAnswer(String answer) {
        return validateAnswer(answer);
    }
}