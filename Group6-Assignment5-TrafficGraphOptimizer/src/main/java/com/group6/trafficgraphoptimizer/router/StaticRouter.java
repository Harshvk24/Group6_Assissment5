package com.group6.trafficgraphoptimizer.router;

import com.group6.trafficgraphoptimizer.graph.*;

import java.util.*;

/**
 * A traditional shortest-path router using standard Dijkstra's algorithm.
 * Used as a baseline to compare against the AdaptiveRouter.
 */
public class StaticRouter {

    private final Graph graph;

    /**
     * Constructs a StaticRouter over a given graph.
     *
     * @param graph the graph to route over
     */
    public StaticRouter(Graph graph) {
        this.graph = graph;
    }

    /**
     * Finds the shortest path from start to end using fixed edge weights.
     *
     * @param start the starting node
     * @param end   the destination node
     * @return list of nodes in the shortest path
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
                double newDist = distances.get(current) + edge.getWeight();
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
     * Reconstructs the shortest path based on the predecessor map.
     */
    private List<Node> reconstructPath(Map<Node, Node> previous, Node start, Node end) {
        LinkedList<Node> path = new LinkedList<>();
        Node current = end;
        while (current != null && !current.equals(start)) {
            path.addFirst(current);
            current = previous.get(current);
        }
        if (current == null) return Collections.emptyList();
        path.addFirst(start);
        return path;
    }
}
