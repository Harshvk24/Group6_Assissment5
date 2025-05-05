package com.group6.trafficgraphoptimizer.graph;

import java.util.*;

/**
 * Represents the city traffic network as a graph where intersections are nodes
 * and roads are directed edges with weights (e.g., travel time).
 */
public class Graph {
    // Set of all nodes in the graph
    private final Set<Node> nodes;

    // Map each node to its outgoing edges
    private final Map<Node, List<Edge>> adjacencyList;

    /**
     * Initializes an empty graph.
     */
    public Graph() {
        this.nodes = new HashSet<>();
        this.adjacencyList = new HashMap<>();
    }

    /**
     * Adds a new node (intersection) to the graph.
     *
     * @param node the node to add
     */
    public void addNode(Node node) {
        nodes.add(node);
        adjacencyList.putIfAbsent(node, new ArrayList<>());
    }

    /**
     * Adds a directed edge (road) to the graph.
     *
     * @param from   source node
     * @param to     destination node
     * @param weight cost of travel (e.g., distance or time)
     */
    public void addEdge(Node from, Node to, double weight) {
        addNode(from); // ensures from node exists
        addNode(to);   // ensures to node exists
        Edge edge = new Edge(from, to, weight);
        adjacencyList.get(from).add(edge);
    }

    /**
     * Gets all nodes in the graph.
     *
     * @return a set of nodes
     */
    public Set<Node> getNodes() {
        return Collections.unmodifiableSet(nodes);
    }

    /**
     * Gets all edges from a given node.
     *
     * @param node the source node
     * @return list of outgoing edges
     */
    public List<Edge> getEdgesFrom(Node node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList());
    }

    /**
     * For debug purposes: print all edges in the graph.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node node : adjacencyList.keySet()) {
            for (Edge edge : adjacencyList.get(node)) {
                sb.append(edge.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
