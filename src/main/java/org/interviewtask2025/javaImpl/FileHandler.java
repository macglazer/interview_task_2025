package org.interviewtask2025.javaImpl;

import org.interviewtask2025.core.PairFinder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandler {

    /**
     * Reads numbers from a file. Numbers can be separated by spaces, commas, or new lines.
     * Invalid tokens or numbers outside the range 0-12 are ignored (but logged to stderr).
     *
     * @param fileName the name (or path) of the input file
     * @return a list of valid Integer numbers (in the range 0-12)
     */
    public static List<Integer> readNumbersFromFile(String fileName) {
        List<Integer> numbers = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                // Assume numbers are separated by commas or whitespace
                String[] tokens = line.split("[,\\s]+");
                for (String token : tokens) {
                    try {
                        int num = Integer.parseInt(token);
                        if (num < 0 || num > 12) {
                            System.err.println("Ignoring number out of range (0-12): " + num);
                            continue;
                        }
                        numbers.add(num);
                    } catch (NumberFormatException e) {
                        System.err.println("Ignoring invalid token: " + token);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return numbers;
    }

    /**
     * Processes the input file line by line:
     * - For each line, parses numbers (0-12) and also collects invalid tokens,
     * - Finds pairs (summing to 12) from the valid numbers,
     * - Joins all pairs found in that line into a single string and appends invalid tokens (if any),
     * - Writes each result (for each input line) on a separate line in the output file.
     *
     * @param inputFileName  the name (or path) of the input file
     * @param outputFileName the name (or path) of the output file
     * @param pairFinder     an instance of PairFinder to compute pairs
     */
    public static void processFileLineByLine(String inputFileName, String outputFileName, PairFinder pairFinder) {
        List<String> outputLines = new ArrayList<>();

        // Add a timestamp as the first line
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        outputLines.add("Results generated on: " + timestamp);

        try {
            List<String> lines = Files.readAllLines(Paths.get(inputFileName));

            for (String line : lines) {
                ParseResult result = parseLineToNumbers(line);
                // Find pairs only from valid numbers
                List<PairFinder.Pair> pairs = pairFinder.findPairs(result.validNumbers);

                // Join all pairs into one string
                String joinedPairs = pairs.stream()
                        .map(PairFinder.Pair::toString)
                        .collect(Collectors.joining(" "));

                // If there are any invalid tokens, append them to the result
                String invalidTokensInfo = "";
                if (!result.invalidTokens.isEmpty()) {
                    invalidTokensInfo = " ; Ignored tokens: " + String.join(" ", result.invalidTokens);
                }
                outputLines.add(joinedPairs + invalidTokensInfo);
            }

            Files.write(Paths.get(outputFileName), outputLines,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }

    /**
     * Helper class to store the results of parsing a single line.
     */
    private static class ParseResult {
        List<Integer> validNumbers;
        List<String> invalidTokens;

        ParseResult(List<Integer> validNumbers, List<String> invalidTokens) {
            this.validNumbers = validNumbers;
            this.invalidTokens = invalidTokens;
        }
    }

    /**
     * Parses a single line into a list of integers (only numbers in range 0-12)
     * and collects any invalid tokens.
     *
     * @param line the input line
     * @return a ParseResult containing valid numbers and a list of invalid tokens
     */
    private static ParseResult parseLineToNumbers(String line) {
        List<Integer> validNumbers = new ArrayList<>();
        List<String> invalidTokens = new ArrayList<>();
        // Split the line by commas or whitespace
        String[] tokens = line.split("[,\\s]+");
        for (String token : tokens) {
            if (token.isEmpty()) {
                continue;
            }
            try {
                int num = Integer.parseInt(token);
                if (num < 0 || num > 12) {
                    System.err.println("Ignoring number out of range (0-12): " + num);
                    invalidTokens.add(token);
                    continue;
                }
                validNumbers.add(num);
            } catch (NumberFormatException e) {
                System.err.println("Ignoring invalid token: " + token);
                invalidTokens.add(token);
            }
        }
        return new ParseResult(validNumbers, invalidTokens);
    }
}