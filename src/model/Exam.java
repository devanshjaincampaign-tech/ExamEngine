package model;

import java.util.ArrayList;
import java.util.Collections;

public class Exam {

    private final ArrayList<Question> questions;
    private final int durationSeconds;

    public Exam(ArrayList<Question> questions, int durationSeconds) {
        this.questions = new ArrayList<>(questions);
        this.durationSeconds = durationSeconds;
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

    public int getDurationSeconds() {
        return durationSeconds;
    }
}