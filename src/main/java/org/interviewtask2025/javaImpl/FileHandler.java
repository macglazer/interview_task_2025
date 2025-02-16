package org.interviewtask2025.javaImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    /**
     * Reads numbers from a file. Numbers can be separated by spaces, commas, or new lines.
     * Invalid tokens or numbers outside the range 0-12 are ignored.
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
     * Writes the given list of strings to a file.
     *
     * @param fileName the name (or path) of the output file
     * @param lines    the list of strings to write
     */
    public static void writeLinesToFile(String fileName, List<String> lines) {
        try {
            Files.write(Paths.get(fileName), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
