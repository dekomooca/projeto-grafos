

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
        int ini = 10; 
        int fim = 200; 
        int stp = 10;
        double p = 0.1; 

        Locale.setDefault(Locale.US);

        System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%n",
                "V", "E", "gmin", "gmax", "gmed", "diam");
        System.out.println("------------------------------------------------------------");

        int n = ini;

        while (n <= fim) {

            List<int[]> graph = generateRandomGraph(n, p);

            int vertices = n;
            int arestas = graph.size();
            int gmin = calculateMinDegree(graph, n);
            int gmax = calculateMaxDegree(graph, n);
            double gmed = calculateAverageDegree(graph, n);
            int diam = calculateDiameter(graph, n);

            System.out.printf("%-15d%-15d%-15d%-15d%-15.2f%-15d%n",
                    vertices, arestas, gmin, gmax, gmed, diam);

            n += stp; 
        }
    }

    private static List<int[]> generateRandomGraph(int n, double p) {
        List<int[]> graph = new ArrayList<>();
        Random random = new Random();

        for (int u = 0; u < n; u++) {
            for (int v = u + 1; v < n; v++) {
                if (random.nextDouble() <= p) {
                    graph.add(new int[]{u, v});
                }
            }
        }

        return graph;
    }

    private static int calculateMinDegree(List<int[]> graph, int n) {
    	int minDegree = Integer.MAX_VALUE;
    	int[] degree = calculateDegree(graph, n);
        for (int d : degree) {
            if (d < minDegree) {
                minDegree = d;
            }
        }
        return minDegree;
    }

    private static int calculateMaxDegree(List<int[]> graph, int n) {
    	int maxDegree = 0;
    	int[] degree = calculateDegree(graph, n);
        for (int d : degree) {
            if (d > maxDegree) {
                maxDegree = d;
            }
        }
        return maxDegree;
    }

    private static double calculateAverageDegree(List<int[]> graph, int n) {
    	double sumDegree = 0;
    	int[] degree = calculateDegree(graph, n);
        
        for (int d : degree) {
            sumDegree += d;
        }
        return sumDegree / n;
    }

    private static int[] calculateDegree(List<int[]> graph, int n) {
    	int[] degree = new int[n];
        for (int[] edge : graph) {
            degree[edge[0]]++;
            degree[edge[1]]++;
        }
		return degree;
	}

	private static int calculateDiameter(List<int[]> graph, int n) {
        int diameter = 0;

        for (int i = 0; i < n; i++) {
            int[] distances = bfs(graph, n, i);
            int maxDistance = Arrays.stream(distances).max().orElse(0);

            if (maxDistance == Integer.MAX_VALUE) {
                return -1;
            }

            diameter = Math.max(diameter, maxDistance);
        }

        return diameter;
    }

    private static int[] bfs(List<int[]> graph, int n, int start) {
        int[] distances = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        distances[start] = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int[] edge : graph) {
                if (edge[0] == current && distances[edge[1]] == Integer.MAX_VALUE) {
                    distances[edge[1]] = distances[current] + 1;
                    queue.add(edge[1]);
                } else if (edge[1] == current && distances[edge[0]] == Integer.MAX_VALUE) {
                    distances[edge[0]] = distances[current] + 1;
                    queue.add(edge[0]);
                }
            }
        }

        return distances;
    }
}
