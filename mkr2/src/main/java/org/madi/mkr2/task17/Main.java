package org.madi.mkr2.task17;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>();
        words.add("Madina");
        words.add("Adzhyheldiieva");
        words.add("MacBook");
        words.add("Pro");

        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        AvgWordLength avgWordLength = new AvgWordLength(words);
        double avgLength = forkJoinPool.invoke(avgWordLength);

        System.out.println("Average word length = " + avgLength);
    }
}
