package ui;

import fileio.ResultStore;
import model.ExamResult;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistoryFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public HistoryFrame() {
        setTitle("Exam History");
        setSize(500, 300);
        setLocationRelativeTo(null);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        try {
            List<ExamResult> list = new ResultStore().loadResults();
            for (ExamResult r : list) {
                listModel.addElement(r.toString());
            }
        } catch (Exception e) {
            listModel.addElement("Error loading history.");
        }

        JList<String> jList = new JList<>(listModel);
        add(new JScrollPane(jList), BorderLayout.CENTER);
    }
}