package org.interviewtask2025;

import org.interviewtask2025.core.PairFinder;
import org.interviewtask2025.javaImpl.FileHandler;
import org.interviewtask2025.javaImpl.JavaPairsApp;

import java.util.List;
import java.util.stream.Collectors;

public class Run {
    public static void main(String[] args) {
        JavaPairsApp app = new JavaPairsApp();
        String inputFileName;
        String outputFileName;

        if (args.length >= 2) {
            inputFileName = args[0];
            outputFileName = args[1];
        } else {
            inputFileName = "data.txt";
            outputFileName = "result.txt";
        }

        List<Integer> numbers = FileHandler.readNumbersFromFile(inputFileName);

        List<PairFinder.Pair> pairs = app.findPairs(numbers);

        FileHandler.processFileLineByLine(inputFileName, outputFileName, app);

        System.out.println("Processing done. Check the output file: " + outputFileName);
    }
}