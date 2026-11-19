package model;

import java.util.Locale;

public class ShortAnswerQuestion extends Question {

    private final String[] acceptedAnswers;

    public ShortAnswerQuestion(String id, String text, double marks, String expectedAnswer) {
        this(id, text, marks, new String[] { expectedAnswer });
    }

    public ShortAnswerQuestion(String id, String text, double marks, String[] acceptedAnswers) {
        super(id, text, marks);
        this.acceptedAnswers = acceptedAnswers;
    }

    @Override
    public boolean validateAnswer(String answer) {
        if (answer == null) {
            return false;
        }
        String given = normalize(answer);
        for (String accepted : acceptedAnswers) {
            if (given.equals(normalize(accepted))) {
                return true;
            }
        }
        return false;
    }

    private String normalize(String answer) {
        return answer == null
                ? ""
                : answer.trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9 ]", "");
    }

    @Override
    public String getCorrectAnswer() {
        return acceptedAnswers[0];
    }
}