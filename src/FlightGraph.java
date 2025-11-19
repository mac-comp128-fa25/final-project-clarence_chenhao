import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class FlightGraph {
    private HashSet<AirportNode> nodesSet;
    private int numNodes;
    private int numEdges;
    private int[][] adjacencyMatrix;
    private Map<AirportNode, Integer> codeToIndexMap;
    private CSVReader csvReader;

    public FlightGraph(String filePath) {
        this.csvReader = new CSVReader(filePath);
        this.nodesSet
        this.numEdges = 0;
        this.adjacencyMatrix = new int[numNodes][numNodes];
        this.codeToIndexMap = createCodeToIndexMap();
    }
}
