package algorithms.dp;

import java.util.*;

/**
 * Dynamic Programming Masterclass
 * Comprehensive collection of classic DP problems with multiple approaches
 * 
 * Demonstrates:
 * - Memoization (Top-down)
 * - Tabulation (Bottom-up)
 * - Space optimization techniques
 */
public class DynamicProgramming {
    
    /**
     * Problem 1: Fibonacci Sequence
     * Multiple approaches to demonstrate DP concepts
     */
    public static class FibonacciSolutions {
        
        // Naive recursive - O(2^n) time, O(n) space
        public static long fibRecursive(int n) {
            if (n <= 1) return n;
            return fibRecursive(n - 1) + fibRecursive(n - 2);
        }
        
        // Memoization - O(n) time, O(n) space
        public static long fibMemo(int n) {
            Map<Integer, Long> memo = new HashMap<>();
            return fibMemoHelper(n, memo);
        }
        
        private static long fibMemoHelper(int n, Map<Integer, Long> memo) {
            if (n <= 1) return n;
            if (memo.containsKey(n)) return memo.get(n);
            
            long result = fibMemoHelper(n - 1, memo) + fibMemoHelper(n - 2, memo);
            memo.put(n, result);
            return result;
        }
        
        // Tabulation - O(n) time, O(n) space
        public static long fibTab(int n) {
            if (n <= 1) return n;
            
            long[] dp = new long[n + 1];
            dp[0] = 0;
            dp[1] = 1;
            
            for (int i = 2; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
            
            return dp[n];
        }
        
        // Space optimized - O(n) time, O(1) space
        public static long fibOptimized(int n) {
            if (n <= 1) return n;
            
            long prev2 = 0, prev1 = 1;
            
            for (int i = 2; i <= n; i++) {
                long current = prev1 + prev2;
                prev2 = prev1;
                prev1 = current;
            }
            
            return prev1;
        }
    }
    
    /**
     * Problem 2: Longest Common Subsequence
     */
    public static class LCSolutions {
        
        // Recursive solution
        public static int lcsRecursive(String text1, String text2) {
            return lcsHelper(text1, text2, 0, 0);
        }
        
        private static int lcsHelper(String s1, String s2, int i, int j) {
            if (i >= s1.length() || j >= s2.length()) return 0;
            
            if (s1.charAt(i) == s2.charAt(j)) {
                return 1 + lcsHelper(s1, s2, i + 1, j + 1);
            }
            
            return Math.max(lcsHelper(s1, s2, i + 1, j), lcsHelper(s1, s2, i, j + 1));
        }
        
        // Memoization
        public static int lcsMemo(String text1, String text2) {
            int[][] memo = new int[text1.length()][text2.length()];
            for (int[] row : memo) Arrays.fill(row, -1);
            return lcsMemoHelper(text1, text2, 0, 0, memo);
        }
        
        private static int lcsMemoHelper(String s1, String s2, int i, int j, int[][] memo) {
            if (i >= s1.length() || j >= s2.length()) return 0;
            if (memo[i][j] != -1) return memo[i][j];
            
            if (s1.charAt(i) == s2.charAt(j)) {
                memo[i][j] = 1 + lcsMemoHelper(s1, s2, i + 1, j + 1, memo);
            } else {
                memo[i][j] = Math.max(lcsMemoHelper(s1, s2, i + 1, j, memo), 
                                    lcsMemoHelper(s1, s2, i, j + 1, memo));
            }
            
            return memo[i][j];
        }
        
        // Tabulation with string reconstruction
        public static String lcsTabulation(String text1, String text2) {
            int m = text1.length(), n = text2.length();
            int[][] dp = new int[m + 1][n + 1];
            
            // Fill DP table
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            
            // Reconstruct LCS
            StringBuilder lcs = new StringBuilder();
            int i = m, j = n;
            
            while (i > 0 && j > 0) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    lcs.append(text1.charAt(i - 1));
                    i--;
                    j--;
                } else if (dp[i - 1][j] > dp[i][j - 1]) {
                    i--;
                } else {
                    j--;
                }
            }
            
            return lcs.reverse().toString();
        }
    }
    
    /**
     * Problem 3: 0/1 Knapsack Problem
     */
    public static class KnapsackSolutions {
        
        public static class Item {
            int weight, value;
            
            public Item(int weight, int value) {
                this.weight = weight;
                this.value = value;
            }
        }
        
        // Recursive solution
        public static int knapsackRecursive(Item[] items, int capacity) {
            return knapsackHelper(items, capacity, 0);
        }
        
        private static int knapsackHelper(Item[] items, int capacity, int index) {
            if (index >= items.length || capacity <= 0) return 0;
            
            // Don't include current item
            int exclude = knapsackHelper(items, capacity, index + 1);
            
            // Include current item if possible
            int include = 0;
            if (items[index].weight <= capacity) {
                include = items[index].value + 
                         knapsackHelper(items, capacity - items[index].weight, index + 1);
            }
            
            return Math.max(include, exclude);
        }
        
        // DP solution with item tracking
        public static Result knapsackDP(Item[] items, int capacity) {
            int n = items.length;
            int[][] dp = new int[n + 1][capacity + 1];
            
            // Fill DP table
            for (int i = 1; i <= n; i++) {
                for (int w = 1; w <= capacity; w++) {
                    if (items[i - 1].weight <= w) {
                        dp[i][w] = Math.max(dp[i - 1][w],
                                          dp[i - 1][w - items[i - 1].weight] + items[i - 1].value);
                    } else {
                        dp[i][w] = dp[i - 1][w];
                    }
                }
            }
            
            // Find selected items
            List<Integer> selectedItems = new ArrayList<>();
            int w = capacity;
            for (int i = n; i > 0 && dp[i][w] != 0; i--) {
                if (dp[i][w] != dp[i - 1][w]) {
                    selectedItems.add(i - 1);
                    w -= items[i - 1].weight;
                }
            }
            
            return new Result(dp[n][capacity], selectedItems);
        }
        
        public static class Result {
            int maxValue;
            List<Integer> selectedItems;
            
            public Result(int maxValue, List<Integer> selectedItems) {
                this.maxValue = maxValue;
                this.selectedItems = selectedItems;
            }
        }
    }
    
    /**
     * Performance testing and demonstrations
     */
    public static void main(String[] args) {
        System.out.println("=== Dynamic Programming Masterclass ===\n");
        
        // Fibonacci performance comparison
        System.out.println("1. Fibonacci Performance Comparison:");
        int n = 40;
        
        long start = System.nanoTime();
        long result = FibonacciSolutions.fibOptimized(n);
        long optimizedTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        long result2 = FibonacciSolutions.fibTab(n);
        long tabulationTime = System.nanoTime() - start;
        
        System.out.println("Fibonacci(" + n + ") = " + result);
        System.out.println("Optimized approach: " + optimizedTime / 1000.0 + " microseconds");
        System.out.println("Tabulation approach: " + tabulationTime / 1000.0 + " microseconds");
        
        // LCS demonstration
        System.out.println("\n2. Longest Common Subsequence:");
        String text1 = "ABCDGH";
        String text2 = "AEDFHR";
        String lcs = LCSolutions.lcsTabulation(text1, text2);
        System.out.println("Text 1: " + text1);
        System.out.println("Text 2: " + text2);
        System.out.println("LCS: " + lcs + " (length: " + lcs.length() + ")");
        
        // Knapsack demonstration
        System.out.println("\n3. 0/1 Knapsack Problem:");
        KnapsackSolutions.Item[] items = {
            new KnapsackSolutions.Item(10, 60),
            new KnapsackSolutions.Item(20, 100),
            new KnapsackSolutions.Item(30, 120)
        };
        int capacity = 50;
        
        KnapsackSolutions.Result knapsackResult = KnapsackSolutions.knapsackDP(items, capacity);
        System.out.println("Capacity: " + capacity);
        System.out.println("Maximum value: " + knapsackResult.maxValue);
        System.out.println("Selected items: " + knapsackResult.selectedItems);
    }
}