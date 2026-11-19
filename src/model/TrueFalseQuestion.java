package model;

public class TrueFalseQuestion extends Question {

    private final boolean correct;

    public TrueFalseQuestion(String id, String text, double marks, boolean correct) {
        super(id, text, marks);
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