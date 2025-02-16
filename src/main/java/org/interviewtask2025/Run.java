package org.interviewtask2025;

import org.interviewtask2025.core.PairFinder;
import org.interviewtask2025.javaImpl.FileHandler;
import org.interviewtask2025.javaImpl.JavaPairsApp;

import java.util.List;
import java.util.stream.Collectors;

public class Run {

        public static void main(String[] args) {
            JavaPairsApp app = new JavaPairsApp();
            List<Integer> numbers;
            String inputFileName;
            String outputFileName;

            if (args.length >= 2) {
                inputFileName = args[0];
                outputFileName = args[1];
                numbers = FileHandler.readNumbersFromFile(inputFileName);
            } else {
                // If not enough arguments are provided, use default values
                inputFileName = "data.txt";
                outputFileName = "src/main/resources/result.txt";
                numbers = List.of(1, 2, 3, 11, 1);
            }

            List<PairFinder.Pair> pairs = app.findPairs(numbers);
            // Convert pairs to strings for file output
            List<String> outputLines = pairs.stream()
                    .map(PairFinder.Pair::toString)
                    .collect(Collectors.toList());

            FileHandler.writeLinesToFile(outputFileName, outputLines);

            System.out.println("Pairs found:");
            pairs.forEach(System.out::println);
            System.out.println("Results have been written to: " + outputFileName);
        }
    }

