import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A simple CSV reader Class that reads data from a CSV file.
 */
public class CSVReader {

    private String filePath;
    private String delimiter;

    /**
     * Constructor for CSVReader.
     * @param filePath file path to the CSV file.
     */
    public CSVReader(String filePath) {
        this(filePath, ",");  // default: comma
    }

    /**
     * Constructor for CSVReader with custom delimiter.
     * @param filePath file path to the CSV file.
     * @param delimiter delimiter used in the CSV file.
     */
    public CSVReader(String filePath, String delimiter) {
        this.filePath = filePath;
        this.delimiter = delimiter;
    }

    /**
     * Reads a specific column from the CSV file and returns unique values as a Set.
     * @param columnIndex index of the column to read.
     * @return A Set of unique values from the specified column.
     * @throws IOException
     */
    public HashSet<String> readColumnAsSet(int columnIndex) throws IOException {
        HashSet<String> result = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
            line = br.readLine();
            HashSet<String> existingCodes = new HashSet<>();

            while (line != null) {
                String[] parts = line.split(delimiter);

                if (columnIndex < parts.length) {
                    if (!existingCodes.contains(parts[columnIndex].trim())) {
                        result.add(parts[columnIndex].trim());
                        existingCodes.add(parts[columnIndex].trim());
                    }
                }

                line = br.readLine();
            }
        }

        return result;
    }

    /**
     * Reads all rows from the CSV file.
     * @return A List of String arrays, each representing a row in the CSV file.
     * @throws IOException
     */
    public List<String[]> readRows() throws IOException {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
            line = br.readLine();

            while (line != null) {
                String[] parts = line.split(delimiter);
                rows.add(parts);
                line = br.readLine();
            }
        }
        return rows;
    }
}

