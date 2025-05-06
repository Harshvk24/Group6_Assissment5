package com.group6.trafficgraphoptimizer.router;

import com.group6.trafficgraphoptimizer.graph.Edge;
import com.group6.trafficgraphoptimizer.graph.Graph;
import com.group6.trafficgraphoptimizer.graph.Node;

import java.util.*;

/**
 * An adaptive traffic-aware routing engine using Dijkstra's algorithm as a base.
 * This version allows for dynamic weight adjustments to model changing traffic.
 */
public class AdaptiveRouter {

    private final Graph graph;

    /**
     * Constructs an AdaptiveRouter over a traffic graph.
     *
     * @param graph the graph representing the road network
     */
    public AdaptiveRouter(Graph graph) {
        this.graph = graph;
    }

    /**
     * Finds the shortest path between two intersections using a modified Dijkstra's algorithm.
     *
     * @param start the starting intersection
     * @param end   the target intersection
     * @return the list of nodes representing the optimal path, or empty if none found
     */
    public List<Node> findShortestPath(Node start, Node end) {
        Map<Node, Double> distances = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(distances::get));

        for (Node node : graph.getNodes()) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }
        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.equals(end)) break;

            for (Edge edge : graph.getEdgesFrom(current)) {
                double adjustedWeight = adjustWeight(edge); // dynamic adaptation
                double newDist = distances.get(current) + adjustedWeight;
                if (newDist < distances.get(edge.getTo())) {
                    distances.put(edge.getTo(), newDist);
                    previous.put(edge.getTo(), current);
                    queue.add(edge.getTo());
                }
            }
        }

        return reconstructPath(previous, start, end);
    }

    /**
     * Adjusts the edge weight dynamically — this is the "smart" part of the algorithm.
     * In real-world use, this might include congestion data, weather, etc.
     *
     * @param edge the original edge
     * @return adjusted cost
     */
    private double adjustWeight(Edge edge) {
        // Example logic: simulate congestion by increasing cost randomly
        double trafficFactor = 1.0 + Math.random() * 0.2; // ±20% weight fluctuation
        return edge.getWeight() * trafficFactor;
    }

    /**
     * Reconstructs the path from start to end based on backtracking through the `previous` map.
     *
     * @param previous the map of node predecessors
     * @param start    start node
     * @param end      end node
     * @return ordered list of nodes in the path
     */
    private List<Node> reconstructPath(Map<Node, Node> previous, Node start, Node end) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = end;
        while (current != null && !current.equals(start)) {
            path.addFirst(current);
            current = previous.get(current);
        }
        if (current == null) return Collections.emptyList(); // no path found
        path.addFirst(start);
        return path;
    }
}
