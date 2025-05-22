package com.example.hexagonal_bank.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors; // Required for Stream operations like .collect(Collectors.joining())

public class MessageDecoder {

    /**
     * Decodes a message where words have their inner letters scrambled,
     * utilizing Java Streams for a more functional approach.
     *
     * @param words   A list of known words (vocabulary).
     * @param message The coded message.
     * @return The decoded message.
     */
    public String decode(List<String> words, String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }

        // Split the message into words, then use a stream to map each coded word
        // to its decoded equivalent, and finally join them back with spaces.
        return Arrays.stream(message.split(" "))
                .map(codedWord -> findOriginalWord(words, codedWord))
                .collect(Collectors.joining(" "));
    }

    /**
     * Finds the original word from the vocabulary that matches a given coded word,
     * using Streams for filtering.
     *
     * @param words    The list of known words.
     * @param codedWord The word to decode.
     * @return The original, decoded word.
     */
    private String findOriginalWord(List<String> words, String codedWord) {
        // Handle words with 0, 1, or 2 characters.
        // For these, there are no "inner" characters to scramble,
        // so the coded word must match an original word exactly.
        if (codedWord.length() <= 2) {
            // Use stream to find an exact match. findAny() is efficient as order doesn't matter,
            // and the problem guarantees a unique match.
            return words.stream()
                    .filter(originalWord -> originalWord.equals(codedWord))
                    .findAny() // Finds any matching element
                    .orElse(codedWord); // Fallback: return codedWord if no match (though problem guarantees one)
        }

        char codedFirstChar = codedWord.charAt(0);
        char codedLastChar = codedWord.charAt(codedWord.length() - 1);
        String codedInnerCharsSorted = sortChars(codedWord.substring(1, codedWord.length() - 1));

        // Use stream to filter through the list of words and find the matching one.
        return words.stream()
                // Filter by length
                .filter(originalWord -> originalWord.length() == codedWord.length())
                // Filter by first and last character
                .filter(originalWord -> {
                    char originalFirstChar = originalWord.charAt(0);
                    char originalLastChar = originalWord.charAt(originalWord.length() - 1);
                    return codedFirstChar == originalFirstChar && codedLastChar == originalLastChar;
                })
                // Filter by sorted inner characters (anagram check)
                .filter(originalWord -> {
                    String originalInnerCharsSorted = sortChars(originalWord.substring(1, originalWord.length() - 1));
                    return codedInnerCharsSorted.equals(originalInnerCharsSorted);
                })
                .findFirst() // Find the first (and only, due to problem guarantee) match
                .orElse(codedWord); // Fallback: return codedWord if no match (though problem guarantees one)
    }

    /**
     * Helper method to sort the characters within a string.
     *
     * @param s The input string.
     * @return A new string with characters sorted alphabetically.
     */
    private String sortChars(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    // Main method for testing (optional, for local execution)
    public static void main(String[] args) {
        MessageDecoder decoder = new MessageDecoder();

        // Example from the problem description
        List<String> words1 = Arrays.asList("ball", "funny", "hello", "this", "message", "is", "a");
        String message1 = "hlleo tihs masegse is fnnuy";
        String decoded1 = decoder.decode(words1, message1);
        System.out.println("Coded:    " + message1);
        System.out.println("Expected: hello this message is funny");
        System.out.println("Decoded:  " + decoded1);
        System.out.println("---");

        // Another test case with short words
        List<String> words2 = Arrays.asList("i", "am", "a", "good", "programmer");
        String message2 = "i ma a goog programrme";
        String decoded2 = decoder.decode(words2, message2);
        System.out.println("Coded:    " + message2);
        System.out.println("Expected: i am a good programmer");
        System.out.println("Decoded:  " + decoded2);
        System.out.println("---");

        // Test with a single word message
        List<String> words3 = Arrays.asList("apple", "banana", "orange");
        String message3 = "oarnge";
        String decoded3 = decoder.decode(words3, message3);
        System.out.println("Coded:    " + message3);
        System.out.println("Expected: orange");
        System.out.println("Decoded:  " + decoded3);
        System.out.println("---");
    }
}
