package model;

public class TrueFalseQuestion extends Question {

    private final boolean correct;

    public TrueFalseQuestion(String text, boolean correct, int marks) {
        super(text, marks);
        this.correct = correct;
    }

    @Override
    public boolean validateAnswer(String answer) {
        return answer != null && answer.trim().equalsIgnoreCase(String.valueOf(correct));
    }

    @Override
    public String getCorrectAnswer() {
        return String.valueOf(correct);
    }
}