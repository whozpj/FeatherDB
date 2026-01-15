package edu.uob;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Performance benchmark comparing B-tree vs ArrayList for row storage
 */
public class PerformanceBenchmarkTest {

    private static final int SMALL_DATASET = 1000;
    private static final int MEDIUM_DATASET = 10000;
    private static final int LARGE_DATASET = 100000;

    @Test
    public void benchmarkInsertionPerformance() {
        System.out.println("\n=== INSERTION PERFORMANCE BENCHMARK ===\n");

        // Test with small dataset
        System.out.println("Small Dataset (" + SMALL_DATASET + " rows):");
        benchmarkInsertionForSize(SMALL_DATASET);

        // Test with medium dataset
        System.out.println("\nMedium Dataset (" + MEDIUM_DATASET + " rows):");
        benchmarkInsertionForSize(MEDIUM_DATASET);

        // Test with large dataset
        System.out.println("\nLarge Dataset (" + LARGE_DATASET + " rows):");
        benchmarkInsertionForSize(LARGE_DATASET);
    }

    private void benchmarkInsertionForSize(int size) {
        // Benchmark B-tree insertion
        long bTreeStart = System.nanoTime();
        BTree bTree = new BTree();
        for (int i = 0; i < size; i++) {
            List<String> row = createTestRow(i);
            bTree.insert(row);
        }
        long bTreeTime = System.nanoTime() - bTreeStart;

        // Benchmark ArrayList insertion
        long arrayListStart = System.nanoTime();
        List<List<String>> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<String> row = createTestRow(i);
            arrayList.add(row);
        }
        long arrayListTime = System.nanoTime() - arrayListStart;

        System.out.printf("  B-tree:    %,d ns (%.2f ms)\n", bTreeTime, bTreeTime / 1_000_000.0);
        System.out.printf("  ArrayList: %,d ns (%.2f ms)\n", arrayListTime, arrayListTime / 1_000_000.0);
        System.out.printf("  Ratio: B-tree is %.2fx %s than ArrayList\n",
                Math.max(bTreeTime, arrayListTime) / (double) Math.min(bTreeTime, arrayListTime),
                bTreeTime > arrayListTime ? "slower" : "faster");
    }

    @Test
    public void benchmarkRetrievalPerformance() {
        System.out.println("\n=== RETRIEVAL PERFORMANCE BENCHMARK ===\n");

        // Test with small dataset
        System.out.println("Small Dataset (" + SMALL_DATASET + " rows):");
        benchmarkRetrievalForSize(SMALL_DATASET);

        // Test with medium dataset
        System.out.println("\nMedium Dataset (" + MEDIUM_DATASET + " rows):");
        benchmarkRetrievalForSize(MEDIUM_DATASET);

        // Test with large dataset
        System.out.println("\nLarge Dataset (" + LARGE_DATASET + " rows):");
        benchmarkRetrievalForSize(LARGE_DATASET);
    }

    private void benchmarkRetrievalForSize(int size) {
        // Setup B-tree
        BTree bTree = new BTree();
        for (int i = 0; i < size; i++) {
            bTree.insert(createTestRow(i));
        }

        // Setup ArrayList
        List<List<String>> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arrayList.add(createTestRow(i));
        }

        // Benchmark B-tree retrieval (getAllRows)
        long bTreeStart = System.nanoTime();
        List<List<String>> bTreeRows = bTree.getAllRows();
        long bTreeTime = System.nanoTime() - bTreeStart;

        // Benchmark ArrayList iteration
        long arrayListStart = System.nanoTime();
        List<List<String>> copiedRows = new ArrayList<>(arrayList);
        long arrayListTime = System.nanoTime() - arrayListStart;

        System.out.printf("  B-tree:    %,d ns (%.2f ms)\n", bTreeTime, bTreeTime / 1_000_000.0);
        System.out.printf("  ArrayList: %,d ns (%.2f ms)\n", arrayListTime, arrayListTime / 1_000_000.0);
        System.out.printf("  Ratio: B-tree is %.2fx %s than ArrayList\n",
                Math.max(bTreeTime, arrayListTime) / (double) Math.min(bTreeTime, arrayListTime),
                bTreeTime > arrayListTime ? "slower" : "faster");

        assertEquals(size, bTreeRows.size(), "B-tree should return correct number of rows");
        assertEquals(size, copiedRows.size(), "ArrayList should return correct number of rows");
    }

    @Test
    public void benchmarkUpdatePerformance() {
        System.out.println("\n=== UPDATE PERFORMANCE BENCHMARK ===\n");

        // Test with small dataset
        System.out.println("Small Dataset (" + SMALL_DATASET + " rows):");
        benchmarkUpdateForSize(SMALL_DATASET);

        // Test with medium dataset
        System.out.println("\nMedium Dataset (" + MEDIUM_DATASET + " rows):");
        benchmarkUpdateForSize(MEDIUM_DATASET);

        // Test with large dataset
        System.out.println("\nLarge Dataset (" + LARGE_DATASET + " rows):");
        benchmarkUpdateForSize(LARGE_DATASET);
    }

    private void benchmarkUpdateForSize(int size) {
        // Setup B-tree
        BTree bTree = new BTree();
        for (int i = 0; i < size; i++) {
            bTree.insert(createTestRow(i));
        }

        // Setup ArrayList
        List<List<String>> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arrayList.add(createTestRow(i));
        }

        // Benchmark B-tree update (update first 10% of rows)
        long bTreeStart = System.nanoTime();
        bTree.updateRows(
                row -> Integer.parseInt(row.get(0)) < size / 10,
                row -> row.set(1, "UPDATED")
        );
        long bTreeTime = System.nanoTime() - bTreeStart;

        // Benchmark ArrayList update (update first 10% of rows)
        long arrayListStart = System.nanoTime();
        for (List<String> row : arrayList) {
            if (Integer.parseInt(row.get(0)) < size / 10) {
                row.set(1, "UPDATED");
            }
        }
        long arrayListTime = System.nanoTime() - arrayListStart;

        System.out.printf("  B-tree:    %,d ns (%.2f ms)\n", bTreeTime, bTreeTime / 1_000_000.0);
        System.out.printf("  ArrayList: %,d ns (%.2f ms)\n", arrayListTime, arrayListTime / 1_000_000.0);
        System.out.printf("  Ratio: B-tree is %.2fx %s than ArrayList\n",
                Math.max(bTreeTime, arrayListTime) / (double) Math.min(bTreeTime, arrayListTime),
                bTreeTime > arrayListTime ? "slower" : "faster");
    }

    @Test
    public void benchmarkDeletePerformance() {
        System.out.println("\n=== DELETE PERFORMANCE BENCHMARK ===\n");

        // Test with small dataset
        System.out.println("Small Dataset (" + SMALL_DATASET + " rows):");
        benchmarkDeleteForSize(SMALL_DATASET);

        // Test with medium dataset
        System.out.println("\nMedium Dataset (" + MEDIUM_DATASET + " rows):");
        benchmarkDeleteForSize(MEDIUM_DATASET);

        // Test with large dataset
        System.out.println("\nLarge Dataset (" + LARGE_DATASET + " rows):");
        benchmarkDeleteForSize(LARGE_DATASET);
    }

    private void benchmarkDeleteForSize(int size) {
        // Setup B-tree
        BTree bTree = new BTree();
        for (int i = 0; i < size; i++) {
            bTree.insert(createTestRow(i));
        }

        // Setup ArrayList
        List<List<String>> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arrayList.add(createTestRow(i));
        }

        // Benchmark B-tree delete (delete first 10% of rows)
        long bTreeStart = System.nanoTime();
        bTree.deleteRows(row -> Integer.parseInt(row.get(0)) < size / 10);
        long bTreeTime = System.nanoTime() - bTreeStart;

        // Benchmark ArrayList delete (delete first 10% of rows)
        long arrayListStart = System.nanoTime();
        for (int i = arrayList.size() - 1; i >= 0; i--) {
            if (Integer.parseInt(arrayList.get(i).get(0)) < size / 10) {
                arrayList.remove(i);
            }
        }
        long arrayListTime = System.nanoTime() - arrayListStart;

        System.out.printf("  B-tree:    %,d ns (%.2f ms)\n", bTreeTime, bTreeTime / 1_000_000.0);
        System.out.printf("  ArrayList: %,d ns (%.2f ms)\n", arrayListTime, arrayListTime / 1_000_000.0);
        System.out.printf("  Ratio: B-tree is %.2fx %s than ArrayList\n",
                Math.max(bTreeTime, arrayListTime) / (double) Math.min(bTreeTime, arrayListTime),
                bTreeTime > arrayListTime ? "slower" : "faster");
    }

    @Test
    public void benchmarkMemoryUsage() {
        System.out.println("\n=== MEMORY USAGE BENCHMARK ===\n");

        int[] sizes = {SMALL_DATASET, MEDIUM_DATASET, LARGE_DATASET};
        String[] labels = {"Small", "Medium", "Large"};

        for (int i = 0; i < sizes.length; i++) {
            System.out.println(labels[i] + " Dataset (" + sizes[i] + " rows):");
            benchmarkMemoryForSize(sizes[i]);
            System.out.println();
        }
    }

    private void benchmarkMemoryForSize(int size) {
        Runtime runtime = Runtime.getRuntime();

        // Test B-tree memory
        runtime.gc();
        long bTreeMemBefore = runtime.totalMemory() - runtime.freeMemory();
        BTree bTree = new BTree();
        for (int i = 0; i < size; i++) {
            bTree.insert(createTestRow(i));
        }
        long bTreeMemAfter = runtime.totalMemory() - runtime.freeMemory();
        long bTreeMemUsed = bTreeMemAfter - bTreeMemBefore;

        // Test ArrayList memory
        runtime.gc();
        long arrayListMemBefore = runtime.totalMemory() - runtime.freeMemory();
        List<List<String>> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arrayList.add(createTestRow(i));
        }
        long arrayListMemAfter = runtime.totalMemory() - runtime.freeMemory();
        long arrayListMemUsed = arrayListMemAfter - arrayListMemBefore;

        System.out.printf("  B-tree:    %,d bytes (%.2f KB)\n", bTreeMemUsed, bTreeMemUsed / 1024.0);
        System.out.printf("  ArrayList: %,d bytes (%.2f KB)\n", arrayListMemUsed, arrayListMemUsed / 1024.0);
        System.out.printf("  Ratio: B-tree is %.2fx %s memory than ArrayList\n",
                Math.max(bTreeMemUsed, arrayListMemUsed) / (double) Math.min(bTreeMemUsed, arrayListMemUsed),
                bTreeMemUsed > arrayListMemUsed ? "more" : "less");
    }

    private List<String> createTestRow(int id) {
        List<String> row = new ArrayList<>();
        row.add(String.valueOf(id));
        row.add("Name_" + id);
        row.add("Value_" + (id % 100));
        row.add("Status_" + (id % 5));
        return row;
    }
}
