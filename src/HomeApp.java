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
    private GraphicsText firstPageTitle;
    private GraphicsText secondPageTitle;
    private GraphicsText originInputLabel;
    private TextField originInput;
    private GraphicsText destInputLabel;
    private TextField destInput;
    private Button searchButton;
    private GraphicsText originWrongMessage = new GraphicsText(("Enter a valid airport code"), 10, 170);
    private GraphicsText destWrongMessage = new GraphicsText(("Enter a valid airport code"), 210, 170);
    private boolean originWrongMessageAdded = false;
    private boolean destWrongMessageAdded = false;
    private Boolean isSecondPage = false;
    private Button goBackToFirstPageButton;


    public HomeApp(String filePath) {
        try {
            flightGraph = new FlightGraph(filePath);
            pathFinder = new PathFinder(flightGraph);
        } catch (IOException e) {
            e.printStackTrace();
        }
        canvas = new CanvasWindow("Flight Path Finder", 450, 800);
    }

    /**
     * Every canvas component is initiated here.
     */
    public void setupUI() {
        canvas.setBackground(new Color(0xADD8E6));

        firstPageTitle = new GraphicsText("Flight Path Finder", 150, 50);
        firstPageTitle.setFont("serif", FontStyle.BOLD, 42);
        firstPageTitle.setCenter(225,50);

        secondPageTitle = new GraphicsText("Result", 150, 50);
        secondPageTitle.setFont("serif", FontStyle.BOLD, 42);
        secondPageTitle.setCenter(225,50);
        
        originInputLabel = new GraphicsText("FROM :");
        originInput = new TextField();
        originInputLabel.setCenter(100,130);
        originInput.setCenter(100, 150);

        destInputLabel = new GraphicsText("TO :");
        destInput = new TextField();
        destInputLabel.setCenter(300, 130);
        destInput.setCenter(300, 150);

        searchButton = new Button("Search");
        searchButton.setCenter(225,400);

        goBackToFirstPageButton = new Button("<");
        goBackToFirstPageButton.setPosition(5, 5);
    }

    public void createFirstPage(){
        canvas.removeAll();
        canvas.add(firstPageTitle);
        canvas.add(originInput);
        canvas.add(originInputLabel);
        canvas.add(destInput);
        canvas.add(destInputLabel);
        canvas.add(searchButton);
    }

    public boolean testCodeValid() {

        boolean originIsValid = flightGraph.getCodeSet().contains(originInput.getText().toUpperCase());
        boolean destIsValid = flightGraph.getCodeSet().contains(destInput.getText().toUpperCase());

        if (originIsValid && destIsValid){
            originWrongMessageAdded = false;
            originInput.setBackground(new Color(0xFFFFFF));
            destWrongMessageAdded = false;
            destInput.setBackground(new Color(0xFFFFFF));
            isSecondPage = true;
            return true;
        }

        if (!destIsValid){
            if (!destWrongMessageAdded){
                destInput.setBackground(new Color(0xFFB1B1));
                canvas.add(destWrongMessage);
                destWrongMessageAdded = true;
            }
        }
        else {
            if (destWrongMessageAdded){
                destInput.setBackground(new Color(0xFFFFFF));
                canvas.remove(destWrongMessage);
                destWrongMessageAdded = false;
            }
        }

        if (!originIsValid){
            if (!originWrongMessageAdded){
                originInput.setBackground(new Color(0xFFB1B1));
                canvas.add(originWrongMessage);
                originWrongMessageAdded = true;
            }
        }
        else{
            if (originWrongMessageAdded){
                originInput.setBackground(new Color(0xFFFFFF));
                canvas.remove(originWrongMessage);
                originWrongMessageAdded = false;
            }
        }
        return false;
    }

    public void createSecondPage(){

        if(testCodeValid()){
            canvas.removeAll();
            canvas.add(secondPageTitle);
            canvas.add(goBackToFirstPageButton);
        }
    }


    /**
     * Draw Single Entry on the second page.
     * @param pathResult 
     * @param x X-coordinator of top-left corner of the Graphics Group
     * @param y Y-coordinator of top-left corner of the Graphics Group
     * @return GraphicGroup
     */
    public GraphicsGroup drawSingleEntry(PathResult pathResult, int x, int y){
        
        GraphicsGroup singleEntry = new GraphicsGroup(x, y);

        GraphicsText originCode = new GraphicsText(pathResult.getFirstCode().toString());
        singleEntry.add(originCode, x+50, y+50);
        GraphicsText destCode = new GraphicsText(pathResult.getLastCode().toString());
        singleEntry.add(destCode, x+300, y+50);

        Line arrow = new Line(x+100, y+60, x+270, y+60);
        singleEntry.add(arrow);

        return singleEntry;
    }

    
    public static void main(String[] args) {
        System.out.println("Welcome to the Flight Path Finder Application!");
        HomeApp app = new HomeApp("res/flightDataPrice.csv");
        app.setupUI();
        app.createFirstPage();
        app.searchButton.onClick(() -> app.createSecondPage());
        app.goBackToFirstPageButton.onClick(() -> app.createFirstPage());

        // Tests
    //     List<PathResult> timeResult = app.pathFinder.findShortestPaths("MSP", "JFK", 3, PathComparators.BY_TIME);
    //     System.out.println(timeResult);
    //     List<PathResult> costResult = app.pathFinder.findShortestPaths("MSP", "JFK", 3, PathComparators.BY_COST);
    //     System.out.println(costResult);
    }
}
