package com.example.hexagonal_bank.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindSumPair {
    /**
     * Finds two distinct indices in a list of positive integers whose sum equals a target value k.
     * Prioritizes the pair with the lowest left index, then the lowest right index.
     * Returns [0, 0] if no such pair is found.
     *
     * @param numbers A list of positive integers (indices from 0).
     * @param k       The target sum.
     * @return A list of two integers [index1, index2] representing the indices,
     * where index1 < index2, or [0, 0] if no pair is found.
     */
    public List<Integer> findSumPair(List<Integer> numbers, int k) {
        // Map to store number -> its index
        // When iterating from left to right, storing the index allows us to quickly check
        // if the 'complement' exists at an earlier index.
        Map<Integer, Integer> indexMapNumber = new HashMap<>();

        // Iterate through the list of numbers
        for (int i = 0; i < numbers.size(); i++) {
            int actualNumber = numbers.get(i);
            int complement = k - actualNumber;

            // Check if the complement exists in our map
            if (indexMapNumber.containsKey(complement)) {
                // If it exists, we found a pair.
                // The index of the complement is 'j', which must be less than 'i'
                // because we put it in the map when we processed it earlier.
                int j = indexMapNumber.get(complement);

                // According to the problem: "Vous ne pouvez pas prendre deux fois le même indice."
                // This 'j' will always be different from 'i' if complement != actualNumber.
                // If complement == actualNumber (e.g., finding 10 in [5, 5]),
                // then 'j' would be the index of the first 5, and 'i' would be the index of the second 5.
                // So, i != j is naturally handled here.
                return Arrays.asList(j, i); // j will be the smaller index, i will be the larger
            }

            // If complement is not found, add the current number and its index to the map.
            // If a number appears multiple times, this will store the LATEST index.
            // This is crucial for correctly finding the pair with the smallest left index.
            // Example: numbers=[10, 5, 5], k=10.
            // i=0, num=10. complement=0. Map: {10:0}.
            // i=1, num=5. complement=5. Map does NOT contain 5 yet (only 10). Map: {10:0, 5:1}.
            // i=2, num=5. complement=5. Map contains 5, j=1. Return [1, 2]. Correct.
            indexMapNumber.put(actualNumber, i);
        }

        // If no pair is found after iterating through the entire list
        return Arrays.asList(0, 0);
    }

    // Implementing a brute-force approach to correctly handle the index prioritization rules
    // This might be too slow for very large inputs, but guarantees correctness based on rules.
    public static List<Integer> findSumPairBruteForce(List<Integer> numbers, int k) {
        // Initialize with default [0, 0] or a sentinel value indicating no solution found yet.
        // We use [Integer.MAX_VALUE, Integer.MAX_VALUE] to easily compare for "lowest" indices.
        int leftIndexFirst = Integer.MAX_VALUE;
        int rightIndexSecond = Integer.MAX_VALUE;

        // Iterate through all possible pairs (i, j) where i < j
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers.get(i) + numbers.get(j) == k) {
                    // Found a valid pair [i, j]
                    // Compare with the current best pair found so far
                    if (i < leftIndexFirst) {
                        // Current 'i' is smaller than the best 'left index' found so far.
                        // This pair is strictly better.
                        leftIndexFirst = i;
                        rightIndexSecond = j;
                    } else if (i == leftIndexFirst) {
                        // Current 'i' is the same as the best 'left index'.
                        // Now compare 'right index' (j).
                        if (j < rightIndexSecond) {
                            // Current 'j' is smaller than the best 'right index' for this 'left index'.
                            rightIndexSecond = j;
                        }
                    }
                }
            }
        }

        // After checking all pairs, if leftIndexFirst is still MAX_VALUE, no pair was found.
        if (leftIndexFirst == Integer.MAX_VALUE) {
            return Arrays.asList(0, 0);
        } else {
            return Arrays.asList(leftIndexFirst, rightIndexSecond);
        }
    }
}
