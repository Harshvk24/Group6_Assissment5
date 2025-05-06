
package com.group6.trafficgraphoptimizer.metrics;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Utility class for recording and exporting experiment metrics like runtime,
 * path length, and path cost for route evaluations.
 */
public class MetricsLogger {

    private final List<String[]> records;
    private final String[] header = { "Start", "End", "PathLength", "TotalCost", "ExecutionTime(ms)" };

    /**
     * Constructs a new MetricsLogger instance to track metrics for one or more runs.
     */
    public MetricsLogger() {
        this.records = new ArrayList<>();
        records.add(header); // Add CSV header
    }

    /**
     * Records a new metric entry.
     *
     * @param start         ID of the starting node
     * @param end           ID of the ending node
     * @param pathLength    Number of nodes in the path
     * @param totalCost     Total weight of the path
     * @param executionTime Time taken to compute the path (in milliseconds)
     */
    public void log(String start, String end, int pathLength, double totalCost, long executionTime) {
        records.add(new String[] {
                start,
                end,
                String.valueOf(pathLength),
                String.format("%.2f", totalCost),
                String.valueOf(executionTime)
        });
    }

    /**
     * Exports all recorded metrics to a CSV file.
     *
     * @param filePath output file path (e.g., "results/metrics.csv")
     */
    public void exportToCSV(String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeAll(records);
            System.out.println("Metrics exported to " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to export metrics: " + e.getMessage());
        }
    }
}
