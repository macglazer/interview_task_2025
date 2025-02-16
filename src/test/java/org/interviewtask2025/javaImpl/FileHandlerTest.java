package org.interviewtask2025.javaImpl;

import org.interviewtask2025.core.PairFinder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        tempDir = Files.createTempDirectory("filehandler-test-");
    }

    @AfterEach
    void tearDown() throws IOException {
        if (Files.exists(tempDir)) {
            Files.walk(tempDir)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    @Test
    @DisplayName("readNumbersFromFile_validData: Reads valid numbers from file")
    void readNumbersFromFile_validData() throws IOException {
        // GIVEN:
        Path inputFile = tempDir.resolve("valid_data.txt");
        List<String> lines = Arrays.asList(
                "1 2 3 11 1",
                "12 0 6 6 3 9 1"
        );
        Files.write(inputFile, lines);

        // WHEN:
        List<Integer> result = FileHandler.readNumbersFromFile(inputFile.toString());

        // THEN:
        assertEquals(12, result.size());
        assertTrue(result.containsAll(Arrays.asList(1, 2, 3, 11, 12, 0, 6, 9)));
    }

    @Test
    @DisplayName("readNumbersFromFile_invalidData: Ignores invalid tokens and out-of-range numbers")
    void readNumbersFromFile_invalidData() throws IOException {
        // GIVEN: file with invalid data
        Path inputFile = tempDir.resolve("invalid_data.txt");
        List<String> lines = Arrays.asList(
                "abc 13 -1 5 11 0",
                "12 99 text 7"
        );
        Files.write(inputFile, lines);

        // WHEN:
        List<Integer> result = FileHandler.readNumbersFromFile(inputFile.toString());

        // THEN:
        assertEquals(5, result.size());
        assertTrue(result.containsAll(Arrays.asList(5, 11, 0, 12, 7)));
    }

    @Test
    @DisplayName("processFileLineByLine_shouldProcessFileCorrectly: Processes file line by line")
    void processFileLineByLine_shouldProcessFileCorrectly() throws IOException {
        // GIVEN:
        Path inputFile = tempDir.resolve("input.txt");
        List<String> inputLines = Arrays.asList(
                "0 0 0 0 0 0 12 12 12 12 12 12",
                "1 11"
        );
        Files.write(inputFile, inputLines);

        Path outputFile = tempDir.resolve("output.txt");

        // WHEN:
        JavaPairsApp app = new JavaPairsApp();
        FileHandler.processFileLineByLine(inputFile.toString(), outputFile.toString(), app);

        // THEN:
        List<String> outputLines = Files.readAllLines(outputFile);

        assertTrue(outputLines.get(0).startsWith("Results generated on:"));

        String expectedLine1 = "[0, 12] [0, 12] [0, 12] [0, 12] [0, 12] [0, 12]";
        assertEquals(expectedLine1, outputLines.get(1).trim());

        String expectedLine2 = "[1, 11]";
        assertEquals(expectedLine2, outputLines.get(2).trim());
    }

    @Test
    @DisplayName("processFileLineByLine_invalidOutputPath: Does not throw exception on invalid output path")
    void processFileLineByLine_invalidOutputPath() throws IOException {
        // GIVEN:
        String invalidOutputFileName = tempDir.resolve("invalid?file.txt").toString();
        Path inputFile = tempDir.resolve("input.txt");
        List<String> inputLines = Arrays.asList("1 2 3 11 1");
        Files.write(inputFile, inputLines);

        JavaPairsApp app = new JavaPairsApp();

        // WHEN & THEN:
        assertDoesNotThrow(() ->
                FileHandler.processFileLineByLine(inputFile.toString(), invalidOutputFileName, app)
        );
    }
}