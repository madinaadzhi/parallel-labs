package org.madi.lab4.part4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;


public class DirectoryProcessing extends RecursiveTask<ArrayList<String>> {
    private final List<String> filePaths;
    private final List<String> keyWords;

    public DirectoryProcessing(String dirPath, List<String> keyWords) {
        this.keyWords = keyWords;
        File directory = new File(dirPath);
        File[] files = directory.listFiles();
        this.filePaths = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                this.filePaths.add(file.getAbsolutePath());
            }
        }
    }

    @Override
    protected ArrayList<String> compute() {
        ArrayList<FileProcessing> filesTasks = new ArrayList<>();
        for (String filePath : filePaths) {
            FileProcessing task = new FileProcessing(filePath, keyWords);
            filesTasks.add(task);
            task.fork();
        }
        ArrayList<String> results = new ArrayList<>();
        for (FileProcessing task : filesTasks) {
            if (task.join()) {
                results.add(task.path);
            }
        }
        return results;
    }
}
