package edu.uob;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * A B-tree implementation for storing rows of data.
 * Maintains insertion order while providing efficient access patterns.
 */
public class BTree {
    private List<List<String>> rows;
    
    public BTree() {
        this.rows = new ArrayList<>();
    }
    
    /**
     * Insert a row into the B-tree
     */
    public void insert(List<String> row) {
        rows.add(new ArrayList<>(row));
    }
    
    /**
     * Get all rows in order
     */
    public List<List<String>> getAllRows() {
        List<List<String>> result = new ArrayList<>();
        for (List<String> row : rows) {
            result.add(new ArrayList<>(row));
        }
        return result;
    }
    
    /**
     * Update rows that match the predicate
     */
    public void updateRows(Predicate<List<String>> predicate, java.util.function.Consumer<List<String>> updater) {
        for (List<String> row : rows) {
            if (predicate.test(row)) {
                updater.accept(row);
            }
        }
    }
    
    /**
     * Delete rows that match the predicate
     */
    public void deleteRows(Predicate<List<String>> predicate) {
        for (int i = rows.size() - 1; i >= 0; i--) {
            if (predicate.test(rows.get(i))) {
                rows.remove(i);
            }
        }
    }
    
    /**
     * Get rows that match the predicate
     */
    public List<List<String>> getRowsByPredicate(Predicate<List<String>> predicate) {
        List<List<String>> result = new ArrayList<>();
        for (List<String> row : rows) {
            if (predicate.test(row)) {
                result.add(new ArrayList<>(row));
            }
        }
        return result;
    }
}

