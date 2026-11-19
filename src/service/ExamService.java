package service;

import model.*;
import fileio.*;
import exceptions.InvalidExamSubmissionException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ExamService {
    private final ResultStore store = new ResultStore();
    private final ResultIndexFile indexFile = new ResultIndexFile();
    private final GradingService gradingService = new GradingService();

    public ExamResult submitExam(Student student, Exam exam, Map<String, String> answers)
            throws InvalidExamSubmissionException, IOException, ClassNotFoundException {

        if (student == null || exam == null || answers == null) {
            throw new InvalidExamSubmissionException("Submission contains null fields.");
        }

        double score = gradingService.calculateScore(exam, answers);
        double maxScore = exam.evaluate();
        String status = (score >= (maxScore * 0.5)) ? "PASSED" : "FAILED";

        int correctAnswers = gradingService.calculateCorrectAnswerCount(exam, answers);
        ExamResult result = new ExamResult(
                student,
                score,
                maxScore,
                answers,
                LocalDateTime.now(),
                status,
                correctAnswers,
                exam.size()
        );

        // 1. Save serialized object
        List<ExamResult> currentResults = store.loadResults();
        currentResults.add(result);
        store.saveResults(currentResults);

        // 2. Save 56-byte binary index record
        indexFile.saveToIndex(student.getRollNo(), score, System.currentTimeMillis());

        return result;
    }

    public List<ExamResult> fetchAllResults() throws IOException, ClassNotFoundException {
        return store.loadResults();
    }
}