package org.madi.lab4.part3;

import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        DirectoryProcessing task = new DirectoryProcessing("/Users/madinaadzhigeldieva/Documents/javaProject/parallel-labs/lab4/src/main/java/org/madi/lab4/texts");
        Set<String> words = pool.invoke(task);
        pool.shutdown();
        System.out.println("Common words in texts: " + words.toString());
    }
}
