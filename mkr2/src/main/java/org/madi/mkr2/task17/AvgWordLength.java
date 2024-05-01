package org.madi.mkr2.task17;

import java.util.concurrent.RecursiveTask;
import java.util.ArrayList;


public class AvgWordLength extends RecursiveTask<Double> {
    private final ArrayList<String> words;

    public AvgWordLength(ArrayList<String> words) {
        this.words = words;
    }

    @Override
    protected Double compute() {
        if (words.isEmpty()) {
            return 0.0;
        }
        int totalLength = 0;
        int wordCount = words.size();

        for (String word : words) {
            totalLength += word.length();
        }
        return (double) totalLength / wordCount;
    }
}
