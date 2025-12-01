import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class FlightGraph {
    //private HashSet<AirportNode> nodesSet;
    private HashSet<String> codeSet;
    private int numNodes;
    private int numEdges;
    private int[][] adjacencyMatrix;
    private String[] indexToCode;
    private HashMap<String, Integer> codeToIndexMap;
    private CSVReader csvReader;

    public FlightGraph(String filePath) throws IOException {
        this.csvReader = new CSVReader(filePath);
        this.codeSet = csvReader.readColumnAsSet(0);
        this.numNodes = codeSet.size();
        this.numEdges = 0;
        this.indexToCode = codeSet.toArray(new String[0]);
        this.codeToIndexMap = createCodeToIndexMap();
        this.adjacencyMatrix = createAdjacencyMatrix(csvReader.readRows());
    }

    public HashMap<String, Integer> createCodeToIndexMap() {
        HashMap<String, Integer> map = new HashMap<>();
        int index = 0;
        for (String code : indexToCode) {
            map.put(code, index++);
        }
        return map;
    }

    public int[][] createAdjacencyMatrix(List<String[]> csvRows) {
        int[][] matrix = new int[numNodes][numNodes];

        for (String[] row : csvRows) {
            addEdge(matrix, row);
        }
        return matrix;
    }

    public boolean addEdge(int[][] matrix, String[] row) {
        String origin = row[0].trim();
        String dest = row[1].trim();
        int distance = Integer.parseInt(row[2].trim());

        if (!codeSet.contains(origin) || !codeSet.contains(dest)) {
            return false;
        }

        int originIndex = codeToIndexMap.get(origin);
        int destIndex = codeToIndexMap.get(dest);

        if (matrix[originIndex][destIndex] == 0) {
            matrix[originIndex][destIndex] = distance;
            numEdges++;
            return true;
        }
        return false;
    }

    public List<String> getNeighbors(String code) {
        List<String> neighbors = new ArrayList<>();

        if (!codeToIndexMap.containsKey(code)) {
            return neighbors;
        }

        int index = codeToIndexMap.get(code);

        for (int j = 0; j < numNodes; j++) {
            if (adjacencyMatrix[index][j] != 0) {
                neighbors.add(indexToCode[j]);
            }
        }
        return neighbors;
    }

    public int getAirTime(String origin, String dest) {
        if (!codeToIndexMap.containsKey(origin) || !codeToIndexMap.containsKey(dest)) {
            throw new IllegalArgumentException("Invalid airport code(s).");
        }

        int originIdx = codeToIndexMap.get(origin);
        int destIdx = codeToIndexMap.get(dest);

        return adjacencyMatrix[originIdx][destIdx];
    }

    public int getNumNodes() {
        return numNodes;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public String[] getIndexToCode() {
        return indexToCode;
    }

    public HashMap<String, Integer> getCodeToIndexMap() {
        return codeToIndexMap;
    }

    public void printAdjacencyMatrix() {
        System.out.print("    ");
        for (int i = 0; i < numNodes; i++) {
            System.out.printf("%4d", i);
        }
        System.out.println();

        for (int i = 0; i < numNodes; i++) {
            System.out.printf("%4d", i);
            for (int j = 0; j < numNodes; j++) {
                System.out.printf("%4d", adjacencyMatrix[i][j]);
            }
            System.out.println();
        }
    }

    public void printCodes() {
        for (String code: indexToCode) {
            System.out.println(code);
        }
    }

    public static void main(String[] args) {
        try {
            FlightGraph graph = new FlightGraph("res/testData.csv");
            graph.printCodes();
            graph.printAdjacencyMatrix();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
