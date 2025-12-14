import edu.macalester.graphics.*;
import edu.macalester.graphics.ui.*;
import java.io.IOException;
import java.util.List;
import java.awt.Color;

/**
 * HomeApp class that sets up the UI for the Flight Path Finder application.
 */
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
    private Button goBackToFirstPageButton;
    private Button byTimeButton;
    private Button byCostButton;


    /**
     * Constructor for HomeApp.
     * @param filePath file path to the CSV file containing flight data.
     */
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
     * Sets up the UI components.
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

        byTimeButton = new Button("filter by time");
        byTimeButton.setCenter(150, 750);

        byCostButton = new Button("filter by price");
        byCostButton.setCenter(300, 750);
    }

    /**
     * Creates the first page of the UI.
     */
    public void createFirstPage(){
        canvas.removeAll();
        canvas.add(firstPageTitle);
        canvas.add(originInput);
        canvas.add(originInputLabel);
        canvas.add(destInput);
        canvas.add(destInputLabel);
        canvas.add(searchButton);
    }

    /**
     * Tests if the input airport codes are valid.
     * @return true if both codes are valid, false otherwise.
     */
    public boolean testCodeValid() {
        boolean originIsValid = flightGraph.getCodeSet().contains(originInput.getText().toUpperCase());
        boolean destIsValid = flightGraph.getCodeSet().contains(destInput.getText().toUpperCase());

        if (originIsValid && destIsValid){
            originWrongMessageAdded = false;
            originInput.setBackground(new Color(0xFFFFFF));
            destWrongMessageAdded = false;
            destInput.setBackground(new Color(0xFFFFFF));
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

    /**
     * Creates the second page of the UI sorted by time.
     */
    public void createSecondPageByTime(){
        if(testCodeValid()){
            canvas.removeAll();
            canvas.add(secondPageTitle);
            canvas.add(goBackToFirstPageButton);
            canvas.add(byCostButton);
            canvas.add(byTimeButton);

            printResultByTime();                       
        }
    }

    /**
     * Creates the second page of the UI sorted by cost.
     */
    public void createSecondPageByCost(){
        if(testCodeValid()){
            canvas.removeAll();
            canvas.add(secondPageTitle);
            canvas.add(goBackToFirstPageButton);
            canvas.add(byCostButton);
            canvas.add(byTimeButton);

            printResultByCost();         
        }
    }

    /**
     * Print the results sorted by time.
     */
    public void printResultByTime(){
        List<PathResult> resultsByTime = pathFinder.findShortestPaths(originInput.getText().toUpperCase(), 
                                                                    destInput.getText().toUpperCase(), 
                                                                    10, 
                                                                    PathComparators.BY_TIME);
        for (int i = 0; i < resultsByTime.size(); i++){
            PathResult result = resultsByTime.get(i);
            GraphicsGroup singleEntry = createSingleEntry(result, 0, 50 + 30 * i);
            canvas.add(singleEntry);
        }

    }

    /**
     * Print the results sorted by cost.
     */
    public void printResultByCost(){
        List<PathResult> resultsByCost = pathFinder.findShortestPaths(originInput.getText().toUpperCase(), 
                                                                    destInput.getText().toUpperCase(), 
                                                                    10, 
                                                                    PathComparators.BY_COST);

        for (int i = 0; i < resultsByCost.size(); i++){
            PathResult result = resultsByCost.get(i);
            GraphicsGroup singleEntry = createSingleEntry(result, 0, 50 + 30 * i);
            canvas.add(singleEntry);
        }

    }

    /**
     * Draw Single Entry on the second page.
     * @param pathResult 
     * @param x X-coordinator of top-left corner of the Graphics Group
     * @param y Y-coordinator of top-left corner of the Graphics Group
     * @return GraphicGroup
     */
    public GraphicsGroup createSingleEntry(PathResult pathResult, int x, int y){
        
        GraphicsGroup singleEntry = new GraphicsGroup(x, y);

        GraphicsText originCode = new GraphicsText(pathResult.getFirstCode().toString());
        singleEntry.add(originCode, x+50, y+50);
        GraphicsText destCode = new GraphicsText(pathResult.getLastCode().toString());
        singleEntry.add(destCode, x+290, y+50);

        Line arrow = new Line(x+100, y+50, x+270, y+50);
        singleEntry.add(arrow);

        List<String> connectionAirports = pathResult.getConnectionAirports();

        if (connectionAirports == null){
            GraphicsText directText = new GraphicsText("Direct");
            singleEntry.add(directText);
            directText.setCenter(x+182, y+37);
        }
        else if (connectionAirports.size() == 1){
            GraphicsText airport = new GraphicsText(connectionAirports.get(0));
            singleEntry.add(airport);
            airport.setCenter(x+182, y+37);
        }
        else if (connectionAirports.size() == 2){
            GraphicsText airports = new GraphicsText(connectionAirports.get(0) + ", " + connectionAirports.get(1));
            singleEntry.add(airports);
            airports.setCenter(x+182, y+37);
        }
        else {
            GraphicsText tooMuch = new GraphicsText("3+ transfers");
            singleEntry.add(tooMuch);
            tooMuch.setCenter(x+182, y+37);
        }

        GraphicsText time = new GraphicsText(pathResult.timeToString());
        singleEntry.add(time);
        time.setCenter(x+182, y+65);

        GraphicsText cost = new GraphicsText(pathResult.costToString());
        singleEntry.add(cost);
        cost.setCenter(x+360, y+47);
        cost.setFont("serif", FontStyle.BOLD, 20);

        return singleEntry;
    }

    /**
     * Main method to run the Flight Path Finder application.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Flight Path Finder Application!");
        HomeApp app = new HomeApp("res/flightDataPrice.csv");
        app.setupUI();
        app.createFirstPage();
        app.searchButton.onClick(() -> app.createSecondPageByTime());
        app.goBackToFirstPageButton.onClick(() -> app.createFirstPage());
        app.byTimeButton.onClick(() -> app.createSecondPageByTime());
        app.byCostButton.onClick(() -> app.createSecondPageByCost());

    }
}
