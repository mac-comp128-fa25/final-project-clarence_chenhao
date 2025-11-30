import java.util.HashMap;

public class PathFinder {
    private FlightGraph flightGraph;
    private HashMap<String, Integer> codeToIndexMap;
    private String[] indexToCode;

    public PathFinder(FlightGraph flightGraph) {
        this.flightGraph = flightGraph;
        this.codeToIndexMap = flightGraph.getCodeToIndexMap();
        this.indexToCode = flightGraph.getIndexToCode();
    }

    public PathResult findShortestPath(String originCode, String destCode) {
        int originIndex = codeToIndexMap.get(originCode);
        int destIndex = codeToIndexMap.get(destCode);

        return new PathResult(null, -1); // Placeholder
    }
}
