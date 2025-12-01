import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class PathFinder {
    private FlightGraph flightGraph;
    private HashMap<String, Integer> codeToIndexMap;
    private String[] indexToCode;

    public PathFinder(FlightGraph flightGraph) {
        this.flightGraph = flightGraph;
        this.codeToIndexMap = flightGraph.getCodeToIndexMap();
        this.indexToCode = flightGraph.getIndexToCode();
    }

    public List<PathResult> findShortestPaths(String originCode, String destCode, int k) {
        
        PriorityQueue<PathResult> pq = new PriorityQueue<>();
        List<PathResult> results = new ArrayList<>();

        pq.add(new PathResult(new ArrayList<>(List.of(originCode)), 0));

        while (!pq.isEmpty() && results.size() < k) {
            PathResult currentPath = pq.poll();
            String lastNode = currentPath.getLastCode();

            if (lastNode.equals(destCode)) {
                results.add(currentPath);
                continue;
            }

            for (String neighborCode : flightGraph.getNeighbors(lastNode)) {
                if (!currentPath.getPath().contains(neighborCode)) {
                    List<String> newPathList = new ArrayList<>(currentPath.getPath());
                    newPathList.add(neighborCode);
                    int newTotalTime = currentPath.getTotalTime() + flightGraph.getAirTime(lastNode, neighborCode);
                    pq.add(new PathResult(newPathList, newTotalTime));
                }
            }
        }

        return results;
    }
}
