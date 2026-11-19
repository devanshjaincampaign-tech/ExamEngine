package fileio;

import model.ExamResult;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        System.out.println("--- Starting File I/O Tests ---\n");

        // 1. Create sample test data using the 5-parameter ExamResult constructor
        List<ExamResult> sampleResults = new ArrayList<>();
        
        // Pass null/empty defaults for Student, Map, LocalDateTime, String if testing basic storage
        sampleResults.add(new ExamResult(null, 95.5, new HashMap<>(), LocalDateTime.now(), "PASSED"));
        sampleResults.add(new ExamResult(null, 88.0, new HashMap<>(), LocalDateTime.now(), "PASSED"));

        // 2. Test ResultStore (Object Serialization)
        ResultStore store = new ResultStore();
        try {
            System.out.println("1. Testing ResultStore (Object Serialization)...");
            store.saveResults(sampleResults);
            System.out.println("   --> Saved " + sampleResults.size() + " results to results.dat");

            List<ExamResult> loadedResults = store.loadResults();
            System.out.println("   --> Loaded " + loadedResults.size() + " results back from file.");
            for (ExamResult res : loadedResults) {
                System.out.println("       " + res);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("   ❌ ResultStore Error: " + e.getMessage());
        }

        System.out.println("\n--------------------------------------------------\n");

        // 3. Test ResultIndexFile (RandomAccessFile)
        ResultIndexFile indexFile = new ResultIndexFile();
        try {
            System.out.println("2. Testing ResultIndexFile (Fixed-size 56-byte Records)...");
            long currentTime = System.currentTimeMillis();
            
            // Testing 56-byte index directly with roll numbers
            indexFile.saveToIndex("CS-2024-001", 95.5, currentTime);
            System.out.println("   --> Indexed rollNo: CS-2024-001 at 56-byte boundary.");
            
            indexFile.saveToIndex("CS-2024-002", 88.0, currentTime);
            System.out.println("   --> Indexed rollNo: CS-2024-002 at 56-byte boundary.");
            
            System.out.println("   --> Index file written successfully to data/index.dat!");
        } catch (IOException e) {
            System.err.println("   ❌ ResultIndexFile Error: " + e.getMessage());
        }

        System.out.println("\n--- All Tests Completed! ---");
    }
}