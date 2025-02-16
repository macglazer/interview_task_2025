package org.interviewtask2025.javaImpl;

import org.interviewtask2025.core.PairFinder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JavaPairsAppTest {

    @Test
    @DisplayName("testFindPairsBasic: Should find one pair [1, 11]")
    public void testFindPairsBasic() {
        // GIVEN:
        List<Integer> input = List.of(1, 2, 3, 11, 1);
        JavaPairsApp app = new JavaPairsApp();

        // WHEN:
        List<PairFinder.Pair> pairs = app.findPairs(input);

        // THEN:
        assertEquals(1, pairs.size(), "Expected exactly one pair to be found");
        PairFinder.Pair pair = pairs.get(0);
        assertEquals(1, pair.first(), "The first element of the pair should be 1");
        assertEquals(11, pair.second(), "The second element of the pair should be 11");
    }

    @Test
    @DisplayName("testFindPairsWithDuplicates: Should find two pairs [6, 6] from duplicate 6's")
    public void testFindPairsWithDuplicates() {
        // GIVEN:
        List<Integer> input = List.of(6, 6, 6, 6);
        JavaPairsApp app = new JavaPairsApp();

        // WHEN:
        List<PairFinder.Pair> pairs = app.findPairs(input);

        // THEN:
        assertEquals(2, pairs.size(), "Expected two pairs to be found");
        for (PairFinder.Pair pair : pairs) {
            assertEquals(6, pair.first(), "The first element of the pair should be 6");
            assertEquals(6, pair.second(), "The second element of the pair should be 6");
        }
    }

    @Test
    @DisplayName("testFindPairsNoPairs: Should return empty list when no valid pairs exist")
    public void testFindPairsNoPairs() {
        // GIVEN:
        List<Integer> input = List.of(1, 2, 3, 4);
        JavaPairsApp app = new JavaPairsApp();

        // WHEN:
        List<PairFinder.Pair> pairs = app.findPairs(input);

        // THEN:
        assertTrue(pairs.isEmpty(), "Expected no pairs to be found");
    }
}