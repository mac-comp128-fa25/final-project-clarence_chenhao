import java.util.List;

public class PathResult implements Comparable<PathResult> {
    private List<String> path;
    private int totalTime;

    public PathResult(List<String> path, int totalTime){
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

    public String getLastCode() {
        if (path.isEmpty()) {
            return null;
        }
        return path.get(path.size() - 1);
    }

    @Override
    public int compareTo(PathResult other) {
        return Integer.compare(this.totalTime, other.totalTime);
    }

    @Override
    public String toString() {
        return "Path: " + path + ", Total Time: " + totalTime;
    }
}
