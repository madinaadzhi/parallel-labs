package org.madi.mkr2.task16;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);

        double[] numbers = new double[10000];
        for (int i = 0; i < 10000; i++) {
            numbers[i] = Math.random();
        }
        ModifiedCascadeAlgorithm modifiedCascadeAlgorithm = new ModifiedCascadeAlgorithm(numbers, 0, 10000);
        double sum = forkJoinPool.invoke(modifiedCascadeAlgorithm);
        System.out.println("Sum = " + sum);
    }
}
