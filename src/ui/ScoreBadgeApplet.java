package ui;

import javax.swing.JPanel;

public class ScoreBadgeApplet extends JPanel {
    private static final long serialVersionUID = 1L;

    public ScoreBadgeApplet() {
        add(new ScoreBadgePanel(10.0));
    }
}