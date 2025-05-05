package com.group6.trafficgraphoptimizer.graph;

/**
 * Represents a directional road segment from one node to another with a cost.
 * The cost can represent distance, time, or adjusted traffic weight.
 */
public class Edge {
    private final Node from;
    private final Node to;
    private double weight;

    /**
     * Constructs a new edge from one node to another with the given weight.
     *
     * @param from   Starting node (source intersection)
     * @param to     Ending node (destination intersection)
     * @param weight Cost (e.g., time in minutes, or dynamic weight)
     */
    public Edge(Node from, Node to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return from + " -> " + to + " (" + weight + ")";
    }
}
