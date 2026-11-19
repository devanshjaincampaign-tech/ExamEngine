package fileio;

import java.io.IOException;
import java.io.File;
import java.io.RandomAccessFile;

public class ResultIndexFile {
    private static final int RECORD_SIZE = 56;
    private static final String FILE_PATH = "data/index.dat";

    public void saveToIndex(String rollNo, double score, long timestamp) throws IOException {
        File file = new File(FILE_PATH);
        File parent = file.getParentFile();
        if (parent != null) parent.mkdirs();
        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {

            raf.seek(raf.length());

            // 2. Pad the roll number to exactly 20 characters
            String safeRollNo = rollNo == null ? "" : rollNo;
            String paddedRoll = String.format("%-20s", safeRollNo).substring(0, 20);

            // 3. Write the data to the disk
            raf.writeChars(paddedRoll); // 40 bytes (20 chars * 2 bytes)
            raf.writeDouble(score);     // 8 bytes
            raf.writeLong(timestamp);   // 8 bytes
        }
    }
}
