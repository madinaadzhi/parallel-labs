package org.madi.lab2;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class Matrix {
    private final int[][] matrix;

    public Matrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public Matrix(int rowsSize, int columnsSize) {
        matrix = new int[rowsSize][columnsSize];
    }

    public int getRowSize() {
        return matrix.length;
    }

    public int getColumnSize() {
        return matrix[0].length;
    }

    public int get(int i, int j) {
        return matrix[i][j];
    }

    public int[] getRow(int index) {
        return matrix[index];
    }

    public void set(int i, int j, int value) {
        matrix[i][j] = value;
    }

    public void print() {
        Arrays.stream(matrix).map(Arrays::toString).forEach(System.out::println);
    }
}
