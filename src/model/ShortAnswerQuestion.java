package model;

public class ShortAnswerQuestion extends Question {

    private final String[] acceptedAnswers;

    public ShortAnswerQuestion(String text, String[] acceptedAnswers, int marks) {
        super(text, marks);
        this.acceptedAnswers = acceptedAnswers;
    }

    @Override
    public boolean validateAnswer(String answer) {
        if (answer == null) {
            return false;
        }
        String given = answer.trim();
        for (String accepted : acceptedAnswers) {
            if (given.equalsIgnoreCase(accepted.trim())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getCorrectAnswer() {
        return acceptedAnswers[0];
    }
}