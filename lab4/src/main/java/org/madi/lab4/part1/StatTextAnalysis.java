package org.madi.lab4.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class StatTextAnalysis extends RecursiveTask<HashMap<Integer, Integer>> {
    public final String file;
    private List<String> words;
    private int start;
    private int end;
    private final boolean isSplit;

    public StatTextAnalysis(String file) {
        this.file = file;
        isSplit = false;
    }

    public StatTextAnalysis(String file, List<String> words, int start, int end) {
        this.file = file;
        this.words = words;
        this.start = start;
        this.end = end;
        isSplit = true;
    }

    @Override
    protected HashMap<Integer, Integer> compute() {
        if (!isSplit) {
            initWordsList();
        }
        if (end - start < 200_000) {
            return getWords();
        }

        int middle = (end + start) / 2;
        StatTextAnalysis firstTask = new StatTextAnalysis(file, words, start, middle);
        firstTask.fork();
        StatTextAnalysis secondTask = new StatTextAnalysis(file, words, middle, end);

        HashMap<Integer, Integer> result = secondTask.compute();
        firstTask.join().forEach((lengthKey, count) -> result.merge(lengthKey, count, Integer::sum));
        return result;
    }

    private HashMap<Integer, Integer> getWords() {
        HashMap<Integer, Integer> words = new HashMap<>();
        this.words.subList(start, end).forEach(word -> {
            if (words.containsKey(word.length())) {
                words.put(word.length(), words.get(word.length()) + 1);
            } else {
                words.put(word.length(), 1);
            }
        });
        return words;
    }

    private void initWordsList() {
        try {
            String text = Files.readString(Paths.get(file));
            words = List.of(text.split("\\s+"));
            start = 0;
            end = words.size();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while reading file");
        }
    }
}
