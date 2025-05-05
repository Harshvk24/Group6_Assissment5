package com.group6.trafficgraphoptimizer.graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Node class.
 */
public class NodeTest {

    @Test
    void testNodeId() {
        Node node = new Node("X");
        assertEquals("X", node.getId(), "Node ID should match the constructor input");
    }

    @Test
    void testEquality() {
        Node a = new Node("A");
        Node a2 = new Node("A");
        Node b = new Node("B");

        assertEquals(a, a2, "Nodes with same ID should be equal");
        assertNotEquals(a, b, "Nodes with different IDs should not be equal");
    }

    @Test
    void testToString() {
        Node node = new Node("Z");
        assertEquals("Z", node.toString(), "toString should return the node ID");
    }

    @Test
    void testHashCodeConsistency() {
        Node n1 = new Node("Y");
        Node n2 = new Node("Y");
        assertEquals(n1.hashCode(), n2.hashCode(), "Equal nodes must have the same hashCode");
    }
}

