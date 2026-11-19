package model;

public class MultipleChoiceQuestion extends Question {

    private final String[] options;
    private final String correctAnswer;

    public MultipleChoiceQuestion(String id, String text, double marks,
                                  java.util.List<String> options, String correctAnswer) {
        this(id, text, options.toArray(new String[0]), correctAnswer, marks);
    }

    public MultipleChoiceQuestion(String id, String text, String[] options,
                                  String correctAnswer, double marks) {
        super(id, text, marks);
        if (options == null || options.length == 0 || correctAnswer == null) {
            throw new IllegalArgumentException("MCQ options and correct answer are required.");
        }
        this.options = options.clone();
        this.correctAnswer = correctAnswer.trim();
    }

    @Override
    public boolean validateAnswer(String answer) {
        return answer != null && answer.trim().equalsIgnoreCase(correctAnswer);
    }

    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getOptions() {
        return options.clone();
    }
}