package org.madi.lab4.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        basicAnalysis();
        long endTimeBasicAnalysis = System.currentTimeMillis();
        System.out.println("Basic analysis: " + (endTimeBasicAnalysis - startTime) + " ms");
        System.out.println();
        startTime = System.currentTimeMillis();
        forkJoinAnalysis();
        long endTimeForkJoin = System.currentTimeMillis();
        System.out.println("ForkJoin analysis: " + (endTimeForkJoin - startTime) + " ms");
    }

    private static void basicAnalysis() throws IOException {
        HashMap<Integer, Integer> result = new HashMap<>();
        String content = Files.readString(Path.of("/Users/madinaadzhigeldieva/Documents/javaProject/parallel-labs/lab4/src/main/java/org/madi/lab4/texts/text1.txt"));
        String[] words = content.split("\\s+");
        for (String word : words) {
            int length = word.length();
            result.put(length, result.getOrDefault(length, 0) + 1);
        }
        printStat(result);
    }

    private static void forkJoinAnalysis() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        StatTextAnalysis task = new StatTextAnalysis("/Users/madinaadzhigeldieva/Documents/javaProject/parallel-labs/lab4/src/main/java/org/madi/lab4/texts/text1.txt");
        HashMap<Integer, Integer> result = pool.invoke(task);
        pool.shutdown();
        printStat(result);
    }

    public static void printStat(HashMap<Integer, Integer> map) {
        int wordsCnt = 0;
        int charsCnt = 0;
        for (int lengthKey : map.keySet()) {
            wordsCnt += map.get(lengthKey);
            charsCnt += map.get(lengthKey) * lengthKey;
        }

        int avgLength = charsCnt / wordsCnt;
        double dispersion = 0.0;
        for (int lengthKey : map.keySet()) {
            for (int i = 0; i < map.get(lengthKey); i++) {
                dispersion += Math.pow(lengthKey - avgLength, 2);
            }
        }
        dispersion /= wordsCnt;

        System.out.println("Total words count: " + wordsCnt);
        System.out.println("Avg words length: " + Math.round(avgLength * 100.0) / 100.0);
        System.out.println("Word length dispersion: " + Math.round(dispersion * 100.0) / 100.0);
        System.out.println("Standard deviation: " + Math.round(Math.sqrt(dispersion) * 100.0) / 100.0);
    }
}
