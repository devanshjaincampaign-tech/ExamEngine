package fileio;

import model.*;
import exceptions.QuestionFormatException;

import java.io.*;
import java.util.*;

public class QuestionBankLoader {
    private final File file;

    public QuestionBankLoader(String filePath) {
        this.file = new File(filePath);
    }

    public List<Question> loadQuestions() throws IOException, QuestionFormatException {
        List<Question> list = new ArrayList<>();
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty() || line.trim().startsWith("#")) continue;

                // Format: TYPE|ID|PROMPT|MARKS|EXTRA_INFO|CORRECT
                String[] parts = line.split("\\|", -1);
                if (parts.length < 5) {
                    throw new QuestionFormatException(lineNumber, "Invalid format or missing parameters");
                }

                String type = parts[0].trim().toUpperCase();
                String id = parts[1].trim();
                String prompt = parts[2].trim();
                if (id.isEmpty() || prompt.isEmpty()) {
                    throw new QuestionFormatException(lineNumber, "Question id and prompt are required");
                }
                double marks;
                try {
                    marks = Double.parseDouble(parts[3].trim());
                } catch (NumberFormatException e) {
                    throw new QuestionFormatException(lineNumber, "Marks must be a number");
                }

                switch (type) {
                    case "MCQ" -> {
                        if (parts.length < 6 || parts[4].trim().isEmpty() || parts[5].trim().isEmpty()) {
                            throw new QuestionFormatException(lineNumber, "MCQ requires options and a correct answer");
                        }
                        String[] opts = parts[4].split(",");
                        String correct = parts[5].trim();
                        list.add(new MultipleChoiceQuestion(id, prompt, marks, Arrays.asList(opts), correct));
                    }
                    case "TF" -> {
                        String value = parts[4].trim();
                        if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
                            throw new QuestionFormatException(lineNumber, "TF answer must be true or false");
                        }
                        boolean correct = Boolean.parseBoolean(value);
                        list.add(new TrueFalseQuestion(id, prompt, marks, correct));
                    }
                    case "SA" -> {
                        String expected = parts[4].trim();
                        String[] acceptedAnswers = Arrays.stream(expected.split(","))
                                .map(String::trim)
                                .filter(answer -> !answer.isEmpty())
                                .toArray(String[]::new);
                        if (acceptedAnswers.length == 0) {
                            throw new QuestionFormatException(lineNumber, "SA answer is required");
                        }
                        list.add(new ShortAnswerQuestion(id, prompt, marks, acceptedAnswers));
                    }
                    default -> throw new QuestionFormatException(lineNumber, "Unknown question type: " + type);
                }
            }
        }
        return list;
    }
}