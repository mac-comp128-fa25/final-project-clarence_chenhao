import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * PathFinder class that finds the shortest paths between airports.
 */
public class PathFinder {
    private FlightGraph flightGraph;

    /**
     * Constructor for PathFinder.
     * @param flightGraph the FlightGraph object representing the flight network.
     */
    public PathFinder(FlightGraph flightGraph) {
        this.flightGraph = flightGraph;
    }

    /**
     * Finds the k shortest paths between the origin and destination airport codes using the specified comparator for sorting.
     * @param originCode the origin airport code.
     * @param destCode the destination airport code.
     * @param k the number of shortest paths to find.
     * @param comparator the comparator to sort the paths.
     * @return A list of PathResult objects representing the k shortest paths.
     */
    public List<PathResult> findShortestPaths(String originCode, String destCode, int k, Comparator<PathResult> comparator) {
        
        PriorityQueue<PathResult> pq = new PriorityQueue<>(comparator);
        List<PathResult> results = new ArrayList<>();

        pq.add(new PathResult(new ArrayList<>(List.of(originCode)), 0, 0));

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
                    int newTotalPrice = currentPath.getTotalPrice() + flightGraph.getCost(lastNode, neighborCode);
                    pq.add(new PathResult(newPathList, newTotalTime, newTotalPrice));
                }
            }
        }
        return results;
    }
}
