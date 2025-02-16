package org.interviewtask2025.core;


import java.util.List;

public interface PairFinder {
    /**
     * Finds all unique pairs of numbers from a list that sum to 12.
     * Each number can only be used once.
     *
     * @param numbers a list of natural numbers (values 0 to 12)
     * @return a list of pairs, where each pair has a smaller number as its first element
     */

    List<Pair> findPairs(List<Integer> numbers);

    /**
     * A simple class representing a pair of numbers.
     */
    record Pair(int first, int second) {
        public Pair(int first, int second) {
            if (first <= second) {
                this.first = first;
                this.second = second;
            } else {
                this.first = second;
                this.second = first;
            }
        }

        @Override
        public String toString() {
            return "[" + first + ", " + second + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pair)) return false;
            Pair pair = (Pair) o;
            return first == pair.first && second == pair.second;
        }
    }
}
