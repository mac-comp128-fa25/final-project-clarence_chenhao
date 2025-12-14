import java.util.ArrayList;
import java.util.List;

public class PathResult {
    private List<String> path;
    private int totalTime;
    private int totalPrice;

    public PathResult(List<String> path, int totalTime, int totalPrice) {
        this.path = path;
        this.totalTime = totalTime;
        this.totalPrice = totalPrice;
    }

    public List<String> getPath() {
        return path;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public boolean pathExists() {
        return !path.isEmpty();
    }

    public String getLastCode() {
        if (path.isEmpty()) {
            return null;
        }
        return path.get(path.size() - 1);
    }

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

    public String getFirstCode() {
        if (path.isEmpty()) {
            return null;
        }
        return path.get(0);
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String timeToString() {
        return totalTime + "min";
    }

    public String costToString() {
        return "$" + totalPrice ;
    }
}
