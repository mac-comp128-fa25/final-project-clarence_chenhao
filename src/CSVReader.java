import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CSVReader {

    private String filePath;
    private String delimiter;

    public CSVReader(String filePath) {
        this(filePath, ",");  // default: comma
    }

    public CSVReader(String filePath, String delimiter) {
        this.filePath = filePath;
        this.delimiter = delimiter;
    }


    public Set<String> readColumnAsSet(int columnIndex) throws IOException {
        Set<String> result = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
            line = br.readLine();

            while (line != null) {
                String[] parts = line.split(delimiter);

                if (columnIndex < parts.length) {
                    result.add(parts[columnIndex].trim());
                }

                line = br.readLine();
            }
        }

        return result;
    }

    public HashSet<AirportNode> readAirportsAsSet (int columnIndex) throws IOException {
        HashSet<AirportNode> result = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // skip header
            line = br.readLine();
            HashSet<String> existingCodes = new HashSet<>();

            while (line != null) {
                String[] parts = line.split(delimiter);
            
                if (columnIndex < parts.length) {
                    if (!existingCodes.contains(parts[columnIndex].trim())) {
                        result.add(new AirportNode(parts[columnIndex].trim()));
                        existingCodes.add(parts[columnIndex].trim());
                    }
                }

                line = br.readLine();
            }
        }
        return result;
    }

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

