package com.group6.trafficgraphoptimizer.router;

import com.group6.trafficgraphoptimizer.graph.Graph;
import com.group6.trafficgraphoptimizer.graph.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the AdaptiveRouter class.
 */
public class AdaptiveRouterTest {

    private Graph graph;
    private AdaptiveRouter router;
    private Node A, B, C, D;

    @BeforeEach
    void setUp() {
        graph = new Graph();
        A = new Node("A");
        B = new Node("B");
        C = new Node("C");
        D = new Node("D");

        graph.addEdge(A, B, 1.0);
        graph.addEdge(B, C, 1.0);
        graph.addEdge(C, D, 1.0);
        graph.addEdge(A, D, 5.0);

        router = new AdaptiveRouter(graph);
    }

    @Test
    void testFindShortestPathExists() {
        List<Node> path = router.findShortestPath(A, D);
        assertFalse(path.isEmpty(), "Path should exist from A to D");
        assertEquals(A, path.get(0), "Path should start at A");
        assertEquals(D, path.get(path.size() - 1), "Path should end at D");
    }

    @Test
    void testFindShortestPathNoConnection() {
        Node E = new Node("E"); // Isolated node
        List<Node> path = router.findShortestPath(A, E);
        assertTrue(path.isEmpty(), "No path should exist to disconnected node");
    }

    @Test
    void testPathContainsExpectedNodes() {
        List<Node> path = router.findShortestPath(A, C);
        assertTrue(path.contains(A), "Path should include start node A");
        assertTrue(path.contains(C), "Path should include target node C");
    }

    @Test
    void testRoutingIsConsistentForConnectedNodes() {
        for (int i = 0; i < 5; i++) {
            List<Node> path = router.findShortestPath(A, D);
            assertFalse(path.isEmpty(), "Should consistently find a path");
        }
    }
}
