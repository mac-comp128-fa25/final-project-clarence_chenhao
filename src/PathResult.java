import java.util.ArrayList;
import java.util.List;

/**
 * PathResult class that represents a path between airports along with its total time and price.
 */
public class PathResult {
    private List<String> path;
    private int totalTime;
    private int totalPrice;

    /**
     * Constructor for PathResult.
     * @param path List of airport codes representing the path.
     * @param totalTime total time of the path in minutes.
     * @param totalPrice total price of the path in dollars.
     */
    public PathResult(List<String> path, int totalTime, int totalPrice) {
        this.path = path;
        this.totalTime = totalTime;
        this.totalPrice = totalPrice;
    }

    /**
     * Getter for the path.
     * @return List of airport codes representing the path.
     */
    public List<String> getPath() {
        return path;
    }

    /**
     * Getter for the total time.
     * @return total time of the path in minutes.
     */
    public int getTotalTime() {
        return totalTime;
    }

    /**
     * Checks if the path exists.
     * @return true if the path is not empty, false otherwise.
     */
    public boolean pathExists() {
        return !path.isEmpty();
    }

    /**
     * Getter for the last airport code in the path.
     * @return the last airport code in the path.
     */
    public String getLastCode() {
        if (path.isEmpty()) {
            return null;
        }
        return path.get(path.size() - 1);
    }

    /**
     * Getter for the connection airports in the path.
     * @return List of connection airport codes, or null if there are none.
     */
    public List<String> getConnectionAirports() {

        if (path.size() < 3){
            return null;
        }

        List<String> airports = new ArrayList<String>();
        for (int i = 1; i < path.size() - 1; i++){
            airports.add(path.get(i));
        }

        return airports;
    }

    /**
     * Getter for the first airport code in the path.
     * @return the first airport code in the path.
     */
    public String getFirstCode() {
        if (path.isEmpty()) {
            return null;
        }
        return path.get(0);
    }

    /**
     * Getter for the total price.
     * @return total price of the path in dollars.
     */
    public int getTotalPrice() {
        return totalPrice;
    }

    /**
     * Converts total time to a string representation.
     * @return String representation of total time in minutes.
     */
    public String timeToString() {
        return totalTime + "min";
    }

    /**
     * Converts total price to a string representation.
     * @return String representation of total price in dollars.
     */
    public String costToString() {
        return "$" + totalPrice ;
    }
}
