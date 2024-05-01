package org.madi.mkr2.task16;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class ModifiedCascadeAlgorithm extends RecursiveTask<Double> {
    private final double[] numbers;
    private final int start;
    private final int end;

    public ModifiedCascadeAlgorithm(double[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Double compute() {
        if (end - start <= 1) {
            return numbers[start];
        }
        int middleValue = (start + end) / 2;
        ModifiedCascadeAlgorithm firstPart = new ModifiedCascadeAlgorithm(numbers, start, middleValue);
        ModifiedCascadeAlgorithm secondPart = new ModifiedCascadeAlgorithm(numbers, middleValue + 1, end);
        invokeAll(firstPart, secondPart);
        double firstSum = 0;
        try {
            firstSum = firstPart.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        double secondSum = 0;
        try {
            secondSum = secondPart.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return firstSum + secondSum;
    }
}
