package fileio;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.ExamResult;

public class ResultStore {
    private final File file;

    public ResultStore() {
        this.file = new File("data/results.dat");
    }

    public void saveResults(List<ExamResult> results) throws IOException {
        // 1. Create the 'data/' folder if it doesn't exist
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        // 2. Try-with-resources automatically closes the streams when done
        try (FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(results);
        }
    }

    @SuppressWarnings("unchecked")
    public List<ExamResult> loadResults() throws IOException, ClassNotFoundException {
        // 1. If file doesn't exist yet, return an empty list
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        // 2. Try-with-resources to open the input streams
        try (FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            List<ExamResult> results = (List<ExamResult>) ois.readObject();
            return results;
            // 3. Read the object from the stream, cast it to List<ExamResult>, and return it
            // [YOUR EXACT LINE OF CODE HERE]
        }
    }
}
