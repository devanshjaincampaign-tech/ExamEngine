package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ExamResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private Student student;
    private double score;
    private Map<String, String> answers;
    private LocalDateTime timestamp;
    private String feedback;


    // 2. The Constructor
    public ExamResult(Student student, double score, Map<String, String> answers, LocalDateTime timestamp, String feedback) {
    this.student = student;
    this.score = score;
    this.answers = new HashMap<>(answers);
    this.timestamp = timestamp;
    this.feedback = feedback;
    }

    // 3. Getters
    public Student getStudent() { return student; }
    public double getScore() { return score; }
    public Map<String, String> getAnswers() { return answers; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getFeedback() { return feedback; }
}