package service;

import model.*;
import java.util.Map;

public class GradingService {
    public double calculateScore(Exam exam, Map<String, String> userAnswers) {
        double score = 0.0;
        for (Question q : exam.getQuestions()) {
            String ans = userAnswers.get(q.getId());
            if (q.checkAnswer(ans)) {
                score += q.getMarks();
            }
        }
        return score;
    }

    public int calculateCorrectAnswerCount(Exam exam, Map<String, String> userAnswers) {
        int correct = 0;
        for (Question q : exam.getQuestions()) {
            if (q.checkAnswer(userAnswers.get(q.getId()))) {
                correct++;
            }
        }
        return correct;
    }
}