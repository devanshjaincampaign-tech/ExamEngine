package ui;

import model.ExamResult;
import javax.swing.*;
import java.awt.*;

public class ResultFrame extends JFrame {
    private static final long serialVersionUID = 1L;


    public ResultFrame(ExamResult result) {
        setTitle("Exam Summary & Results");
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen

        // Main Panel layout
        JPanel panel = new JPanel(new GridLayout(6, 1, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // 1. Student Details Label
        String studentInfo = (result.getStudent() != null)
                ? result.getStudent().getName() + " (" + result.getStudent().getRollNo() + ")"
                : "Unknown Student";
        JLabel studentLabel = new JLabel("Student: " + studentInfo);
        studentLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(studentLabel);

        // 2. Score Label
        String scoreText = String.format("Final Score: %.1f / %.1f",
                result.getScore(), result.getMaxScore());
        JLabel scoreLabel = new JLabel(scoreText);
        scoreLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(scoreLabel);

        // 3. Status Label
        JLabel statusLabel = new JLabel(String.format("Status: %s (%.1f%%)",
                result.getStatus(), result.getPercentage()));
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(statusLabel);

        JLabel correctLabel = new JLabel(String.format("Correct Answers: %d / %d",
                result.getCorrectAnswers(), result.getTotalQuestions()));
        panel.add(correctLabel);

        // 4. Custom UI Component: Score Badge Painting
        panel.add(new ScoreBadgePanel(result.getScore(), result.getMaxScore()));

        // 5. Close / Dismiss Button
        JButton closeBtn = new JButton("Close Summary");
        closeBtn.addActionListener(e -> dispose());
        panel.add(closeBtn);

        add(panel);
    }
}