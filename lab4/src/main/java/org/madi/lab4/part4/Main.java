package org.madi.lab4.part4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        List<String> keyWords = new ArrayList<>();
        keyWords.add("github");
        keyWords.add("oracle");

        ForkJoinPool pool = ForkJoinPool.commonPool();
        DirectoryProcessing task = new DirectoryProcessing("/Users/madinaadzhigeldieva/Documents/javaProject/parallel-labs/lab4/src/main/java/org/madi/lab4/texts", keyWords);
        ArrayList<String> filePaths = pool.invoke(task);
        pool.shutdown();

        System.out.println("Keywords " + keyWords + " found in files:");
        for (String file : filePaths) {
            System.out.println(file);
        }
        if (filePaths.isEmpty()) {
            System.out.println("Files not found.");
        }
    }
}
