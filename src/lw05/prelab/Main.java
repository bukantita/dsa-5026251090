package lw05.prelab;

import java.util.*;
import java.io.*;

public class Main {

    static int size;
    static String[] names;
    static int[][] graph;

    static void addEdge(int v1, int v2, int minutes) {
        graph[v1][v2] = minutes;
        graph[v2][v1] = minutes;
    }

    static void printPath(List<Integer> path) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print(names[path.get(i)]);
            if (i < path.size() - 1) {
                System.out.print(" -> ");
            }
        }
        System.out.println();
    }

    static List<Integer> bfs(int start) {
        List<Integer> order = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[size];

        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);

            for (int i = 0; i < size; i++) {
                if (graph[current][i] != 0 && !visited[i]) {
                    visited[i] = true;
                    queue.offer(i);
                }
            }
        }

        return order;
    }

    static void dfs(int current, Set<Integer> visited,
                    List<Integer> dfsOrder) {

        visited.add(current);
        dfsOrder.add(current);

        for (int i = 0; i < size; i++) {
            if (graph[current][i] != 0 && !visited.contains(i)) {
                dfs(i, visited, dfsOrder);
            }
        }
    }

    static int findTheNextNode(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE;
        int nextNode = -1;

        for (int i = 0; i < size; i++) {
            if (!visited[i] && dist[i] < min) {
                min = dist[i];
                nextNode = i;
            }
        }

        return nextNode;
    }

    static void route(int current, int[] prev) {
        if (prev[current] != -1) {
            route(prev[current], prev);
        }

        System.out.print(names[current]);

        if (current != findDestination) {
            System.out.print(" -> ");
        }
    }

    static int findDestination;

    static void dijkstraAlg(int start, int destination) {
        int[] dist = new int[size];
        int[] prev = new int[size];
        boolean[] visited = new boolean[size];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);

        dist[start] = 0;

        for (int i = 0; i < size; i++) {
            int current = findTheNextNode(dist, visited);

            if (current == -1) {
                break;
            }

            visited[current] = true;

            for (int j = 0; j < size; j++) {
                if (graph[current][j] != 0 && !visited[j]) {
                    int newDist = dist[current] + graph[current][j];

                    if (newDist < dist[j]) {
                        dist[j] = newDist;
                        prev[j] = current;
                    }
                }
            }
        }

        System.out.println("=== Shortest Time from " + names[start] + " ===");

        for (int i = 0; i < size; i++) {
            System.out.println(names[i] + " : " + dist[i] + " min");
        }

        System.out.println("=== Shortest Route from "
                + names[start] + " to " + names[destination] + " ===");

        findDestination = destination;
        route(destination, prev);
        System.out.println();

        System.out.println("Total time: " + dist[destination] + " min");
    }

    public static void main(String[] args) throws Exception {
        String fileName = args.length > 0 ? args[0] : "campus.txt";

        Scanner sc = new Scanner(Main.class.getResourceAsStream("input.txt"));

        size = sc.nextInt();
        names = new String[size];
        graph = new int[size][size];

        for (int i = 0; i < size; i++) {
            names[i] = sc.next();
        }

        int edges = sc.nextInt();

        for (int i = 0; i < edges; i++) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();
            int minutes = sc.nextInt();

            addEdge(v1, v2, minutes);
        }

        int start = sc.nextInt();
        int destination = sc.nextInt();

        sc.close();

        System.out.println("=== BFS from " + names[start] + " ===");
        printPath(bfs(start));

        System.out.println("=== DFS from " + names[start] + " ===");

        List<Integer> dfsOrder = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();

        dfs(start, visited, dfsOrder);
        printPath(dfsOrder);

        dijkstraAlg(start, destination);
    }
}

