package org.madi.lab2.experiment;

import org.madi.lab2.Matrix;
import org.madi.lab2.MatrixGenerator;
import org.madi.lab2.MultithreadMultiplication;
import org.madi.lab2.fox.Fox;
import org.madi.lab2.striped.Striped;

public class FirstExperiment {
    public static void main(String[] args) {
        MatrixGenerator randomMatrixGenerator = new MatrixGenerator();

        int MATRIXSIZE = 1500;
        int THREADSCOUNT = 4;

        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();

        Matrix matrix1 = new Matrix(randomMatrixGenerator.generateRandomMatrix(MATRIXSIZE, MATRIXSIZE).getMatrix());
        Matrix matrix2 = new Matrix(
                randomMatrixGenerator
                        .generateRandomMatrix(MATRIXSIZE, MATRIXSIZE)
                        .getMatrix());

        Striped striped = new Striped();
        MultithreadMultiplication multithreadMultiplication = new MultithreadMultiplication();
        Fox fox = new Fox(matrix1, matrix2, THREADSCOUNT);

        startTime = System.currentTimeMillis();
        Matrix strRes = new Matrix(striped.multiply(matrix1, matrix2).getMatrix());
        endTime = System.currentTimeMillis();
        System.out.println("Striped: " + (endTime - startTime) + " ms " + "for " + MATRIXSIZE + " matrix size" );

        startTime = System.currentTimeMillis();
        Matrix multithreadRes = new Matrix(multithreadMultiplication.multiply(matrix1, matrix2, THREADSCOUNT).getMatrix());
        endTime = System.currentTimeMillis();
        System.out.println("Multithreaded multiplication: " + (endTime - startTime) + " ms " + "for " + MATRIXSIZE + " matrix size" );

        startTime = System.currentTimeMillis();
        Matrix foxRes = new Matrix(fox.multiply().getMatrix());
        endTime = System.currentTimeMillis();
        System.out.println("Fox: " + (endTime - startTime) + " ms " + "for " + MATRIXSIZE + " matrix size" );

        // Check results
        for (int i = 0; i < MATRIXSIZE; i++) {
            for (int j = 0; j < MATRIXSIZE; j++) {
                if (strRes.get(i, j) != multithreadRes.get(i, j) || strRes.get(i, j) != foxRes.get(i, j)) {
                    System.out.println("Error");
                    return;
                }
            }
        }
    }
}
