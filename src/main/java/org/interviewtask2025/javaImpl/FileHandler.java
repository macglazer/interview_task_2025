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
     /**
     * Processes the input file line by line:
     * - For each line, parses numbers (0-12),
     * - Finds pairs that sum to 12,
     * - Joins all pairs found in that line into a single string,
     * - Writes each joined result on a separate line in the output file.
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
                List<Integer> numbers = parseLineToNumbers(line);

                List<PairFinder.Pair> pairs = pairFinder.findPairs(numbers);

                String joinedPairs = pairs.stream()
                        .map(PairFinder.Pair::toString)
                        .collect(Collectors.joining(" "));

                outputLines.add(joinedPairs);
            }

            Files.write(Paths.get(outputFileName), outputLines,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
        }
    }

    /**
     * Helper method to parse a single line into a list of integers (only numbers in range 0-12).
     *
     * @param line the input line
     * @return a list of parsed integers
     */
    private static List<Integer> parseLineToNumbers(String line) {
        List<Integer> numbers = new ArrayList<>();
        // Split the line by commas or whitespace
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
        return numbers;
    }
}