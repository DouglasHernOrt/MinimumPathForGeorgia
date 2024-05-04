package finalProject;

import java.util.*;

public class TSPnear {
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

    public static void main(String[] args) {
    	// map to store city names and coordinates
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
        String[] cityNames = cities.keySet().toArray(new String[0]); // list to store the tour path
        Point[] points = cities.values().toArray(new Point[0]); // list to keep track of visited cities

        List<Integer> tour = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        int startCity = 0; // starting city
        tour.add(startCity);
        visited.add(startCity);

        while (tour.size() < n) {
            int currentCity = tour.get(tour.size() - 1);
            double minDistance = Double.MAX_VALUE;
            int nearestCity = -1;
            
            // find the nearest unvisited city from the current city
            for (int i = 0; i < n; i++) {
                if (!visited.contains(i) && i != currentCity) {
                    double dist = distance(points[currentCity], points[i]);
                    if (dist < minDistance) {
                        minDistance = dist;
                        nearestCity = i;
                    }
                }
            }

            if (nearestCity != -1) {
                tour.add(nearestCity); // add nearest city to the tour
                visited.add(nearestCity); // Mark nearest city as visited
            }
        }

        // Add starting city again to complete the tour
        tour.add(startCity);

        // Print the tour
        System.out.println("Nearest Neighbor tour:");
        for (int i = 0; i < tour.size(); i++) {
            int index = tour.get(i);
            String cityName = cityNames[index];
            System.out.println(cityName + " (" + points[index].x + ", " + points[index].y + ")");
        }

        // Calculate the total distance of the tour
        double totalDistance = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            totalDistance += distance(points[tour.get(i)], points[tour.get(i + 1)]);
        }
        System.out.println("Total distance: " + totalDistance);
    }
}
