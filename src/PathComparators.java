import java.util.Comparator;

/**
 * A class containing comparators for PathResult objects.
 */
public class PathComparators{
    /**
     * Comparator to compare PathResult objects by total time.
     */
    public static final Comparator<PathResult> BY_TIME = new Comparator<PathResult>() {
        @Override
        public int compare(PathResult p1, PathResult p2) {
            return Integer.compare(p1.getTotalTime(), p2.getTotalTime());
        }
    };

    /**
     * Comparator to compare PathResult objects by total cost.
     */
    public static final Comparator<PathResult> BY_COST = new Comparator<PathResult>() {
        @Override
        public int compare(PathResult p1, PathResult p2) {
            return Integer.compare(p1.getTotalPrice(), p2.getTotalPrice());
        }
    };
}
