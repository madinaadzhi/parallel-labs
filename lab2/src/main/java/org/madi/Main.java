package org.madi;

import org.madi.lab2.Basic;
import org.madi.lab2.Matrix;
import org.madi.lab2.MatrixGenerator;
import org.madi.lab2.fox.Fox;
import org.madi.lab2.striped.Striped;

public class Main {
    public static void main(String[] args) {
        MatrixGenerator randomMatrixGenerator = new MatrixGenerator();
        int SIZE = 8;

        Matrix matrix1 = new Matrix(randomMatrixGenerator.generateRandomMatrix(SIZE, SIZE).getMatrix());
        Matrix matrix2 = new Matrix(randomMatrixGenerator.generateRandomMatrix(SIZE, SIZE).getMatrix());

        System.out.println("Matrix 1:");
        matrix1.print();
        System.out.println();

        System.out.println("Matrix 2:");
        matrix2.print();
        System.out.println();

        Basic basic = new Basic(matrix1, matrix2);
        System.out.println("Basic result:");
        Matrix basicResult = new Matrix(basic.multiply().getMatrix());
        basicResult.print();

        Striped striped = new Striped(matrix1, matrix2, 4);
        Matrix stripedResult = new Matrix(striped.multiply().getMatrix());
        System.out.println("Striped result:");
        stripedResult.print();

        Fox fox = new Fox(matrix1, matrix2, 4);
        System.out.println("Fox result:");
        Matrix foxResult = new Matrix(fox.multiply().getMatrix());
        foxResult.print();
    }
}