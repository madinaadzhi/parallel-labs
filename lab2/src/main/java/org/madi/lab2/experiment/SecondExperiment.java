package org.madi.lab2.experiment;

import org.madi.lab2.Matrix;
import org.madi.lab2.MatrixGenerator;
import org.madi.lab2.MultithreadMultiplication;
import org.madi.lab2.fox.Fox;

public class SecondExperiment {
    public static void main(String[] args) {
        MatrixGenerator randomMatrixGenerator = new MatrixGenerator();

        int MATRIX_SIZE = 1500;
        int THREADS_COUNT = 8;

        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();


        Matrix matrix1 = new Matrix(randomMatrixGenerator.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE).getMatrix());

        Matrix matrix2 = new Matrix(randomMatrixGenerator.generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE).getMatrix());

        MultithreadMultiplication multithreadMultiplication = new MultithreadMultiplication();
        Fox foxMultiplication = new Fox(matrix1, matrix2, THREADS_COUNT);

        startTime = System.currentTimeMillis();
        Matrix multithread = new Matrix(multithreadMultiplication.multiply(matrix1, matrix2, THREADS_COUNT).getMatrix());
        endTime = System.currentTimeMillis();
        System.out.println("Multithreaded multiplication: " + (endTime - startTime) + " ms " + "with " + THREADS_COUNT + " threads" );

        startTime = System.currentTimeMillis();
        Matrix fox = new Matrix(foxMultiplication.multiply().getMatrix());
        endTime = System.currentTimeMillis();
        System.out.println("Fox: " + (endTime - startTime) + " ms " + "with " + THREADS_COUNT + " threads" );

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (multithread.get(i, j) != fox.get(i, j)) {
                    System.out.println("Error");
                    return;
                }
            }
        }
    }
}
