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

    public int getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Path: " + path + ", Total Time: " + totalTime + ", Total Price: " + totalPrice;
    }
}
