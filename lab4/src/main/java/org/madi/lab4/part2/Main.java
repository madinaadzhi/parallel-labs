package org.madi.lab4.part2;

import org.madi.lab4.part2.matrix.Matrix;
import org.madi.lab4.part2.matrix.MatrixGenerator;
import org.madi.lab4.part2.striped.Striped;
import org.madi.lab4.part2.fox.Fox;

import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        MatrixGenerator randomMatrixGenerator = new MatrixGenerator();

        int MATRIXSIZE = 2000;
        int THREADSCOUNT = Runtime.getRuntime().availableProcessors();

        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();

        Matrix matrix1 = new Matrix(randomMatrixGenerator.generateRandomMatrix(MATRIXSIZE, MATRIXSIZE).getMatrix());
        Matrix matrix2 = new Matrix(randomMatrixGenerator.generateRandomMatrix(MATRIXSIZE, MATRIXSIZE).getMatrix());

//        Basic basic = new Basic(matrix1, matrix2);
        Striped striped = new Striped(matrix1, matrix2, THREADSCOUNT);

//        startTime = System.currentTimeMillis();
//        Matrix basicRes = new Matrix(basic.multiply().getMatrix());
//        endTime = System.currentTimeMillis();
//        long basicTime = endTime - startTime;
//        System.out.println("Basic: " + basicTime + " ms " + "for " + MATRIXSIZE + " matrix size" );

        startTime = System.currentTimeMillis();
        Matrix strRes = new Matrix(striped.multiply().getMatrix());
        endTime = System.currentTimeMillis();
        long strTime = endTime - startTime;
        System.out.println("Striped: " + strTime + " ms " + "for " + MATRIXSIZE + " matrix size" );

        startTime = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREADSCOUNT);
        Matrix foxRes = forkJoinPool.invoke(new Fox(matrix1, matrix2, 6));
        endTime = System.currentTimeMillis();
        long foxTime = endTime - startTime;
        System.out.println("Fox: " + foxTime + " ms " + "for " + MATRIXSIZE + " matrix size" );

        System.out.println("Acceleration: " + (double) (strTime / foxTime));
    }
}
