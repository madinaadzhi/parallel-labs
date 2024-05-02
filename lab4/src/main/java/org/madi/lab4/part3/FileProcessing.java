package org.madi.lab4.part3;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
public class FileProcessing extends RecursiveTask<Set<String>> {
    private final ArrayList<String> paths;

    @Override
    protected Set<String> compute() {
        ArrayList<Set<String>> setsToIntersect = new ArrayList<>();
        for (String filePath : paths) {
            setsToIntersect.add(getSetFromFile(filePath));
        }
        Set<String> setsIntersection = new HashSet<>(setsToIntersect.get(0));
        for (int i = 1; i < setsToIntersect.size(); i++) {
            setsIntersection.retainAll(setsToIntersect.get(i));
        }
        return setsIntersection;
    }

    private Set<String> getSetFromFile(String path) {
        try {
            return Files.lines(Paths.get(path))
                    .flatMap(line -> Stream.of(line.split(" ")))
                    .map(word -> word.toLowerCase().replaceAll("[^\\p{L}]", ""))
                    .filter(word -> !word.isEmpty())
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
