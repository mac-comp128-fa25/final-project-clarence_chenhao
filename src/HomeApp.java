import java.io.IOException;
import java.util.List;

public class HomeApp {
    FlightGraph flightGraph;
    PathFinder pathFinder;

    public HomeApp(String filePath) {
        try {
            flightGraph = new FlightGraph(filePath);
            pathFinder = new PathFinder(flightGraph);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        System.out.println("Welcome to the Flight Path Finder Application!");
        HomeApp app = new HomeApp("res/flightDataPrice.csv");

        // Tests
        List<PathResult> timeResult = app.pathFinder.findShortestPaths("MSP", "JFK", 3, PathComparators.BY_TIME);
        System.out.println(timeResult);
        List<PathResult> costResult = app.pathFinder.findShortestPaths("MSP", "JFK", 3, PathComparators.BY_COST);
        System.out.println(costResult);
    }
}
