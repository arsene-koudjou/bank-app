package com.example.hexagonal_bank.model;

import java.util.*;
import java.util.stream.Collectors;

public class FilterWords {
    
    // Stream solution to filter words
    public List<String> filterWordsWithStream(List<String> words, String letters) {
        // 1. Create a Set for efficient lookup of filter characters.
        // Convert the 'letters' string to a stream of characters, then collect them into a HashSet.
        Set<Character> getFilterChars = letters.chars()        // Get an IntStream of character codes
                .mapToObj(c -> (char) c) // Convert int code to Character object
                .collect(Collectors.toCollection(HashSet::new)); // Collect into a HashSet

        // 2. Use a stream to filter the words.
        // Iterate through the 'words' list and filter based on the presence of any filter character.
        return words.stream()
                .filter(word -> containsAnyFilterChar(word, getFilterChars)) // Filter condition
                .collect(Collectors.toList()); // Collect the filtered words into a new List
    }

    /**
     * Helper method to check if a word contains any character from the filter set.
     *
     * @param word        The word to check.
     * @param listFilterChars The set of characters to look for.
     * @return true if the word contains at least one character from listFilterChars, false otherwise.
     */
    private boolean containsAnyFilterChar(String word, Set<Character> listFilterChars) {
        // Convert the word to an IntStream of characters, then check if any match the filter.
        return word.chars()                // Get an IntStream of character codes
                .mapToObj(c -> (char) c) // Convert int code to Character object
                .anyMatch(listFilterChars::contains); // Check if any character is in the listFilterChars set
    }
    
    
    // second solution without Stream
    
    
    /**
     * Filters a list of words, keeping only those that contain at least one letter
     * from a specified set of filter letters. The order of words is preserved.
     *
     * @param words   A list of strings (words) to be filtered. All are lowercase.
     * @param letters A string containing the filter letters (all lowercase).
     * @return A new list of strings containing only the filtered words,
     * maintaining their original order.
     */
    public static List<String> filterWords(List<String> words, String letters) {
        // 1. Create a Set for efficient lookup of filter characters.
        // O(L) time complexity, where L is the length of 'letters' string.
        Set<Character> filterChars = new HashSet<>();
        for (char c : letters.toCharArray()) {
            filterChars.add(c);
        }

        // 2. Create a list to store the filtered words.
        List<String> filteredWords = new ArrayList<>();

        // 3. Iterate through each word in the input list.
        // O(N * W) in worst case, where N is number of words, W is max word length.
        for (String word : words) {
            boolean containsFilterChar = false;
            // Iterate through each character in the current word.
            for (char c : word.toCharArray()) {
                if (filterChars.contains(c)) {
                    containsFilterChar = true;
                    break; // Found a filter character, no need to check further in this word
                }
            }

            // If the word contains at least one filter character, add it to the result list.
            if (containsFilterChar) {
                filteredWords.add(word);
            }
        }

        // 4. Return the list of filtered words.
        return filteredWords;
    }

    // Main method for testing (optional, for local execution)
    public static void main(String[] args) {


        // Example from the problem description
        List<String> words1 = Arrays.asList("the", "dog", "got", "a", "bone");
        String letters1 = "ae";
        List<String> result1 = filterWords(words1, letters1);
        System.out.println("Words: " + words1 + ", Letters: '" + letters1 + "' -> Result: " + result1);
        System.out.println("Expected: ['the', 'a', 'bone']\n");

        // Test case: No words contain filter letters
        List<String> words2 = Arrays.asList("cat", "sun", "moon");
        String letters2 = "xyz";
        List<String> result2 = filterWords(words2, letters2);
        System.out.println("Words: " + words2 + ", Letters: '" + letters2 + "' -> Result: " + result2);
        System.out.println("Expected: []\n");

        // Test case: All words contain filter letters
        List<String> words3 = Arrays.asList("apple", "banana", "cherry");
        String letters3 = "aeiou";
        List<String> result3 = filterWords(words3, letters3);
        System.out.println("Words: " + words3 + ", Letters: '" + letters3 + "' -> Result: " + result3);
        System.out.println("Expected: ['apple', 'banana', 'cherry']\n");

        // Test case: Empty letters string
        List<String> words4 = Arrays.asList("hello", "world");
        String letters4 = "";
        List<String> result4 = filterWords(words4, letters4);
        System.out.println("Words: " + words4 + ", Letters: '" + letters4 + "' -> Result: " + result4);
        System.out.println("Expected: []\n");

        // Test case: Empty words list
        List<String> words5 = new ArrayList<>();
        String letters5 = "abc";
        List<String> result5 = filterWords(words5, letters5);
        System.out.println("Words: " + words5 + ", Letters: '" + letters5 + "' -> Result: " + result5);
        System.out.println("Expected: []\n");
    }
}
