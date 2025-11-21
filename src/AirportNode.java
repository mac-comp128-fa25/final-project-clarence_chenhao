
/**
 * Represents an airport node in a graph with its code and degree.
 */
public class AirportNode {
    private String code;
    private int degree;

    public AirportNode(String code) {
        this.code = code;
        this.degree = 0;
    }

    public String getCode() {
        return code;
    }
}
