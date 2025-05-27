package com.example.hexagonal_bank.services;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CodingService {
    //strings concatenation
    public static String concat(String[] strings) {
        return Arrays.stream(strings).collect(Collectors.joining());
    }

// filter words exercise
public List<String> filterWordsWithStream(List<String> words, String letters) {
    // 1. Create a Set for efficient lookup of filter characters.
    // Convert the 'letters' string to a stream of characters, then collect them into a HashSet.
    Set<Character> getFilterChars = letters.chars()        // Get an IntStream of character codes
            .mapToObj(c -> (char) c) // Convert int code to Character object
            .collect(Collectors.toCollection(HashSet::new)); // Collect into a HashSet

    // 2. Use a stream to filter the words.
    // Iterate through the 'words' list and filter based on the presence of any filter character.
    return words.stream()
            .filter(word -> ifContainsAnyFilterChar(word, getFilterChars)) // Filter condition
            .collect(Collectors.toList()); // Collect the filtered words into a new List
}

    /**
     * Helper method to check if a word contains any character from the filter set.
     *
     * @param word        The word to check.
     * @param listFilterChars The set of characters to look for.
     * @return true if the word contains at least one character from listFilterChars, false otherwise.
     */
    private boolean ifContainsAnyFilterChar(String word, Set<Character> listFilterChars) {
        // Convert the word to an IntStream of characters, then check if any match the filter.
        return word.chars()                // Get an IntStream of character codes
                .mapToObj(c -> (char) c) // Convert int code to Character object
                .anyMatch(listFilterChars::contains); // Check if any character is in the listFilterChars set
    }


    //find Sum Pair excercise
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

    // java exercice to decode the message
    public static String decode(List<String> words, String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }

        // Split the message into words, then use a stream to map each coded word
        // to its decoded equivalent, and finally join them back with spaces.
        return Arrays.stream(message.split(" "))
                .map(wordCoded -> getOriginalWord(words, wordCoded))
                .collect(Collectors.joining(" "));
    }

    /**
     * Finds the original word from the vocabulary that matches a given coded word,
     * using Streams for filtering.
     *
     * @param listwords    The list of known listwords.
     * @param wordCoded The word to decode.
     * @return The original, decoded word.
     */
    private static String getOriginalWord(List<String> listwords, String wordCoded) {
        // Handle listwords with 0, 1, or 2 characters.
        // For these, there are no "inner" characters to scramble,
        // so the coded word must match an original word exactly.
        if (wordCoded.length() <= 2) {
            // Use stream to find an exact match. findAny() is efficient as order doesn't matter,
            // and the problem guarantees a unique match.
            return listwords.stream()
                    .filter(originalWord -> originalWord.equals(wordCoded))
                    .findAny() // Finds any matching element
                    .orElse(wordCoded); // Fallback: return wordCoded if no match (though problem guarantees one)
        }

        char codedFirstChar = wordCoded.charAt(0);
        char codedLastChar = wordCoded.charAt(wordCoded.length() - 1);
        String codedInnerCharsSorted = sortChars(wordCoded.substring(1, wordCoded.length() - 1));

        // Use stream to filter through the list of listwords and find the matching one.
        return listwords.stream()
                // Filter by length
                .filter(stringOriginalWord -> stringOriginalWord.length() == wordCoded.length())
                // Filter by first and last character
                .filter(filterOriginalWord -> {
                    char originalFirstChar = filterOriginalWord.charAt(0);
                    char originalLastChar = filterOriginalWord.charAt(filterOriginalWord.length() - 1);
                    return codedFirstChar == originalFirstChar && codedLastChar == originalLastChar;
                })
                // Filter by sorted inner characters (anagram check)
                .filter(sortedOriginalWord -> {
                    String originalInnerCharsSorted = sortChars(sortedOriginalWord.substring(1, sortedOriginalWord.length() - 1));
                    return codedInnerCharsSorted.equals(originalInnerCharsSorted);
                })
                .findFirst() // Find the first (and only, due to problem guarantee) match
                .orElse(wordCoded); // Fallback: return wordCoded if no match (though problem guarantees one)
    }

    /**
     * Helper method to sort the characters within a string.
     *
     * @param s The input string.
     * @return A new string with characters sorted alphabetically.
     */
    private static String sortChars(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    // Main method for testing (optional, for local execution)
    public static void testMain() {

        // Example from the problem description
        List<String> words1 = Arrays.asList("ball", "funny", "hello", "this", "message", "is", "a");
        String message1 = "hlleo tihs masegse is fnnuy";
        String decoded1 = decode(words1, message1);
        System.out.println("Coded:    " + message1);
        System.out.println("Expected: hello this message is funny");
        System.out.println("Decoded:  " + decoded1);
        System.out.println("---");

        // Another test case with short words
        List<String> words2 = Arrays.asList("i", "am", "a", "good", "programmer");
        String message2 = "i ma a goog programrme";
        String decoded2 = decode(words2, message2);
        System.out.println("Coded:    " + message2);
        System.out.println("Expected: i am a good programmer");
        System.out.println("Decoded:  " + decoded2);
        System.out.println("---");

        // Test with a single word message
        List<String> words3 = Arrays.asList("apple", "banana", "orange");
        String message3 = "oarnge";
        String decoded3 = decode(words3, message3);
        System.out.println("Coded:    " + message3);
        System.out.println("Expected: orange");
        System.out.println("Decoded:  " + decoded3);
        System.out.println("---");
    }
}
