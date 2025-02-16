package org.interviewtask2025.javaImpl;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void readNumbersFromFile_invalidData() throws IOException {
        // GIVEN:
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
    void writeLinesToFile_shouldWriteAndOverwriteExistingFile() throws IOException {
        // GIVEN:
        Path outputFile = tempDir.resolve("output.txt");

        Files.write(outputFile, Arrays.asList("Old content"));

        List<String> linesToWrite = Arrays.asList("Line 1", "Line 2", "Line 3");

        // WHEN:
        FileHandler.writeLinesToFile(outputFile.toString(), linesToWrite);

        // THEN:
        List<String> writtenLines = Files.readAllLines(outputFile);
        assertEquals(3, writtenLines.size());
        assertEquals("Line 1", writtenLines.get(0));
        assertEquals("Line 2", writtenLines.get(1));
        assertEquals("Line 3", writtenLines.get(2));
    }

    @Test
    void writeLinesToFile_invalidPath() {
        // GIVEN:
        String invalidFileName = tempDir.resolve("invalid?file.txt").toString();

        // WHEN & THEN:
        assertDoesNotThrow(() ->
                FileHandler.writeLinesToFile(invalidFileName, Arrays.asList("Test line"))
        );
    }
}