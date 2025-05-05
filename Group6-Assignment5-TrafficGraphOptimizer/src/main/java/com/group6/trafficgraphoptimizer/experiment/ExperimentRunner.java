package com.group6.trafficgraphoptimizer.experiment;

import com.group6.trafficgraphoptimizer.graph.*;
import com.group6.trafficgraphoptimizer.router.*;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Extended ExperimentRunner that logs:
 * - Path length
 * - Cost improvement
 * - Time improvement
 */
public class ExperimentRunner {

    private static final int NUM_RUNS = 100;
    private static final int MIN_NODES = 200;
    private static final int MAX_NODES = 300;
    private static final int MIN_EDGES = 500;
    private static final int MAX_EDGES = 1000;

    private static final String[] CSV_HEADER = {
            "Run", "Start", "End",
            "StaticCost", "StaticTime(ms)", "StaticPathLen",
            "AdaptiveCost", "AdaptiveTime(ms)", "AdaptivePathLen",
            "CostDelta(%)", "TimeDelta(%)"
    };

    public static void main(String[] args) {
        List<String[]> records = new ArrayList<>();
        records.add(CSV_HEADER);

        for (int i = 1; i <= NUM_RUNS; i++) {
            Graph graph = generateRandomGraph();
            List<Node> nodeList = new ArrayList<>(graph.getNodes());
            Node start = getRandomNode(nodeList);
            Node end = getRandomNode(nodeList);
            while (end.equals(start)) {
                end = getRandomNode(nodeList);
            }

            // Static routing
            StaticRouter staticRouter = new StaticRouter(graph);
            long staticStart = System.nanoTime();
            List<Node> staticPath = staticRouter.findShortestPath(start, end);
            long staticEnd = System.nanoTime();
            double staticTime = (staticEnd - staticStart) / 1_000_000.0;
            double staticCost = calculatePathCost(staticPath, graph);
            int staticLen = staticPath.size();

            // Adaptive routing
            AdaptiveRouter adaptiveRouter = new AdaptiveRouter(graph);
            long adaptiveStart = System.nanoTime();
            List<Node> adaptivePath = adaptiveRouter.findShortestPath(start, end);
            long adaptiveEnd = System.nanoTime();
            double adaptiveTime = (adaptiveEnd - adaptiveStart) / 1_000_000.0;
            double adaptiveCost = calculatePathCost(adaptivePath, graph);
            int adaptiveLen = adaptivePath.size();

            double costDelta = staticCost > 0 ? ((staticCost - adaptiveCost) / staticCost) * 100 : 0;
            double timeDelta = staticTime > 0 ? ((staticTime - adaptiveTime) / staticTime) * 100 : 0;

            records.add(new String[] {
                    String.valueOf(i),
                    start.getId(), end.getId(),
                    String.format("%.3f", staticCost),
                    String.format("%.3f", staticTime),
                    String.valueOf(staticLen),
                    String.format("%.3f", adaptiveCost),
                    String.format("%.3f", adaptiveTime),
                    String.valueOf(adaptiveLen),
                    String.format("%.2f", costDelta),
                    String.format("%.2f", timeDelta)
            });

            System.out.printf("Run %3d | ΔCost: %6.2f%% | ΔTime: %6.2f%%\n", i, costDelta, timeDelta);
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter("results/compare_metrics.csv"))) {
            writer.writeAll(records);
            System.out.println("✅ Extended results written to results/compare_metrics.csv");
        } catch (IOException e) {
            System.err.println("❌ Failed to write CSV: " + e.getMessage());
        }
    }

    private static Graph generateRandomGraph() {
        Graph graph = new Graph();
        Random rand = new Random();
        int numNodes = rand.nextInt(MAX_NODES - MIN_NODES + 1) + MIN_NODES;
        int numEdges = rand.nextInt(MAX_EDGES - MIN_EDGES + 1) + MIN_EDGES;

        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < numNodes; i++) {
            Node node = new Node("N" + i);
            nodes.add(node);
            graph.addNode(node);
        }

        for (int i = 0; i < numEdges; i++) {
            Node from = nodes.get(rand.nextInt(numNodes));
            Node to = nodes.get(rand.nextInt(numNodes));
            if (!from.equals(to)) {
                double weight = 1.0 + rand.nextDouble() * 9.0;
                graph.addEdge(from, to, weight);
            }
        }

        return graph;
    }

    private static double calculatePathCost(List<Node> path, Graph graph) {
        double total = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            for (Edge edge : graph.getEdgesFrom(path.get(i))) {
                if (edge.getTo().equals(path.get(i + 1))) {
                    total += edge.getWeight();
                    break;
                }
            }
        }
        return path.isEmpty() ? Double.POSITIVE_INFINITY : total;
    }

    private static Node getRandomNode(List<Node> nodes) {
        return nodes.get(new Random().nextInt(nodes.size()));
    }
}
