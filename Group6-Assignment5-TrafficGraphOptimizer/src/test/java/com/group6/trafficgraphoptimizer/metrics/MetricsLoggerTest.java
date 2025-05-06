
package com.group6.trafficgraphoptimizer.metrics;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MetricsLogger class.
 */
public class MetricsLoggerTest {

    private MetricsLogger logger;
    private static final String TEST_CSV_PATH = "results/test_metrics.csv";

    @BeforeEach
    void setUp() {
        logger = new MetricsLogger();
    }

    @Test
    void testLogEntryAddsToRecords() {
        logger.log("X", "Y", 3, 12.5, 50);
        logger.log("A", "B", 4, 10.0, 30);

        logger.exportToCSV(TEST_CSV_PATH);
        File file = new File(TEST_CSV_PATH);
        assertTrue(file.exists(), "CSV file should be created");
    }

    @Test
    void testCSVContainsExpectedLines() throws IOException {
        logger.log("Start", "End", 2, 5.5, 100);
        logger.exportToCSV(TEST_CSV_PATH);

        List<String> lines = Files.readAllLines(Paths.get(TEST_CSV_PATH));
        assertTrue(lines.size() >= 2, "CSV should contain at least header and one record");
        assertTrue(lines.get(1).contains("Start"), "CSV line should include node IDs");
    }

    @AfterEach
    void cleanUp() {
        File file = new File(TEST_CSV_PATH);
        if (file.exists()) file.delete();
    }
}
