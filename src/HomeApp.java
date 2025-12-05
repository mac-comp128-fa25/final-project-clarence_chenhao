import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.*;
import edu.macalester.graphics.events.*;
import java.io.IOException;
import java.util.List;
import java.awt.Color;
import java.awt.Paint;
import java.awt.color.*;

public class HomeApp {
    FlightGraph flightGraph;
    PathFinder pathFinder;

    private CanvasWindow canvas;
    private GraphicsText title;
    private GraphicsText originInputLabel;
    private TextField originInput;
    private GraphicsText destInputLabel;
    private TextField destInput;
    private Button searchButton;

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
        canvas.setBackground(new Color(0xADD8E6) );

        title = new GraphicsText("Flight Path Finder", 150, 50);
        title.setFont("serif", FontStyle.BOLD, 42);
        title.setCenter(225,50);
        canvas.add(title);

        originInputLabel = new GraphicsText("FROM :");
        originInput = new TextField();
        originInputLabel.setCenter(100,130);
        originInput.setCenter(100, 150);
        canvas.add(originInput);
        canvas.add(originInputLabel);

        destInputLabel = new GraphicsText("TO :");
        destInput = new TextField();
        destInputLabel.setCenter(300, 130);
        destInput.setCenter(300, 150);
        canvas.add(destInput);
        canvas.add(destInputLabel);

        searchButton = new Button("Search");
        searchButton.setCenter(225,400);
        canvas.add(searchButton);
    }

    public void createSecondPage() {
        if (flightGraph.getCodeSet().contains(originInput.getText().toUpperCase()) && flightGraph.getCodeSet().contains(destInput.getText().toUpperCase())){
            canvas.removeAll();
            title.setText("Result");
            canvas.add(title);
        }
        else{
            if (!flightGraph.getCodeSet().contains(originInput.getText().toUpperCase())){
                originInput.setBackground(new Color(0xFFB1B1));
                canvas.add(new GraphicsText("Enter a valid airport code"), 10, 170);
            }
            if (!flightGraph.getCodeSet().contains(destInput.getText().toUpperCase())){
                destInput.setBackground(new Color(0xFFB1B1));
                canvas.add(new GraphicsText("Enter a valid airport code", 210, 170));
            }
        }
    }

    
    public static void main(String[] args) {
        System.out.println("Welcome to the Flight Path Finder Application!");
        HomeApp app = new HomeApp("res/flightDataPrice.csv");
        app.setupUI();
        app.searchButton.onClick(() -> app.createSecondPage());

        // Tests
    //     List<PathResult> timeResult = app.pathFinder.findShortestPaths("MSP", "JFK", 3, PathComparators.BY_TIME);
    //     System.out.println(timeResult);
    //     List<PathResult> costResult = app.pathFinder.findShortestPaths("MSP", "JFK", 3, PathComparators.BY_COST);
    //     System.out.println(costResult);
    }
}
