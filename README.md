# Flight Path Finder

## Project Description and Expected Functionality
In modern air travel, it is often helpful to compare not just a single flight, but multiple possible flight paths between two cities. Our project’s goal is to build a simple flight search engine that, given an origin and destination airport, returns several flight paths, sorted either by total travel time or by total ticket cost.

Our system reads a CSV file containing estimated flight data and builds a directed graph in which each vertex represents an airport and each edge represents a direct flight, storing both the airtime and the price. We then use a graph-search algorithm to compute the top-k paths between a pair of airports, based on a user-selected metric (time or cost). Using a CSV file makes it easy to expand to incorporate a different set of airports or other data by loading a different CSV file.

The user interacts with the program through a simple graphical interface built with the KiltGraphics library. 

### Expected Functionality
    The user will start the program by hitting the “run” button in the “HomeApp” class.
    The user will then fill in 2 text fields in the KiltGraphics UI to enter valid airport codes for the origin and destination.
    The user will then hit the “GO” button, which will:
    Go to the results page if valid airport codes are entered.
    Prompt the user to enter valid airport codes if invalid ones are entered.
    The results page will display up to 10 results based on flight time.
    The user will then be given the choice to toggle between time and cost priority.

## Implementation Details
Our code functions as follows:
    Data loading
    The program uses the helper class CSVReader to read a CSV file storing flight information, where each row represents one flight with the columns from left to right being: OriginAirport, DestinationAirport, Airtime(minutes), Cost(USD)
    From the file, we identify all unique airport codes and store them in a HashSet to determine the total number of nodes in our graph. We create a HashMap that maps each airport code (String) to an integer index.

    Graph Construction
    We then read the file by row and represent the flight network data as a weighted directed graph, where each vertex corresponds to an airport and each edge to a directed flight. The weights of each edge are the airtime and cost data.
    The graph is stored as a 3D integer array, which we will explain in greater detail in a later section.
    Path Finding Algorithm
    We then implement a variation on Dijkstra’s algorithm to allow us to find the shortest k paths between any pair of vertices. Details of the algorithm will also be explained in the next section.
    The result is then sorted using a priority queue and is ready to be output to the user.
    UI
    We then draw the UI with KiltGraphics and allow the user to interact with the program through the UI rather than the console.

## Algorithm and Data Structure Choice
### Algorithm
The main challenge of this project is implementing an appropriate path-finding algorithm and selecting an appropriate graph data structure. This section will dive into the algorithm we use, a variation of Dijkstra’s algorithm that finds the shortest k paths, not just the shortest route. 

Dijkstra’s algorithm is a shortest-path algorithm for graphs with non-negative edge weights. In this project, Dijkstra’s algorithm can be described as a weighted version of breadth-first search in which a priority queue replaces the ordinary queue. In a standard breadth-first search on an unweighted graph, the algorithm stores vertices in a FIFO queue and processes them in “layers”: it first visits all vertices at distance 1 from the start, then all at distance 2, and so on. Dijkstra’s algorithm keeps the same overall pattern of “take one vertex from a queue, then look at its neighbours,” but it changes the rule for which vertex is processed next. Instead of using a simple queue, it stores vertices in a priority queue ordered by their current known distance from the starting vertex. At the beginning, the starting vertex is given a distance of 0, and everything else is set to infinity, and only the starting vertex is added to the priority queue.

The algorithm then repeatedly performs the same two actions. First, it removes from the priority queue the vertex whose recorded distance from the start is currently the smallest. This guarantees the shortest actual distance to that vertex. Second, it looks at each neighbour of that vertex and checks whether going from the start to the current vertex, then along the edge to the neighbour, would yield a shorter total distance (based on the weights) than the currently recorded distance for that neighbour. If the newly computed distance is smaller, the algorithm updates the neighbour’s distance and inserts it into the priority queue in this way, the search still “fans out” from the start as in breadth-first search, but ordered by total path cost rather than by the number of steps, which allows the algorithm to find shortest paths in graphs with non-negative edge weights.

In this project, we adapt Dijkstra’s algorithm by running it on custom path objects that hold time and cost information, rather than on individual vertices. We also ask for the top k paths instead of just one. Instead of storing a single “best distance” per airport, the algorithm keeps a priority queue of candidate paths, each with its own total time and total cost. At each step, it removes the path with the smallest total value according to a chosen metric, then extends that path by one flight along each outgoing edge from its last airport, discarding any extension that would revisit an airport already in the path to avoid cycles. Every time a path ends at the destination, it is recorded as one of the results, and the process continues until k such paths have been found or no candidates remain. A key feature is that the same search logic is reused for both “shortest time” and “lowest cost,” with the priority queue ordered by different comparators depending on which metric the user selects.


### Data Structure Choice
For this project, we had to make several key decisions regarding data structures. The most significant choices were how to represent the graph and how to manage the exploration of paths.
Graph Representation: Adjacency Matrix vs. Adjacency List
Since we are using a BFS algorithm, it seems natural to use an adjacency list, since the algorithm requires visiting each neighbour of each node. In an adjacency list implementation, this results in a time complexity of O(deg(u)) for each node u. The space complexity of this implementation would be O(V+E), where V is the number of vertices and E is the number of edges, which favours sparse graphs.
    We chose to use an adjacency matrix representation for our project. This is slightly less efficient with a time complexity for each node of  O(n), n being the number of nodes, and a space efficiency of O(V^2). This is justified because we are using a dense graph with a density of roughly 0.55, in which more than half of the edges are present. We expect real flight data graphs to be even denser as central hubs connect to hundreds of other airports, meaning that O(deg(u)) ≈ O(n) and O(V+E) ≈ O(V^2), making implementation comparable in terms of both time and space efficiency with an adjacency list implementation.
    We benefit from an adjacency array implementation in two ways. Firstly, it allows us to implement a weighted multi-graph using a 3D array int[][][], where the first two dimensions correspond to the standard adjacency matrix, and each entry is a 2-element array that stores time and price data in a compact package without requiring a custom Edge class. Secondly, it gives us a O(1) time efficiency for checking whether an edge exists and retrieving its weights, instead of O(n) for adjacency lists. This makes our getter functions highly efficient and allows a quick check for direct flights.

## Potential Improvements
    Although we have completed all of our intended features with no significant bugs, there is definitely room for improvement and additional features.

    Drop-down menu:
    Current functionality: We use two text fields for the user to enter airport codes, and we employ defensive programming to catch user errors.
    Potential Improvement: We can use a dropdown menu with all valid airports in alphabetical order so the user won’t make a mistake.
    Real data with time:
    Current functionality: Our current project uses made-up data with no time information, assuming that only one flight is available between each pair of airports.
    Potential Improvement: We can incorporate real-world data on departure and arrival times to allow more variation in price and airtime based on how early or late the flight is and on how many flights are scheduled on a given day.

## Sources Used
    The Dijkstra’s Algorithm Wikipedia page


