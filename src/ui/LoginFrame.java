package ui;

import model.Student;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;


    private JTextField rollNoField;
    private JTextField nameField;

    public LoginFrame() {
        // Window setup
        setTitle("Exam Engine System - Login");
        setSize(420, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center screen
        setResizable(false);

        // Main Container Panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        // Header Title
        JLabel titleLabel = new JLabel("Online Examination Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(new Color(41, 128, 185));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Form Grid (Inputs)
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 15));

        JLabel rollLabel = new JLabel("Roll Number:");
        rollLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        rollNoField = new JTextField();
        rollNoField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nameField = new JTextField();
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        formPanel.add(rollLabel);
        formPanel.add(rollNoField);
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Footer Action Panel (Buttons)
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton clearBtn = new JButton("Clear");
        clearBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        clearBtn.addActionListener((ActionEvent e) -> clearFields());

        JButton startExamBtn = new JButton("Start Exam 🚀");
        startExamBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        startExamBtn.setBackground(new Color(46, 204, 113));
        startExamBtn.setForeground(Color.WHITE);
        startExamBtn.setFocusPainted(false);
        startExamBtn.addActionListener((ActionEvent e) -> handleLogin());

        actionPanel.add(clearBtn);
        actionPanel.add(startExamBtn);

        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void handleLogin() {
        String rollNo = rollNoField.getText().trim();
        String name = nameField.getText().trim();

        // Basic Input Validation
        if (rollNo.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Please enter both your Roll Number and Name to proceed.",
                "Input Validation Error",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // Instantiate Student Model in Heap
        Student student = new Student(name, rollNo);

        // Close Login Window & Launch Exam GUI
        dispose();
        SwingUtilities.invokeLater(() -> new ExamFrame(student).setVisible(true));
    }

    private void clearFields() {
        rollNoField.setText("");
        nameField.setText("");
        rollNoField.requestFocus();
    }

    // Direct Main launcher for testing Swing app execution
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}