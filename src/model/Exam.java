package model;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Exam {

    private final String id;
    private final String title;
    private final ArrayList<Question> questions;
    private final int durationMinutes;

    public Exam(String id, String title, List<Question> questions, int durationMinutes) {
        if (questions == null || durationMinutes <= 0) throw new IllegalArgumentException("Invalid exam configuration.");
        this.id = id;
        this.title = title;
        this.questions = new ArrayList<>(questions);
        this.durationMinutes = durationMinutes;
    }

    public void shuffle() {
        Collections.shuffle(questions);
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }

    public int size() {
        return questions.size();
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public List<Question> getQuestions() {
        return Collections.unmodifiableList(questions);
    }

    public String getTitle() {
        return title;
    }

    public double evaluate() {
        double total = 0;
        for (Question question : questions) total += question.getMarks();
        return total;
    }
}