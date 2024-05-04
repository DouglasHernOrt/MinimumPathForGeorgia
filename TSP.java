package finalProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TSP {
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
    // swap utility function
    static void swap(int[] path, int i, int j) {
        int temp = path[i];
        path[i] = path[j];
        path[j] = temp;
    }
    // function that calculates total distance of a path
    static double totalDistance(Point[] points, int[] path) {
        double total = 0;
        for (int i = 0; i < path.length - 1; i++) {
            total += distance(points[path[i]], points[path[i + 1]]);
        }
        total += distance(points[path[path.length - 1]], points[path[0]]);
        return total;
    }
    
    // create permutations of every possible city path
    static void cityPermutations(int[] path, ArrayList<int[]> paths, Point[] points, int start) {
        if (start == path.length - 2) {
            int[] newPath = Arrays.copyOf(path, path.length);
            paths.add(newPath);
            return;
        }

        for (int i = start; i < path.length - 1; i++) {
            swap(path, i, start);
            cityPermutations(path, paths, points, start + 1);
            swap(path, i, start);
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
        // number of cities
        int n = cities.size();
        // arrays of city names and coordinates
        String[] cityNames = cities.keySet().toArray(new String[0]);
        Point[] points = cities.values().toArray(new Point[0]);

        int[] path = new int[n + 1]; // includes an extra slot for the starting city at the end
        for (int i = 0; i < n; i++) {
            path[i] = i;
        }
        path[n] = 0; // Starting city index at the end

        ArrayList<int[]> paths = new ArrayList<>();
        // generate permutation of city paths
        cityPermutations(path, paths, points, 1);

        double minDistance = Double.MAX_VALUE;
        int[] shortestPath = null;
        // find the shortest path
        for (int[] p : paths) {
            double currentDistance = totalDistance(points, p);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                shortestPath = p;
            }
        }
        // output
        System.out.println("Shortest path returning to the original city: ");
        for (int i = 0; i < shortestPath.length - 1; i++) {
            int index = shortestPath[i];
            String cityName = cityNames[index];
            System.out.println(cityName + " (" + points[index].x + ", " + points[index].y + ")");
        }

        System.out.println(cityNames[0] + " (" + points[0].x + ", " + points[0].y + ")"); // Starting city

        System.out.println("Total distance: " + minDistance);
    }
}

