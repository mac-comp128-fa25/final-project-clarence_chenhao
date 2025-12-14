import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

/**
 * FlightGraph class that represents a graph of flights between airports.
 */
public class FlightGraph {
    private HashSet<String> codeSet;
    private int numNodes;
    private int numEdges;
    private int[][][] adjacencyMatrix;
    private String[] indexToCode;
    private HashMap<String, Integer> codeToIndexMap;
    private CSVReader csvReader;

    /**
     * Constructor for FlightGraph.
     * @param filePath file path to the CSV file containing flight data.
     * @throws IOException
     */
    public FlightGraph(String filePath) throws IOException {
        this.csvReader = new CSVReader(filePath);
        this.codeSet = csvReader.readColumnAsSet(0);
        this.numNodes = codeSet.size();
        this.numEdges = 0;
        this.indexToCode = codeSet.toArray(new String[0]);
        this.codeToIndexMap = createCodeToIndexMap();
        this.adjacencyMatrix = createAdjacencyMatrix(csvReader.readRows());
    }

    /**
     * Creates a mapping from airport codes to their corresponding indices.
     * @return A HashMap mapping airport codes to indices.
     */
    public HashMap<String, Integer> createCodeToIndexMap() {
        HashMap<String, Integer> map = new HashMap<>();
        int index = 0;
        for (String code : indexToCode) {
            map.put(code, index++);
        }
        return map;
    }

    /**
     * Creates the adjacency matrix from the CSV rows.
     * @param csvRows List of String arrays, each representing a row in the CSV file.
     * @return A 3D integer array representing the adjacency matrix.
     */
    public int[][][] createAdjacencyMatrix(List<String[]> csvRows) {
        int[][][] matrix = new int[numNodes][numNodes][2];

        for (String[] row : csvRows) {
            addEdge(matrix, row);
        }
        return matrix;
    }

    /**
     * Adds an edge to the adjacency matrix.
     * @param matrix The adjacency matrix.
     * @param row A String array representing a row in the CSV file.
     * @return true if the edge was added successfully, false otherwise.
     */
    public boolean addEdge(int[][][] matrix, String[] row) {
        String origin = row[0].trim();
        String dest = row[1].trim();
        int distance = Integer.parseInt(row[2].trim());
        int cost = Integer.parseInt(row[3].trim());

        if (!codeSet.contains(origin) || !codeSet.contains(dest)) {
            return false;
        }

        int originIndex = codeToIndexMap.get(origin);
        int destIndex = codeToIndexMap.get(dest);

        if (matrix[originIndex][destIndex][0] == 0) {
            matrix[originIndex][destIndex][0] = distance;
            matrix[originIndex][destIndex][1] = cost;
            numEdges++;
            return true;
        }
        return false;
    }

    /**
     * Gets the neighboring airport codes for a given airport code.
     * @param code The airport code.
     * @return A List of neighboring airport codes.
     */
    public List<String> getNeighbors(String code) {
        List<String> neighbors = new ArrayList<>();

        if (!codeToIndexMap.containsKey(code)) {
            return neighbors;
        }

        int index = codeToIndexMap.get(code);

        for (int j = 0; j < numNodes; j++) {
            if (adjacencyMatrix[index][j][0] != 0) {
                neighbors.add(indexToCode[j]);
            }
        }
        return neighbors;
    }

    /**
     * Gets the air time between two airport codes.
     * @param origin The origin airport code.
     * @param dest The destination airport code.
     * @return The air time between the two airports.
     */
    public int getAirTime(String origin, String dest) {
        if (!codeToIndexMap.containsKey(origin) || !codeToIndexMap.containsKey(dest)) {
            throw new IllegalArgumentException("Invalid airport code(s).");
        }

        int originIdx = codeToIndexMap.get(origin);
        int destIdx = codeToIndexMap.get(dest);

        return adjacencyMatrix[originIdx][destIdx][0];
    }

    /**
     * Gets the cost between two airport codes.
     * @param origin The origin airport code.
     * @param dest The destination airport code.
     * @return The cost between the two airports.
     */
    public int getCost(String origin, String dest) {
        if (!codeToIndexMap.containsKey(origin) || !codeToIndexMap.containsKey(dest)) {
            throw new IllegalArgumentException("Invalid airport code(s).");
        }

        int originIdx = codeToIndexMap.get(origin);
        int destIdx = codeToIndexMap.get(dest);

        return adjacencyMatrix[originIdx][destIdx][1];
    }

    /**
     * Calculates the density of the flight graph.
     * @return The density of the graph.
     */
    public double getDensity() {
        if (numNodes <= 1) {
            return 0.0;
        }
        return (1.0 * numEdges) / (numNodes * (numNodes - 1));
    }

    /**
     * Gets the set of airport codes in the flight graph.
     */
    public HashSet<String> getCodeSet() {
        return codeSet;
    }

    /**
     * Gets the number of nodes in the flight graph.
     */
    public int getNumNodes() {
        return numNodes;
    }

    /**
     * Gets the number of edges in the flight graph.
     */
    public int getNumEdges() {
        return numEdges;
    }

    /**
     * Gets the adjacency matrix of the flight graph.
     */
    public int[][][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    /**
     * Gets the mapping from indices to airport codes.
     */
    public String[] getIndexToCode() {
        return indexToCode;
    }

    /**
     * Gets the mapping from airport codes to indices.
     */
    public HashMap<String, Integer> getCodeToIndexMap() {
        return codeToIndexMap;
    }
}
