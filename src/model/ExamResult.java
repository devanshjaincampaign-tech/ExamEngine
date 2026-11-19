package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ExamResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private Student student;
    private double score;
    private HashMap<String, String> answers;
    private LocalDateTime timestamp;
    private String feedback;
    private double maxScore;
    private int correctAnswers;
    private int totalQuestions;


    // 2. The Constructor
    public ExamResult(Student student, double score, Map<String, String> answers, LocalDateTime timestamp, String feedback) {
        this(student, score, 10.0, answers, timestamp, feedback, 0, 0);
    }

    public ExamResult(Student student, double score, double maxScore,
                      Map<String, String> answers, LocalDateTime timestamp,
                      String feedback, int correctAnswers, int totalQuestions) {
        this.student = student;
        this.score = score;
        this.maxScore = maxScore;
        this.answers = answers == null ? new HashMap<>() : new HashMap<>(answers);
        this.timestamp = timestamp;
        this.feedback = feedback;
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
    }

    // 3. Getters
    public Student getStudent() { return student; }
    public double getScore() { return score; }
    public Map<String, String> getAnswers() { return answers; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getFeedback() { return feedback; }
    public String getStatus() { return feedback; }
    public double getMaxScore() { return maxScore; }
    public int getCorrectAnswers() { return correctAnswers; }
    public int getTotalQuestions() { return totalQuestions; }
    public double getPercentage() {
        if (maxScore <= 0) return 0;
        return (score / maxScore) * 100.0;
    }

    @Override
    public String toString() {
        return student + " - " + score + " (" + feedback + ") at " + timestamp;
    }
}