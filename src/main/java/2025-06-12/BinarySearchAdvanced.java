package algorithms.search;

import java.util.*;

/**
 * Advanced Binary Search Implementations
 * Comprehensive collection of binary search variations for different use cases
 * 
 * Time Complexity: O(log n) for all variations
 * Space Complexity: O(1) iterative, O(log n) recursive
 */
public class BinarySearchAdvanced {
    
    /**
     * Standard binary search - find exact match
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2; // Prevents overflow
            
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        
        return -1; // Not found
    }
    
    /**
     * Find first occurrence of target (leftmost)
     * Useful when array has duplicates
     */
    public static int findFirst(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                result = mid;
                right = mid - 1; // Continue searching left
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    /**
     * Find last occurrence of target (rightmost)
     */
    public static int findLast(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) {
                result = mid;
                left = mid + 1; // Continue searching right
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    /**
     * Find insertion position (lower bound)
     * Returns index where target should be inserted to maintain sorted order
     */
    public static int lowerBound(int[] arr, int target) {
        int left = 0, right = arr.length;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    /**
     * Find upper bound
     * Returns index after the last occurrence of target
     */
    public static int upperBound(int[] arr, int target) {
        int left = 0, right = arr.length;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    /**
     * Search in rotated sorted array
     * Classic interview question
     */
    public static int searchRotated(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == target) return mid;
            
            // Check which half is sorted
            if (arr[left] <= arr[mid]) {
                // Left half is sorted
                if (target >= arr[left] && target < arr[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // Right half is sorted
                if (target > arr[mid] && target <= arr[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }
    
    /**
     * Find peak element (element greater than its neighbors)
     */
    public static int findPeak(int[] arr) {
        int left = 0, right = arr.length - 1;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] < arr[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        
        return left;
    }
    
    /**
     * Binary search on floating point numbers
     * Find square root with precision
     */
    public static double sqrt(double x, double precision) {
        if (x < 0) throw new IllegalArgumentException("Cannot find sqrt of negative number");
        if (x == 0 || x == 1) return x;
        
        double left = 0, right = x;
        if (x < 1) right = 1;
        
        while (right - left > precision) {
            double mid = (left + right) / 2;
            double square = mid * mid;
            
            if (square == x) return mid;
            if (square < x) left = mid;
            else right = mid;
        }
        
        return (left + right) / 2;
    }
    
    /**
     * Performance testing and demonstration
     */
    public static void demonstrateAll() {
        System.out.println("=== Advanced Binary Search Demonstrations ===\n");
        
        // Test data with duplicates
        int[] arr = {1, 2, 2, 2, 3, 4, 4, 5, 6, 7, 8, 9};
        int target = 2;
        
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Target: " + target);
        System.out.println();
        
        // Standard search
        System.out.println("Standard search: " + binarySearch(arr, target));
        System.out.println("First occurrence: " + findFirst(arr, target));
        System.out.println("Last occurrence: " + findLast(arr, target));
        System.out.println("Lower bound: " + lowerBound(arr, target));
        System.out.println("Upper bound: " + upperBound(arr, target));
        
        // Rotated array test
        int[] rotated = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("\nRotated array: " + Arrays.toString(rotated));
        System.out.println("Search 0 in rotated: " + searchRotated(rotated, 0));
        System.out.println("Search 3 in rotated: " + searchRotated(rotated, 3));
        
        // Peak finding
        int[] peakArr = {1, 2, 3, 1};
        System.out.println("\nPeak array: " + Arrays.toString(peakArr));
        System.out.println("Peak index: " + findPeak(peakArr));
        
        // Floating point search
        double number = 25.0;
        double precision = 0.000001;
        System.out.println("\nSquare root of " + number + ": " + sqrt(number, precision));
        
        // Performance comparison
        performanceTest();
    }
    
    private static void performanceTest() {
        System.out.println("\n=== Performance Analysis ===");
        
        int[] largeArray = new int[1000000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i * 2; // Even numbers
        }
        
        int target = 999998;
        
        // Binary search
        long start = System.nanoTime();
        int result = binarySearch(largeArray, target);
        long binaryTime = System.nanoTime() - start;
        
        // Linear search for comparison
        start = System.nanoTime();
        int linearResult = linearSearch(largeArray, target);
        long linearTime = System.nanoTime() - start;
        
        System.out.println("Array size: " + largeArray.length);
        System.out.println("Binary search time: " + binaryTime / 1000.0 + " microseconds");
        System.out.println("Linear search time: " + linearTime / 1000.0 + " microseconds");
        System.out.println("Binary search is " + (linearTime / binaryTime) + "x faster");
    }
    
    private static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }
    
    public static void main(String[] args) {
        demonstrateAll();
    }
}