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
        HomeApp app = new HomeApp("res/flightData.csv");

        // Example usage
        List<PathResult> result = app.pathFinder.findShortestPaths("DEN", "ORD", 3);
        System.out.println(result);
    }
}
