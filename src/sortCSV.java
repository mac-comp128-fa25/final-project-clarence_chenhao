import java.io.*;
import java.util.*;

public class SortCSV {
    public static void main(String[] args) throws Exception {

        List<String[]> rows = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader("res/NewYork_flights.csv"))) {
            while ((line = br.readLine()) != null) {
                rows.add(line.split(","));
            }
        }

        int destAirportColumn = 14;

        String[] header = rows.get(0);
        List<String[]> body = rows.subList(1, rows.size());

        body.sort((a, b) ->
            a[destAirportColumn].compareToIgnoreCase(b[destAirportColumn])
        );


        List<String> uniqueAirports = new ArrayList<>();
        Set<String> seen = new HashSet<>();

        for (String[] row : body) {
            String airport = row[destAirportColumn].trim();
            if (!seen.contains(airport)) {
                seen.add(airport);
                uniqueAirports.add(airport);
            }
        }

        for (String a : uniqueAirports) {
            System.out.println(a);
        }

    }
}