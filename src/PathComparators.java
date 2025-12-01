import java.util.Comparator;

public class PathComparators{
    public static final Comparator<PathResult> BY_TIME = new Comparator<PathResult>() {
        @Override
        public int compare(PathResult p1, PathResult p2) {
            return Integer.compare(p1.getTotalTime(), p2.getTotalTime());
        }
    };

    public static final Comparator<PathResult> BY_COST = new Comparator<PathResult>() {
        @Override
        public int compare(PathResult p1, PathResult p2) {
            return Integer.compare(p1.getTotalPrice(), p2.getTotalPrice());
        }
    };
}
