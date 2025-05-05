package com.group6.trafficgraphoptimizer;

import com.group6.trafficgraphoptimizer.graph.Edge;
import com.group6.trafficgraphoptimizer.graph.Graph;
import com.group6.trafficgraphoptimizer.graph.Node;
import com.group6.trafficgraphoptimizer.router.AdaptiveRouter;
import com.group6.trafficgraphoptimizer.metrics.MetricsLogger;

import java.io.File;
import java.util.List;

/**
 * Main runner class for TrafficGraphOptimizer.
 * Builds a test graph, performs routing, and logs metrics.
 */
public class MainApp {

    public static void main(String[] args) {
        System.out.println(" TrafficGraphOptimizer is starting...");

        // Set up graph
        Graph graph = new Graph();
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");

        graph.addEdge(A, B, 4);
        graph.addEdge(B, C, 3);
        graph.addEdge(A, C, 10);
        graph.addEdge(C, D, 2);
        graph.addEdge(B, D, 6);

        // Run router
        AdaptiveRouter router = new AdaptiveRouter(graph);
        long startTime = System.currentTimeMillis();
        List<Node> path = router.findShortestPath(A, D);
        long endTime = System.currentTimeMillis();

        // Print path
        if (path.isEmpty()) {
            System.out.println(" No path found from A to D.");
        } else {
            System.out.println(" Path found from A to D:");
            path.forEach(n -> System.out.print(n + " "));
            System.out.println();
        }

        // Compute path cost
        double totalCost = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Node from = path.get(i);
            Node to = path.get(i + 1);
            for (Edge edge : graph.getEdgesFrom(from)) {
                if (edge.getTo().equals(to)) {
                    totalCost += edge.getWeight(); // NOTE: This is original weight, not adjusted
                    break;
                }
            }
        }

        // Log metrics
        MetricsLogger logger = new MetricsLogger();
        logger.log("A", "D", path.size(), totalCost, endTime - startTime);

        // Ensure output directory exists
        File resultsDir = new File("results");
        if (!resultsDir.exists()) {
            resultsDir.mkdir();
        }

        logger.exportToCSV("results/metrics.csv");

        System.out.println(" Finished.");
    }
}
