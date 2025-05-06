package com.group6.trafficgraphoptimizer.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Graph class.
 */
public class GraphTest {

    private Graph graph;
    private Node A, B, C;

    @BeforeEach
    void setup() {
        graph = new Graph();
        A = new Node("A");
        B = new Node("B");
        C = new Node("C");
    }

    @Test
    void testAddNode() {
        graph.addNode(A);
        Set<Node> nodes = graph.getNodes();
        assertTrue(nodes.contains(A), "Graph should contain the added node");
    }

    @Test
    void testAddEdge() {
        graph.addEdge(A, B, 5.0);
        List<Edge> edgesFromA = graph.getEdgesFrom(A);
        assertEquals(1, edgesFromA.size(), "A should have one outgoing edge");
        assertEquals(B, edgesFromA.get(0).getTo(), "Edge should go from A to B");
        assertEquals(5.0, edgesFromA.get(0).getWeight(), 0.01, "Edge weight should match");
    }

    @Test
    void testMultipleEdges() {
        graph.addEdge(A, B, 3.0);
        graph.addEdge(A, C, 7.0);

        List<Edge> edgesFromA = graph.getEdgesFrom(A);
        assertEquals(2, edgesFromA.size(), "A should have two outgoing edges");

        assertTrue(edgesFromA.stream().anyMatch(e -> e.getTo().equals(B)));
        assertTrue(edgesFromA.stream().anyMatch(e -> e.getTo().equals(C)));
    }

    @Test
    void testToString() {
        graph.addEdge(A, B, 2.0);
        String output = graph.toString();
        assertTrue(output.contains("A -> B"), "toString should include edge from A to B");
    }

    @Test
    void testGetEdgesFromEmptyNode() {
        List<Edge> edges = graph.getEdgesFrom(C);
        assertNotNull(edges, "Should return an empty list, not null");
        assertTrue(edges.isEmpty(), "Unconnected node should have no edges");
    }
}
