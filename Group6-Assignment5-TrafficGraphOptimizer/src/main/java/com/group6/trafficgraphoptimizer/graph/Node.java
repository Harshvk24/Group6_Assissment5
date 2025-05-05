package com.group6.trafficgraphoptimizer.graph;

/**
 * Represents a single intersection or point in the city road network.
 * Each node is uniquely identified by an ID or name.
 */
public class Node {
    private final String id;

    /**
     * Creates a new node with the given ID.
     *
     * @param id A unique identifier for this node (e.g., "A", "Intersection-1")
     */
    public Node(String id) {
        this.id = id;
    }

    /**
     * Gets the unique ID of the node.
     *
     * @return the node ID
     */
    public String getId() {
        return id;
    }

    /**
     * Custom toString for debug-friendly printing.
     *
     * @return Node as string
     */
    @Override
    public String toString() {
        return id;
    }

    /**
     * Two nodes are equal if they share the same ID.
     *
     * @param o another object
     * @return true if same ID
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node other = (Node) o;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

