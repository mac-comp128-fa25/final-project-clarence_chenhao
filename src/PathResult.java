import java.util.List;

public class PathResult {
    private List<String> path;
    private int totalTime;

    public PathResult(List<String> path, int totalTime) {
        this.path = path;
        this.totalTime = totalTime;
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
}
