package org.madi.lab4.part3;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class DirectoryProcessing extends RecursiveTask<Set<String>> {
    private final List<String> filePaths = new ArrayList<>();

    public DirectoryProcessing(String path) {
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                filePaths.add(file.getAbsolutePath());
            }
        }
    }

    @Override
    protected Set<String> compute() {
        ArrayList<RecursiveTask<Set<String>>> tasks = new ArrayList<>();
        ArrayList<String> filesToResolve = new ArrayList<>();
        int c = 0;
        for (String filePath : filePaths) {
            filesToResolve.add(filePath);
            c++;
            if (c >= 2) {
                FileProcessing task = new FileProcessing(new ArrayList<>(filesToResolve));
                tasks.add(task);
                task.fork();
                c = 0;
                filesToResolve.clear();
            }
        }
        if (!filesToResolve.isEmpty()) {
            FileProcessing task = new FileProcessing(new ArrayList<>(filesToResolve));
            tasks.add(task);
            task.fork();
        }
        ArrayList<Set<String>> setsToIntersect = new ArrayList<>();
        for (RecursiveTask<Set<String>> task : tasks) {
            setsToIntersect.add(task.join());
        }

        Set<String> setsIntersection = new HashSet<>(setsToIntersect.get(0));
        for (int i = 1; i < setsToIntersect.size(); i++) {
            setsIntersection.retainAll(setsToIntersect.get(i));
        }
        return setsIntersection;
    }
}