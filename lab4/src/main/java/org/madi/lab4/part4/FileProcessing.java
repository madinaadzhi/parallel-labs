package org.madi.lab4.part4;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.RecursiveTask;
import java.util.regex.Pattern;

@AllArgsConstructor
public class FileProcessing extends RecursiveTask<Boolean> {
    public final String path;
    private final List<String> keyWords;
    private final List<String> words;
    private final int start;
    private final int end;

    public FileProcessing(String path, List<String> keyWords) {
        this.path = path;
        this.keyWords = keyWords;
        Scanner scanner;
        try {
            scanner = new Scanner(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        String content = scanner.useDelimiter("\\A").next();
        scanner.close();
        words = List.of(content.split("\\s+"));
        start = 0;
        end = words.size();
    }

    @Override
    protected Boolean compute() {
        if (end - start < 200000) {
            return isContainsWord();
        }
        int middleIndex = (end + start) / 2;
        FileProcessing firstPart = new FileProcessing(path, keyWords, words, start, middleIndex);
        firstPart.fork();
        FileProcessing secondPart = new FileProcessing(path, keyWords, words, middleIndex, end);
        return firstPart.join() || secondPart.compute();
    }

    private boolean isContainsWord() {
        Pattern pattern = Pattern.compile("\\p{Punct}");
        for (String str : words) {
            String[] words = pattern.split(str.toLowerCase());
            for (String word : words) {
                for (String keyWord : keyWords) {
                    if (word.equals(keyWord.toLowerCase())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

