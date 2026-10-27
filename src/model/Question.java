package model;

public abstract class Question implements Evaluatable {

    private static int count = 0;

    protected final int id;
    protected final String text;
    protected final int marks;

    public Question(String text) {
        this(text, 1);
    }

    public Question(String text, int marks) {
        this.id = ++count;
        this.text = text;
        this.marks = marks;
    }

    public abstract String getCorrectAnswer();

    @Override
    public int getMarks() {
        return marks;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}