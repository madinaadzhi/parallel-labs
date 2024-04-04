package org.madi;

import org.madi.lab2.Matrix;
import org.madi.lab2.MatrixGenerator;
import org.madi.lab2.MultithreadMultiplication;
import org.madi.lab2.fox.Fox;
import org.madi.lab2.striped.Striped;

public class Main {
    public static void main(String[] args) {
        MatrixGenerator randomMatrixGenerator = new MatrixGenerator();

        int SIZE = 8;
        Matrix matrix3 = new Matrix(new int[][]{
                {1, 5},
                {2, 3},
                {1, 7}
        });

        Matrix matrix4 = new Matrix(new int[][]{
                {1, 2, 3, 7},
                {5, 2, 8, 1}
        });

//        Matrix matrix3 = new Matrix(randomMatrixGenerator.generateRandomMatrix(SIZE, SIZE).getMatrix());
//        Matrix matrix4 = new Matrix(randomMatrixGenerator.generateRandomMatrix(SIZE, SIZE).getMatrix());

        System.out.println("Matrix 3:");
        matrix3.print();
        System.out.println();

        System.out.println("Matrix 4:");
        matrix4.print();
        System.out.println();

        Striped striped = new Striped();
        Matrix stripedResult = new Matrix(striped.multiply(matrix3, matrix4).getMatrix());
        System.out.println("Striped result:");
        stripedResult.print();

        MultithreadMultiplication multithreadMultiplication = new MultithreadMultiplication();
        System.out.println("Multithread multiplication result:");
        Matrix multithreadResult = new Matrix(multithreadMultiplication.multiply(matrix3, matrix4, 4).getMatrix());
        multithreadResult.print();

        Fox fox = new Fox(matrix3, matrix4, 4);
        System.out.println("Fox result:");
        Matrix foxResult = new Matrix(fox.multiply().getMatrix());
        foxResult.print();
    }
}