import java.util.List;
import java.util.Map;

public class FlightGraph {
    public static final List<AirportNode> AIRPORTS = List.of(
        new AirportNode("ATL"),
        new AirportNode("LAX"),
        new AirportNode("ORD"),
        new AirportNode("DFW"),
        new AirportNode("DEN"),
        new AirportNode("JFK"),
        new AirportNode("SFO"),
        new AirportNode("SEA"),
        new AirportNode("MIA"),
        new AirportNode("BOS")
    );

    private int numNodes = AIRPORTS.size();
    private int numEdges;
    private int[][] adjacencyMatrix;
    private Map<AirportNode, Integer> codeToIndexMap;

}
