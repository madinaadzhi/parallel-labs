package org.madi.lab5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws Exception {
//        task1();
//        task2(8);
        task3();
    }

    public static void task1() {
        Init init = new Init(false);
        double[] results = init.call();
        print(results[0], results[1]);
    }

    public static void task2(int sysInstanceCnt) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ArrayList<Callable<double[]>> callables = new ArrayList<>();
        for (int i = 0; i < sysInstanceCnt; i++)
            callables.add(new Init(false));
        List<Future<double[]>> resultList = executor.invokeAll(callables);
        executor.shutdown();
        double totalAvgMessages = 0;
        double totalPercentages = 0;
        for (Future<double[]> result : resultList) {
            double[] info = result.get();
            totalAvgMessages += info[1];
            totalPercentages += info[0];
        }
        print(totalPercentages / resultList.size(), totalAvgMessages / resultList.size());
    }

    public static void task3() {
        Init init = new Init(true);
        double[] results = init.call();
        print(results[0], results[1]);
    }

    private static void print(double failProbability, double avgQueueSize) {
        System.out.println("Fail probability: " + Math.round(failProbability * 100.0) / 100.0);
        System.out.println("Average queue size: " + Math.round(avgQueueSize * 100.0) / 100.0);
    }
}