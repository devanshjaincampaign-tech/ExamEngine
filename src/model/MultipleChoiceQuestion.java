package model;

public class MultipleChoiceQuestion extends Question {

    private final String[] options;
    private final int correctIndex;

    public MultipleChoiceQuestion(String text, String[] options, int correctIndex, int marks) {
        super(text, marks);
        this.options = options;
        this.correctIndex = correctIndex;
    }

    @Override
    public boolean validateAnswer(String answer) {
        return answer != null && answer.trim().equals(String.valueOf(correctIndex));
    }

    @Override
    public String getCorrectAnswer() {
        return options[correctIndex];
    }

    public String[] getOptions() {
        return options;
    }
}