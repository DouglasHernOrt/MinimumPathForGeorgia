package finalProject;

import java.util.*;

public class TSPprims {

	// coordinates object for cities
    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    // function to calculate distance between cities (distance formula)
    static double distance(Point a, Point b) {
        return Math.sqrt(Math.pow((b.x - a.x), 2) + Math.pow((b.y - a.y), 2));
    }
    
    static class Edge implements Comparable<Edge> {
        int to;
        double weight;

        Edge(int to, double weight) {
            this.to = to;
            this.weight = weight;
        }

        public int compareTo(Edge other) {
            return Double.compare(this.weight, other.weight);
        }
    }
    // main
    public static void main(String[] args) {
    	// map for storing city names and coordinates
        Map<String, Point> cities = new HashMap<>();
        cities.put("Atlanta", new Point(27, 72));
        cities.put("Macon", new Point(42, 52));
        cities.put("Athens", new Point(46, 76));
        cities.put("Augusta", new Point(71, 65));
        cities.put("Peachtree city", new Point(25, 64));
        cities.put("Rome", new Point(14, 83));
        cities.put("Columbus", new Point(17, 44));
        cities.put("Gainesville", new Point(38, 83));
        cities.put("Eatonton", new Point(46, 62));
        cities.put("Dublin", new Point(54, 45));

        int n = cities.size();
        String[] cityNames = cities.keySet().toArray(new String[0]);
        Point[] points = cities.values().toArray(new Point[0]);

        // Calculate distances between all pairs of cities
        double[][] distances = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                distances[i][j] = distance(points[i], points[j]);
            }
        }

        // create a graph representing distances between cities
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        // build graph with calculated distances as edges
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                graph.get(i).add(new Edge(j, distances[i][j]));
                graph.get(j).add(new Edge(i, distances[i][j]));
            }
        }

        List<Integer> mst = new ArrayList<>();
        boolean[] visited = new boolean[n];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        mst.add(0); // Start from the first city
        visited[0] = true;
        // find the minimum spanning tree using Prim's
        while (mst.size() < n) {
            int current = mst.get(mst.size() - 1);
            for (Edge neighbor : graph.get(current)) {
                if (!visited[neighbor.to]) {
                    pq.add(neighbor);
                }
            }

            Edge minEdge = pq.poll();
            while (visited[minEdge.to]) {
                minEdge = pq.poll();
            }

            mst.add(minEdge.to);
            visited[minEdge.to] = true;
        }

        // Convert the minimum spanning tree to a tour
        List<Integer> tour = new ArrayList<>(mst);
        tour.add(0); // Return to the starting city to complete the tour

        // Print the order of cities in the shortest path
        List<String> shortestPath = new ArrayList<>();
        for (int index : tour) {
            shortestPath.add(cityNames[index]);
        }

        // Calculate the total distance of path
        double totalDistance = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            totalDistance += distances[tour.get(i)][tour.get(i + 1)];
        }

        // Print the order of cities in the shortest path and total distance
        System.out.println("Order of cities in the shortest path: ");
        for (String cityName : shortestPath) {
            System.out.println(cityName + " (" + cities.get(cityName).x + ", " + cities.get(cityName).y + ")");
        }
        System.out.println("Total distance: " + totalDistance);
    }
}
