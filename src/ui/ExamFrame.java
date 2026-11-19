package ui;

import model.*;
import service.ExamService;
import fileio.QuestionBankLoader;
import exceptions.QuestionFormatException;
import timer.CountdownTimer;
import timer.TimerListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamFrame extends JFrame implements TimerListener {
    private static final long serialVersionUID = 1L;

    private final Student student;
    private final ExamService examService = new ExamService();
    private Exam exam;
    private final Map<String, String> userAnswers = new HashMap<>();

    private JLabel timerLabel;
    private JLabel questionLabel;
    private JPanel optionsPanel;
    private int currentQuestionIndex = 0;
    private CountdownTimer timer;

    public ExamFrame(Student student) {
        this.student = student;
        initMockExam();

        setTitle("Exam: " + exam.getTitle() + " - " + student.getName());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout(10, 10));

        // Header Panel with Timer
        JPanel header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        header.add(new JLabel("Student: " + student.getName() + " (" + student.getRollNo() + ")"), BorderLayout.WEST);
        timerLabel = new JLabel("Time: --:--");
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        header.add(timerLabel, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Body Panel for Question
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        centerPanel.add(questionLabel, BorderLayout.NORTH);

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        centerPanel.add(optionsPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Footer Panel for Navigation
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton prevBtn = new JButton("Previous");
        prevBtn.addActionListener(e -> navigate(-1));
        JButton nextBtn = new JButton("Next");
        nextBtn.addActionListener(e -> navigate(1));
        JButton submitBtn = new JButton("Submit Exam");
        submitBtn.addActionListener(e -> submit());

        footer.add(prevBtn);
        footer.add(nextBtn);
        footer.add(submitBtn);
        add(footer, BorderLayout.SOUTH);

        displayQuestion();

        // Start Countdown Timer
        timer = new CountdownTimer(exam.getDurationMinutes() * 60, this);
        timer.start();
    }

    private void initMockExam() {
        List<Question> questions = new ArrayList<>();
        File questionFile = findQuestionBank();

        if (questionFile != null) {
            try {
                questions = new QuestionBankLoader(questionFile.getPath()).loadQuestions();
            } catch (IOException | QuestionFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Could not load questions: " + e.getMessage(),
                        "Question Bank Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        if (questions.isEmpty()) {
            questions = java.util.List.of(
                    new MultipleChoiceQuestion("Q1", "What is Heap memory used for?", 5.0,
                            java.util.List.of("A) Local variables", "B) Object allocation",
                                    "C) Method metadata", "D) Instructions"), "B"),
                    new TrueFalseQuestion("Q2",
                            "RandomAccessFile record size is 56 bytes in our index.",
                            5.0, true)
            );
        }

        this.exam = new Exam("EXAM01", "Java OOP Exam", questions, 10);
    }

    private File findQuestionBank() {
        File[] candidates = {
                new File("data/questions.txt"),
                new File("../data/questions.txt"),
                new File("ExamEngine/data/questions.txt")
        };

        for (File candidate : candidates) {
            if (candidate.isFile()) return candidate;
        }
        return null;
    }

    private void displayQuestion() {
        Question q = exam.getQuestions().get(currentQuestionIndex);
        questionLabel.setText("Q" + (currentQuestionIndex + 1) + ": " + q.getPrompt());
        optionsPanel.removeAll();

        if (q instanceof MultipleChoiceQuestion mcq) {
            ButtonGroup bg = new ButtonGroup();
            for (String opt : mcq.getOptions()) {
                JRadioButton rb = new JRadioButton(opt);
                String optCode = opt.substring(0, 1);
                if (optCode.equalsIgnoreCase(userAnswers.get(q.getId()))) {
                    rb.setSelected(true);
                }
                rb.addActionListener(e -> userAnswers.put(q.getId(), optCode));
                bg.add(rb);
                optionsPanel.add(rb);
            }
        } else if (q instanceof TrueFalseQuestion) {
            ButtonGroup bg = new ButtonGroup();
            JRadioButton tRb = new JRadioButton("True");
            JRadioButton fRb = new JRadioButton("False");

            if ("true".equalsIgnoreCase(userAnswers.get(q.getId()))) tRb.setSelected(true);
            if ("false".equalsIgnoreCase(userAnswers.get(q.getId()))) fRb.setSelected(true);

            tRb.addActionListener(e -> userAnswers.put(q.getId(), "true"));
            fRb.addActionListener(e -> userAnswers.put(q.getId(), "false"));

            bg.add(tRb); bg.add(fRb);
            optionsPanel.add(tRb); optionsPanel.add(fRb);
        } else if (q instanceof ShortAnswerQuestion) {
            JTextField answerField = new JTextField(30);
            answerField.setMaximumSize(new Dimension(400, 30));
            String savedAnswer = userAnswers.get(q.getId());
            if (savedAnswer != null) answerField.setText(savedAnswer);
            answerField.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent event) {
                    userAnswers.put(q.getId(), answerField.getText());
                }
            });

            answerField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent event) {
                    userAnswers.put(q.getId(), answerField.getText());
                }

                @Override
                public void removeUpdate(DocumentEvent event) {
                    userAnswers.put(q.getId(), answerField.getText());
                }

                @Override
                public void changedUpdate(DocumentEvent event) {
                    userAnswers.put(q.getId(), answerField.getText());
                }
            });

            optionsPanel.add(new JLabel("Your answer:"));
            optionsPanel.add(answerField);
        }

        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    private void navigate(int dir) {
        int target = currentQuestionIndex + dir;
        if (target >= 0 && target < exam.getQuestions().size()) {
            currentQuestionIndex = target;
            displayQuestion();
        }
    }

    private void submit() {
        if (timer != null) timer.stop();
        try {
            ExamResult res = examService.submitExam(student, exam, userAnswers);
            dispose();
            new ResultFrame(res).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error submitting exam: " + e.getMessage());
        }
    }

    @Override
    public void onTick(int secondsRemaining) {
        int m = secondsRemaining / 60;
        int s = secondsRemaining % 60;
        SwingUtilities.invokeLater(() -> timerLabel.setText(String.format("Time: %02d:%02d", m, s)));
    }

    @Override
    public void onTimeUp() {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Time Expired! Submitting exam automatically.");
            submit();
        });
    }
}