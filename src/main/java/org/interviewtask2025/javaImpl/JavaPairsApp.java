package org.interviewtask2025.javaImpl;

import org.interviewtask2025.core.PairFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaPairsApp implements PairFinder {

        @Override
        public List<Pair> findPairs(List<Integer> numbers) {
            List<Pair> pairs = new ArrayList<>();
            Map<Integer, Integer> frequency = new HashMap<>();

            // Count the frequency of each number
            for (Integer num : numbers) {
                frequency.put(num, frequency.getOrDefault(num, 0) + 1);
            }

            // Iterate through possible numbers (0-12)
            for (int num = 0; num <= 12; num++) {
                int complement = 12 - num;

                // Check if both numbers are available
                if (!frequency.containsKey(num) || !frequency.containsKey(complement)) {
                    continue;
                }

                // If the numbers are different (num < complement) – create pairs
                if (num < complement) {
                    while (frequency.get(num) > 0 && frequency.get(complement) > 0) {
                        pairs.add(new Pair(num, complement));
                        frequency.put(num, frequency.get(num) - 1);
                        frequency.put(complement, frequency.get(complement) - 1);
                    }
                } else if (num == complement) {
                    // Case when num == complement (e.g., 6 + 6 = 12)
                    while (frequency.get(num) > 1) {
                        pairs.add(new Pair(num, complement));
                        frequency.put(num, frequency.get(num) - 2);
                    }
                }
            }

            return pairs;
        }
    }