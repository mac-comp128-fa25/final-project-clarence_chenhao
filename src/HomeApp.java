import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.*;
import edu.macalester.graphics.events.*;
import java.io.IOException;
import java.util.List;

public class HomeApp {
    FlightGraph flightGraph;
    PathFinder pathFinder;

    private CanvasWindow canvas;
    private GraphicsText title;
    private TextField originInput;
    private TextField destInput;

    public HomeApp(String filePath) {
        try {
            flightGraph = new FlightGraph(filePath);
            pathFinder = new PathFinder(flightGraph);
        } catch (IOException e) {
            e.printStackTrace();
        }
        canvas = new CanvasWindow("Flight Path Finder", 450, 800);
    }

    public void setupUI() {
        // canvas.setBackgroundColor(Colors.LIGHTBLUE);
        title = new GraphicsText("Flight Path Finder", 150, 50);
        title.setFont("serif", FontStyle.BOLD, 42);
        title.setCenter(225,50);
        canvas.add(title);
        originInput = new TextField();
        originInput.setCenter(225, 150);
        originInput.setText("Origin Airport Code");
        canvas.add(originInput);
    }
    
    public static void main(String[] args) {
        System.out.println("Welcome to the Flight Path Finder Application!");
        HomeApp app = new HomeApp("res/flightDataPrice.csv");
        app.setupUI();

        // Tests
        List<PathResult> timeResult = app.pathFinder.findShortestPaths("MSP", "JFK", 3, PathComparators.BY_TIME);
        System.out.println(timeResult);
        List<PathResult> costResult = app.pathFinder.findShortestPaths("MSP", "JFK", 3, PathComparators.BY_COST);
        System.out.println(costResult);
    }
}
