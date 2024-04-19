package org.madi.lab2.experiment;

import org.madi.lab2.Basic;
import org.madi.lab2.Matrix;
import org.madi.lab2.MatrixGenerator;
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

        Striped striped = new Striped(matrix1, matrix2, THREADSCOUNT);
        Basic basic = new Basic(matrix1, matrix2);
        Fox fox = new Fox(matrix1, matrix2, THREADSCOUNT);

        startTime = System.currentTimeMillis();
        Matrix strRes = new Matrix(striped.multiply().getMatrix());
        endTime = System.currentTimeMillis();
        System.out.println("Striped: " + (endTime - startTime) + " ms " + "for " + MATRIXSIZE + " matrix size" );

        startTime = System.currentTimeMillis();
        Matrix basicRes = new Matrix(basic.multiply().getMatrix());
        endTime = System.currentTimeMillis();
        System.out.println("Basic: " + (endTime - startTime) + " ms " + "for " + MATRIXSIZE + " matrix size" );

        startTime = System.currentTimeMillis();
        Matrix foxRes = new Matrix(fox.multiply().getMatrix());
        endTime = System.currentTimeMillis();
        System.out.println("Fox: " + (endTime - startTime) + " ms " + "for " + MATRIXSIZE + " matrix size" );

        // Check results
        for (int i = 0; i < MATRIXSIZE; i++) {
            for (int j = 0; j < MATRIXSIZE; j++) {
                if (strRes.get(i, j) != basicRes.get(i, j) || strRes.get(i, j) != foxRes.get(i, j)) {
                    System.out.println("Error");
                    return;
                }
            }
        }
    }
}
