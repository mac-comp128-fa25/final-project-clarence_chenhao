import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class FlightGraph {
    private HashSet<AirportNode> nodesSet;
    private int numNodes;
    private int numEdges;
    private int[][] adjacencyMatrix;
    private HashMap<AirportNode, Integer> codeToIndexMap;
    private HashMap<Integer, AirportNode> indexToCodeMap;
    private CSVReader csvReader;

    public FlightGraph(String filePath) throws IOException {
        this.csvReader = new CSVReader(filePath);
        this.nodesSet = csvReader.readAirportsAsSet(0);
        this.numNodes = nodesSet.size();
        this.numEdges = 0;
        this.codeToIndexMap = createCodeToIndexMap();
        this.indexToCodeMap = createIndexToCodeMap();
        this.adjacencyMatrix = createAdjacencyMatrix(csvReader.readRows());
    }

    public HashMap<AirportNode, Integer> createCodeToIndexMap() {
        HashMap<AirportNode, Integer> map = new HashMap<>();
        int index = 0;
        for (AirportNode node : nodesSet) {
            map.put(node, index++);
        }
        return map;
    }

    public HashMap<Integer, AirportNode> createIndexToCodeMap() {
        HashMap<Integer, AirportNode> map = new HashMap<>();
        for (AirportNode node : codeToIndexMap.keySet()) {
            int index = codeToIndexMap.get(node);
            map.put(index, node);
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
        AirportNode origin = new AirportNode(row[0].trim());
        AirportNode dest = new AirportNode(row[1].trim());
        int distance = Integer.parseInt(row[2].trim());

        if (!nodesSet.contains(origin) || !nodesSet.contains(dest)) {
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

    public int getNumNodes() {
        return numNodes;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
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

    public void printNodeSet() {
        for (AirportNode node : nodesSet) {
            System.out.println(node.getCode());
        }
    }

    public static void main(String[] args) {
        try {
            FlightGraph graph = new FlightGraph("res/testData.csv");
            graph.printNodeSet();
            graph.printAdjacencyMatrix();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
