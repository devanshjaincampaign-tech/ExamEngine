package ui;

import java.awt.*;
import javax.swing.*;

public class ScoreBadgePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final double score;
    private final double maxScore;

    public ScoreBadgePanel(double score) {
        this(score, 10.0);
    }

    public ScoreBadgePanel(double score, double maxScore) {
        this.score = score;
        this.maxScore = maxScore;
        setPreferredSize(new Dimension(100, 40));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        boolean passed = maxScore > 0
                ? score >= maxScore * 0.5
                : score >= 5.0;
        if (passed) {
            g2.setColor(new Color(46, 204, 113)); // Green
        } else {
            g2.setColor(new Color(231, 76, 60)); // Red
        }

        g2.fillRoundRect(5, 5, 90, 30, 15, 15);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.drawString(passed ? "PASSED" : "FAILED", 25, 25);
    }
}