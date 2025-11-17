

public class FlightEdge {
    private AirportNode origin;
    private AirportNode dest;
    private int distance;
    private int airTime;
    private int cost;

    public FlightEdge(AirportNode origin, AirportNode dest, int distance) {
        this.origin = origin;
        this.dest = dest;
        this.distance = distance;
        this.airTime = 0;
        this.cost = 0;
    }
}
