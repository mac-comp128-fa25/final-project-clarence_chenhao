import java.util.List;
import java.util.Map;

public class FlightGraph {
    public static final List<AirportNode> AIRPORTS = List.of(
        new AirportNode("ATL"),
        new AirportNode("LAX"),
        new AirportNode("ORD"),
        new AirportNode("DFW"),
        new AirportNode("DEN"),
        new AirportNode("JFK"),
        new AirportNode("SFO"),
        new AirportNode("SEA"),
        new AirportNode("MIA"),
        new AirportNode("BOS"),
        new AirportNode("CLT"),
        new AirportNode("MCO"),
        new AirportNode("LAS"),
        new AirportNode("PHX"),
        new AirportNode("EWR"),
        new AirportNode("IAH"),
        new AirportNode("DTW"),
        new AirportNode("PHL"),
        new AirportNode("SLC"),
        new AirportNode("BHM"),
        new AirportNode("LIT"),
        new AirportNode("MSP"),
        new AirportNode("BDL"),
        new AirportNode("ILG"),
        new AirportNode("BOI"),
        new AirportNode("IND"),
        new AirportNode("ICT"),
        new AirportNode("SDF"),
        new AirportNode("PWM"),
        new AirportNode("BWI"),
        new AirportNode("JAN"),
        new AirportNode("STL"),
        new AirportNode("BZN"),
        new AirportNode("OMA"),
        new AirportNode("MHT"),
        new AirportNode("ABQ"),
        new AirportNode("FAR"),
        new AirportNode("CLE"),
        new AirportNode("OKC"),
        new AirportNode("PDX"),
        new AirportNode("IAD")
        // new AirportNode("PVD"),
        // new AirportNode("CHS"),
        // new AirportNode("FSD"),
        // new AirportNode("BNA"),
        // new AirportNode("BTB"),
        // new AirportNode("CRW"),
        // new AirportNode("MKE"),
        // new AirportNode("JAC"),
        // new AirportNode("DCA")
    );

    private int numNodes = AIRPORTS.size();
    private int numEdges;
    private int[][] adjacencyMatrix;
    private Map<AirportNode, Integer> codeToIndexMap;

}
